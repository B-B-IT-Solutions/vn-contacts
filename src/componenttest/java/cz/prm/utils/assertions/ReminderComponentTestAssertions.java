package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.domain.referral.Referral;
import java.util.List;
import java.util.Objects;

public class ReminderComponentTestAssertions {

    public static void assertReminders(List<Referral> referrals, PageDto<ReferralDto> pageDto) {
        assertReminders(referrals, pageDto.getContent());
    }

    public static void assertReminders(List<Referral> referrals, List<ReferralDto> dtos) {
        assertThat(referrals).isNotEmpty().hasSameSizeAs(dtos);
        referrals.forEach(n1 -> {
            var n2 = dtos.stream().filter(n -> Objects.equals(n1.getReminderId(), n.getReminderId())).findFirst().get();
            assertReminder(n1, n2);
        });
    }

    public static void assertReminder(Referral referral, ReferralDto referralDto) {
        assertThat(referral.getReminderId()).isEqualTo(referralDto.getReminderId());
        assertThat(referral.getContactId()).isEqualTo(referralDto.getContactId());
        assertThat(referral.getTitle()).isEqualTo(referralDto.getTitle());
        assertThat(referral.getDescription()).isEqualTo(referralDto.getDescription());
        assertThat(referral.getRecurrence().getValue()).isEqualTo(referralDto.getRecurrence());
        assertThat(referral.getLastEditDate()).isNotNull();
        assertThat(referral.getCreationDate()).isNotNull();
    }
}
