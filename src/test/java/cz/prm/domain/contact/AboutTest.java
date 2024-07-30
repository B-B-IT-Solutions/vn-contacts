package cz.prm.domain.contact;

import static cz.prm.utils.TestUtils.randomLong;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AboutTest {

    @Test
    void newInstance() {
        var contactId = randomLong();
        var about = new About(contactId);
        assertThat(about.getContactId()).isEqualTo(contactId);
    }
}