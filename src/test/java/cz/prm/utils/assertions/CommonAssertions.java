package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.common.QueryDto;
import cz.prm.domain.common.Pagination;
import cz.prm.domain.common.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class CommonAssertions {

    public static void assertPageRequest(PageRequest pr, Pagination pagination, Sort sort) {
        assertThat(pr.getPageNumber()).isNotZero().isEqualTo(pagination.getPageNumber());
        assertThat(pr.getPageSize()).isNotZero().isEqualTo(pagination.getPageSize());
        assertThat(pr.getSort()).isEqualTo(sort);
    }

    public static void assertQuery(Query query, QueryDto dto) {
        assertPagination(query.getPagination(), dto.getPagination());
        assertThat(query.getSort()).isEqualTo(dto.getSort());
    }

    public static void assertPagination(Pagination pagination, PaginationDto paginationDto) {
        assertThat(pagination.getPageNumber()).isNotZero().isEqualTo(paginationDto.getPageNumber());
        assertThat(pagination.getPageSize()).isNotZero().isEqualTo(paginationDto.getPageSize());
    }
}
