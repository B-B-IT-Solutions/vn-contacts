package cz.prm.controllers.mappers;

import static cz.prm.utils.NetworkingUtils.referralSuggestion;
import static cz.prm.utils.assertions.NetworkingAssertions.assertReferralSuggestion;

import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class NetworkingMapperTest {

    private NetworkingMapper mapper = MapperUtils.getNetworkingMapper();

    @Test
    void toReferralSuggestionDto() {
        var rs = referralSuggestion();
        var dto = mapper.toReferralSuggestionDto(rs);
        assertReferralSuggestion(rs, dto);
    }
}