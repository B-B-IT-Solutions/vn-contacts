package cz.prm.domain.common;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.common.query.Pagination;
import org.junit.jupiter.api.Test;

class PaginationTest {

    private static final int DEFAULT_PAGE_SIZE = 50;

    @Test
    void newInstance() {
        var pagination = new Pagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}