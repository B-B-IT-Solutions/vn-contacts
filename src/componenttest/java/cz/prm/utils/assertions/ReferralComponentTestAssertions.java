package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.domain.referral.Referral;
import java.util.List;
import java.util.Objects;

public class ReferralComponentTestAssertions {

    public static void assertReferrals(List<Referral> referrals, PageDto<ReferralDto> pageDto) {
        assertReferrals(referrals, pageDto.getContent());
    }

    public static void assertReferrals(List<Referral> referrals, List<ReferralDto> dtos) {
        assertThat(referrals).isNotEmpty().hasSameSizeAs(dtos);
        referrals.forEach(n1 -> {
            var n2 = dtos.stream().filter(n -> Objects.equals(n1.getReferralId(), n.getReferralId())).findFirst().get();
            assertReferral(n1, n2);
        });
    }

    public static void assertReferral(Referral referral, ReferralDto referralDto) {
        assertThat(referral.getReferralId()).isEqualTo(referralDto.getReferralId());
        assertThat(referral.getContactId()).isEqualTo(referralDto.getContactId());
        assertThat(referral.getTitle()).isEqualTo(referralDto.getTitle());
        assertThat(referral.getDescription()).isEqualTo(referralDto.getDescription());
        assertThat(referral.getRecurrence().getValue()).isEqualTo(referralDto.getRecurrence());
        assertThat(referral.getLastEditDate()).isNotNull();
        assertThat(referral.getCreationDate()).isNotNull();
    }
}
