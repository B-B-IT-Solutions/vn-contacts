package cz.prm.domain.contacts.networking;

import static cz.prm.utils.data.contacts.ContactUtils.contact;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReferralRequirementTest {

    @Test
    void getters() {
        var contact = contact();
        var rs = new ReferralRequirement(contact);
        assertThat(rs.getContact()).isEqualTo(contact);
        assertThat(rs.getIndustries()).isEqualTo(contact.getIndustries());
        assertThat(rs.getProducts()).isEqualTo(contact.getProducts());
        assertThat(rs.getTargetMarkets()).isEqualTo(contact.getTargetMarkets());
        assertThat(rs.getSkills()).isEqualTo(contact.getSkills());
    }
}