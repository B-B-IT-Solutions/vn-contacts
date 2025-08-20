package cz.prm.controllers.mapper.contacts;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.assertions.NetworkingAssertions.assertPage;
import static cz.prm.utils.assertions.NetworkingAssertions.assertReferralSuggestion;
import static cz.prm.utils.data.contacts.NetworkingUtils.referralSuggestion;
import static cz.prm.utils.data.contacts.NetworkingUtils.referralSuggestions;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class NetworkingMapperTest {

    private NetworkingMapper mapper = MapperUtils.getNetworkingMapper();

    @Test
    void toPageDto() {
        var page = page(referralSuggestions());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toReferralSuggestionDto() {
        var rs = referralSuggestion();
        var dto = mapper.toReferralSuggestionDto(rs);
        assertReferralSuggestion(rs, dto);
    }
}