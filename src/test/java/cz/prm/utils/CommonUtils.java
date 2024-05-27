package cz.prm.utils;

import static cz.prm.utils.TestUtils.randomInt;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import java.util.List;

public class CommonUtils {

    public static final int DEFAULT_PAGE_SIZE = 50;

    public static <T> Page<T> page(List<T> content) {
        var page = new Page<T>();
        page.setContent(content);
        page.setTotalPages(randomInt());
        page.setTotalElements(randomInt());
        page.setNumberOfElements(randomInt());
        page.setPageSize(randomInt());
        page.setPageNumber(randomInt());
        return page;
    }

    public static Pagination pagination() {
        var pagination = new Pagination();
        pagination.setPageSize(randomInt());
        pagination.setPageNumber(randomInt());
        return pagination;
    }

    public static PaginationDto paginationDto() {
        var pagination = new PaginationDto();
        pagination.setPageSize(randomInt());
        pagination.setPageNumber(randomInt());
        return pagination;
    }
}
