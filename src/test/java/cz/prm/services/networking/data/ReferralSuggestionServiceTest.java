package cz.prm.services.networking.data;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.NetworkingUtils.contactPotentialReferrals;
import static cz.prm.utils.NetworkingUtils.referralRequirement;
import static cz.prm.utils.assertions.NetworkingAssertions.assertContactReferralSuggestions;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contact.Contact;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralSuggestionServiceTest {

    private static final int SUGGESTIONS_COUNT = 7;

    private ReferralSuggestionService scoringService;

    @BeforeEach
    void setUp() {
        scoringService = new ReferralSuggestionService();
    }

    @Test
    void getReferralSuggestions() {
        var contact = contact();
        var rr = referralRequirement(contact);
        var contactReferrals = contactPotentialReferrals(contact, 10);
        var randomContacts = contacts();
        List<Contact> potentialReferrals = newArrayList();
        potentialReferrals.addAll(contactReferrals);
        potentialReferrals.addAll(randomContacts);

        var result = scoringService.getReferralSuggestions(rr, potentialReferrals);
        assertThat(result).hasSize(SUGGESTIONS_COUNT);
        assertContactReferralSuggestions(result, contactReferrals);
    }

    @Test
    void getReferralSuggestions_EmptyRelevantReferrals() {
        var rr = referralRequirement();
        var potentialReferrals = contacts();

        var result = scoringService.getReferralSuggestions(rr, potentialReferrals);
        assertThat(result).isEmpty();
    }

    @Test
    void getReferralSuggestions_EmptyPotentialReferrals() {
        var rr = referralRequirement();
        var result = scoringService.getReferralSuggestions(rr, newArrayList());
        assertThat(result).isEmpty();
    }
}