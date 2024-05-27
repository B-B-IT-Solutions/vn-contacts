package cz.prm.domain.common.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {

    private static final int DEFAULT_PAGE_SIZE = 50;

    private int pageNumber;

    private int pageSize;

    public Pagination() {
        this.pageNumber = 0;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
}
