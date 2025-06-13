package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReferralUtils.reminder;
import static cz.prm.utils.ReferralUtils.reminderDto;
import static cz.prm.utils.ReferralUtils.reminders;
import static cz.prm.utils.ReferralUtils.remindersQueryDto;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ReferralAssertions.assertPage;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferral;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferralsQuery;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.ReferralMapper;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.referral.query.ReferralsQuery;
import cz.prm.services.ReferralService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralControllerTest {

    @Mock
    private ReferralService referralService;
    @Captor
    private ArgumentCaptor<Referral> reminderCapt;
    @Captor
    private ArgumentCaptor<ReferralsQuery> cQueryCapt;

    private ReferralMapper mapper = MapperUtils.getReferralMapper();
    private ReferralController controller;

    @BeforeEach
    void setUp() {
        controller = new ReferralController(referralService, mapper);
    }

    @Test
    void getReferrals() {
        var page = page(reminders());
        var queryDto = remindersQueryDto();
        var contactId = randomLong();
        when(referralService.getReferrals(eq(contactId), any(ReferralsQuery.class))).thenReturn(page);

        var result = controller.getReferrals(contactId, queryDto);
        assertPage(page, result);
        verify(referralService).getReferrals(eq(contactId), cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertReferralsQuery(query, queryDto);
    }

    @Test
    void getReferral() {
        var reminder = reminder();
        var reminderId = reminder.getReferralId();
        when(referralService.getReferral(reminderId)).thenReturn(reminder);
        var result = controller.getReferral(reminderId);
        assertReferral(reminder, result);
    }

    @Test
    void createReferral() {
        var dto = reminderDto();
        controller.createReferral(dto);
        verify(referralService).createReferral(reminderCapt.capture());
        var reminder = reminderCapt.getValue();
        assertReferral(reminder, dto);
    }

    @Test
    void updateReferral() {
        var dto = reminderDto();
        controller.updateReferral(dto.getReferralId(), dto);
        verify(referralService).updateReferral(eq(dto.getReferralId()), reminderCapt.capture());
        var reminder = reminderCapt.getValue();
        assertReferral(reminder, dto);
    }

    @Test
    void deleteReferral() {
        var reminderId = randomLong();
        controller.deleteReferral(reminderId);
        verify(referralService).deleteReferral(reminderId);
    }
}