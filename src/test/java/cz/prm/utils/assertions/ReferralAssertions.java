package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.referral.query.ReferralsQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class ReferralAssertions {

    public static void assertPage(Page<Referral> page, PageDto<ReferralDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(page.getContent()).isNotEmpty().hasSameSizeAs(pageDto.getContent());
        assertReferralsDto(page.getContent(), pageDto.getContent());
    }

    public static void assertPage(Page<Referral> page1, PageImpl<Referral> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertReferrals(page1.getContent(), page2.getContent());
    }

    public static void assertReferrals(List<Referral> referrals1, List<Referral> referrals2) {
        assertThat(referrals1).isNotEmpty().hasSameSizeAs(referrals2);
        referrals1.forEach(c1 -> {
            var c2 = referrals2.stream().filter(u -> Objects.equals(c1.getContactId(), u.getContactId())).findFirst().get();
            assertReferral(c1, c2);
        });
    }

    public static void assertReferralsDto(List<Referral> referrals, List<ReferralDto> dtos) {
        assertThat(referrals).isNotEmpty().hasSameSizeAs(dtos);
        referrals.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertReferral(u1, u2);
        });
    }

    public static void assertReferral(Referral referral1, Referral referral2) {
        assertThat(referral1.getReferralId()).isEqualTo(referral2.getReferralId());
        assertThat(referral1.getContactId()).isEqualTo(referral2.getContactId());
        assertThat(referral1.getDescription()).isEqualTo(referral2.getDescription());
        assertThat(referral1.getLastEditDate()).isEqualTo(referral2.getLastEditDate());
        assertThat(referral1.getCreationDate()).isEqualTo(referral2.getCreationDate());
        assertThat(referral1.getOwner()).isEqualTo(referral2.getOwner());
    }

    public static void assertReferral(Referral referral, ReferralDto dto) {
        assertThat(referral.getReferralId()).isEqualTo(dto.getReferralId());
        assertThat(referral.getContactId()).isEqualTo(dto.getContactId());
        assertThat(referral.getName()).isEqualTo(dto.getName());
        assertThat(referral.getDescription()).isEqualTo(dto.getDescription());
        assertThat(referral.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertThat(referral.getCreationDate()).isEqualTo(dto.getCreationDate());
    }

    public static void assertReferralsQuery(ReferralsQuery query, ReferralQueryDto dto) {
        assertQuery(query, dto);
    }
}
