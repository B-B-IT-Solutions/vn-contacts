package cz.prm.services.contacts.networking.data;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.assertions.contacts.NetworkingAssertions.assertContactReferralSuggestions;
import static cz.prm.utils.data.contacts.ContactUtils.contact;
import static cz.prm.utils.data.contacts.ContactUtils.contacts;
import static cz.prm.utils.data.contacts.NetworkingUtils.contactPotentialReferrals;
import static cz.prm.utils.data.contacts.NetworkingUtils.referralRequirement;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.networking.ReferralSuggestion;
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
        var expectedSortOrder = newArrayList(result.getContent());
        sort(expectedSortOrder, comparing(ReferralSuggestion::getScore).reversed());
        assertThat(result.getContent()).hasSize(SUGGESTIONS_COUNT).containsExactlyElementsOf(expectedSortOrder);
        assertContactReferralSuggestions(result.getContent(), contactReferrals);
    }

    @Test
    void getReferralSuggestions_EmptyRelevantReferrals() {
        var rr = referralRequirement();
        var potentialReferrals = contacts();

        var result = scoringService.getReferralSuggestions(rr, potentialReferrals);
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void getReferralSuggestions_EmptyPotentialReferrals() {
        var rr = referralRequirement();
        var result = scoringService.getReferralSuggestions(rr, newArrayList());
        assertThat(result.getContent()).isEmpty();
    }
}