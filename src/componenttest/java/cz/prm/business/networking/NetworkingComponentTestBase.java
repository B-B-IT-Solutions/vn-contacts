package cz.prm.business.networking;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.networking.ReferralSuggestionDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class NetworkingComponentTestBase extends BusinessComponentTestBase {

    protected static String NETWORKING_BASE_URL = "networking";
    protected static String REFERRAL_SUGGESTIONS_URL = NETWORKING_BASE_URL + "/%s/referral-suggestions";

    protected PageDto<ReferralSuggestionDto> user1GetReferralSuggestions(Long contactId) {
        return getReferralSuggestionsPage(contactId, USER_1);
    }

    protected PageDto<ReferralSuggestionDto> user2GetReferralSuggestions(Long contactId) {
        return getReferralSuggestionsPage(contactId, USER_2);
    }

    protected PageDto<ReferralSuggestionDto> user3GetReferralSuggestions(Long contactId) {
        return getReferralSuggestionsPage(contactId, USER_3);
    }

    protected void user1GetReferralSuggestionsExpectNotFound(Long contactId) {
        getReferralSuggestionsExpectNotFound(contactId, USER_1);
    }

    protected void user2GetReferralSuggestionsExpectNotFound(Long contactId) {
        getReferralSuggestionsExpectNotFound(contactId, USER_2);
    }

    protected void user3GetReferralSuggestionsExpectNotFound(Long contactId) {
        getReferralSuggestionsExpectNotFound(contactId, USER_3);
    }

    protected PageDto<ReferralSuggestionDto> getReferralSuggestionsPage(Long contactId, ComponentTestUser user) {
        var url = format(REFERRAL_SUGGESTIONS_URL, contactId);
        var typeRef = new TypeRef<PageDto<ReferralSuggestionDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected void getReferralSuggestionsExpectNotFound(Long contactId, ComponentTestUser user) {
        var url = format(REFERRAL_SUGGESTIONS_URL, contactId);
        getExpectNotFound(url, user);
    }
}



