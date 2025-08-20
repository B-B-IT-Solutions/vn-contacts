package cz.prm.domain.common;

import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.data.contacts.ContactUtils.contacts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import cz.prm.domain.common.query.Page;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageTest {

    @Mock
    private org.springframework.data.domain.Page springDataPage;

    @Test
    void newInstanceSpringData() {
        var totalPages = randomInt();
        var totalElements = randomLong();
        var pageNumber = randomInt();
        var pageSize = randomInt();
        var numberOfElements = randomInt();
        var content = contacts();

        when(springDataPage.getTotalPages()).thenReturn(totalPages);
        when(springDataPage.getTotalElements()).thenReturn(totalElements);
        when(springDataPage.getNumber()).thenReturn(pageNumber);
        when(springDataPage.getSize()).thenReturn(pageSize);
        when(springDataPage.getNumberOfElements()).thenReturn(numberOfElements);
        when(springDataPage.getContent()).thenReturn(content);

        var page = new Page(springDataPage);
        assertThat(page.getTotalPages()).isEqualTo(springDataPage.getTotalPages());
        assertThat(page.getTotalElements()).isEqualTo(springDataPage.getTotalElements());
        assertThat(page.getPageNumber()).isEqualTo(springDataPage.getNumber());
        assertThat(page.getPageSize()).isEqualTo(springDataPage.getSize());
        assertThat(page.getNumberOfElements()).isEqualTo(springDataPage.getNumberOfElements());
        assertThat(page.getContent()).isEqualTo(springDataPage.getContent());
    }

    @Test
    void newInstanceCollection() {
        var contacts = contacts();
        var page = new Page(contacts);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getPageNumber()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(contacts.size());
        assertThat(page.getPageSize()).isEqualTo(contacts.size());
        assertThat(page.getNumberOfElements()).isEqualTo(contacts.size());
        assertThat(page.getContent()).isNotEmpty().isEqualTo(contacts);
    }
}