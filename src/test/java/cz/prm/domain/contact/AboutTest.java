package cz.prm.domain.contact;

import static cz.prm.utils.ContactUtils.meeting;
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

    @Test
    void getFirstMeeting() {
        var about = new About();
        assertThat(about.getFirstMeeting()).isNotNull();
        about.setFirstMeeting(null);
        assertThat(about.getFirstMeeting()).isNotNull();

        var meeting = meeting();
        about.setFirstMeeting(meeting);
        assertThat(about.getFirstMeeting()).isEqualTo(meeting);
    }
}