package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import java.util.List;
import java.util.Objects;

public class NetworkingAssertions {

    public static void assertReferralRequirement(ReferralRequirement rr, Contact contact) {
        assertContact(rr.getContact(), contact);
        assertThat(rr.getIndustries()).containsExactlyElementsOf(contact.getIndustries());
        assertThat(rr.getSkills()).containsExactlyElementsOf(contact.getSkills());
        assertThat(rr.getProducts()).containsExactlyElementsOf(contact.getProducts());
        assertThat(rr.getTargetMarkets()).containsExactlyElementsOf(contact.getTargetMarkets());
    }

    public static void assertReferralRequirement(ReferralRequirement rr1, ReferralRequirement rr2) {
        assertContact(rr1.getContact(), rr2.getContact());
        assertThat(rr1.getIndustries()).containsExactlyElementsOf(rr2.getIndustries());
        assertThat(rr1.getSkills()).containsExactlyElementsOf(rr2.getSkills());
        assertThat(rr1.getProducts()).containsExactlyElementsOf(rr2.getProducts());
        assertThat(rr1.getTargetMarkets()).containsExactlyElementsOf(rr2.getTargetMarkets());
    }

    public static void assertReferralSuggestions(List<ReferralSuggestion> rss1, List<ReferralSuggestion> rss2) {
        assertThat(rss1).isNotEmpty().hasSameSizeAs(rss2);
        rss1.forEach(rs1 -> {
            var rs2 = rss2.stream().filter(u -> Objects.equals(rs1.getContact(), u.getContact())).findFirst().get();
            assertReferralSuggestion(rs1, rs2);
        });
    }

    public static void assertReferralSuggestion(ReferralSuggestion rs1, ReferralSuggestion rs2) {
        assertContact(rs1.getContact(), rs2.getContact());
        assertThat(rs1.getScore()).isEqualTo(rs2.getScore());
        assertThat(rs1.getReasons()).containsExactlyElementsOf(rs2.getReasons());
    }
}
