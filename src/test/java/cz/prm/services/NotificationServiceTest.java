package cz.prm.services;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReferralUtils.recurrence;
import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.TestUtils.randomLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import cz.prm.domain.referral.query.ReferralsQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private ReferralService referralService;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(referralService);
    }

    @Test
    void getNotifications() {
        var contactId = randomLong();
        var referral1 = referral();
        var recurrence = recurrence();
        referral1.setRecurrence(recurrence);
        var referral2 = referral();
        var referral3 = referral();
        var referrals = newArrayList(referral1, referral2, referral3);
        var page = page(referrals);

        when(referralService.getContactReferrals(eq(contactId), any(ReferralsQuery.class))).thenReturn(page);
        notificationService.getNotifications(contactId);
    }
}