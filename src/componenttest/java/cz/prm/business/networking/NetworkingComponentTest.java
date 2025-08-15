package cz.prm.business.networking;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.assertions.ComponentTestNetworkingAssertions.assertReferralSuggestions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class NetworkingComponentTest extends NetworkingComponentTestBase {

    @Test
    void getReferralSuggestions() {
        var contact = createDecoratedContact(USER_1);
        var contactId = contact.getContactId();
        var pageDto = user1GetReferralSuggestions(contactId);
        assertThat(pageDto.getContent()).isEmpty();

        var potentialReferrals = createReferralSuggestions(contact, USER_1);
        pageDto = user1GetReferralSuggestions(contactId);
        assertReferralSuggestions(potentialReferrals, pageDto);

        user2GetReferralSuggestionsExpectNotFound(contactId);
        user3GetReferralSuggestionsExpectNotFound(contactId);

        contact = createDecoratedContact(USER_2);
        contactId = contact.getContactId();
        pageDto = user2GetReferralSuggestions(contactId);
        assertThat(pageDto.getContent()).isEmpty();

        potentialReferrals = createReferralSuggestions(contact, USER_2);
        pageDto = user2GetReferralSuggestions(contactId);
        assertReferralSuggestions(potentialReferrals, pageDto);

        user1GetReferralSuggestionsExpectNotFound(contactId);
        user3GetReferralSuggestionsExpectNotFound(contactId);

        contact = createDecoratedContact(USER_3);
        contactId = contact.getContactId();
        pageDto = user3GetReferralSuggestions(contactId);
        assertThat(pageDto.getContent()).isEmpty();

        potentialReferrals = createReferralSuggestions(contact, USER_3);
        pageDto = user3GetReferralSuggestions(contactId);
        assertReferralSuggestions(potentialReferrals, pageDto);

        user1GetReferralSuggestionsExpectNotFound(contactId);
        user2GetReferralSuggestionsExpectNotFound(contactId);
    }
}
