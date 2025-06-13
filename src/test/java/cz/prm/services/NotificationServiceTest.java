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
        var reminder1 = referral();
        var recurrence = recurrence();
        reminder1.setRecurrence(recurrence);
        var reminder2 = referral();
        var reminder3 = referral();
        var reminders = newArrayList(reminder1, reminder2, reminder3);
        var page = page(reminders);

        when(referralService.getContactReferrals(eq(contactId), any(ReferralsQuery.class))).thenReturn(page);
        notificationService.getNotifications(contactId);
    }
}