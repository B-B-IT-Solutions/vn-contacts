package cz.prm.utils.assertions.contacts;

import static cz.prm.utils.TimeComponentTestUtils.ONE_SECOND_OFFSET;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.referral.ReferralDto;
import cz.prm.domain.contacts.referral.Referral;
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
        assertThat(referral.getName()).isEqualTo(referralDto.getName());
        assertThat(referral.getNote()).isEqualTo(referralDto.getNote());
        assertThat(referral.getSource()).isEqualTo(referralDto.getSource());
        assertThat(referral.getPriority()).isEqualTo(referralDto.getPriority());
        assertThat(referral.getStatus()).isEqualTo(referralDto.getStatus());
        assertThat(referral.getClosedDate()).isCloseTo(referralDto.getClosedDate(), ONE_SECOND_OFFSET);
        assertThat(referral.getStartDate()).isCloseTo(referralDto.getStartDate(), ONE_SECOND_OFFSET);
        assertThat(referral.getExpiredDate()).isCloseTo(referralDto.getExpiredDate(), ONE_SECOND_OFFSET);
        assertThat(referral.getLastEditDate()).isNotNull();
        assertThat(referral.getCreationDate()).isNotNull();
    }
}
