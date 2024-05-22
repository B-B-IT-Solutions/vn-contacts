package cz.prm.domain.common;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.unsorted;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = PRIVATE)
public class PageRequests {

    public static PageRequest getPageRequest(Pagination pagination) {
        return getPageRequest(pagination, unsorted());
    }

    public static PageRequest getPageRequest(Pagination pagination, Sort sort) {
        return PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);
    }
}
