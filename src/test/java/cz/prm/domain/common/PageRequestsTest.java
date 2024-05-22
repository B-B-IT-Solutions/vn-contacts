package cz.prm.domain.common;

import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.assertions.CommonAssertions.assertPageRequest;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.Sort.unsorted;

import org.junit.jupiter.api.Test;

class PageRequestsTest {

    @Test
    void getPageRequestUnsorted() {
        var pagination = pagination();
        var pr = PageRequests.getPageRequest(pagination);
        assertPageRequest(pr, pagination, unsorted());
    }

    @Test
    void getPageRequest() {
        var pagination = pagination();
        var sort = by(desc("prop1"));
        var pr = PageRequests.getPageRequest(pagination, sort);
        assertPageRequest(pr, pagination, sort);
    }
}