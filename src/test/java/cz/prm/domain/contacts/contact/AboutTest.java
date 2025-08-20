package cz.prm.domain.contacts.contact;

import static cz.prm.utils.ContactUtils.firstInteraction;
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
        assertThat(about.getFirstInteraction()).isNotNull();
        about.setFirstInteraction(null);
        assertThat(about.getFirstInteraction()).isNotNull();

        var meeting = firstInteraction();
        about.setFirstInteraction(meeting);
        assertThat(about.getFirstInteraction()).isEqualTo(meeting);
    }
}