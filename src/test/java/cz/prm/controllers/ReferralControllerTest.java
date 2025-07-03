package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.ReferralUtils.referralDto;
import static cz.prm.utils.ReferralUtils.referrals;
import static cz.prm.utils.ReferralUtils.referralsQueryDto;
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
import cz.prm.services.referral.ReferralService;
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
    private ArgumentCaptor<Referral> referralCapt;
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
        var page = page(referrals());
        var queryDto = referralsQueryDto();
        when(referralService.getReferrals(any(ReferralsQuery.class))).thenReturn(page);

        var result = controller.getReferrals(queryDto);
        assertPage(page, result);
        verify(referralService).getReferrals(cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertReferralsQuery(query, queryDto);
    }

    @Test
    void getContactReferrals() {
        var page = page(referrals());
        var queryDto = referralsQueryDto();
        var contactId = randomLong();
        when(referralService.getContactReferrals(eq(contactId), any(ReferralsQuery.class))).thenReturn(page);

        var result = controller.getContactReferrals(contactId, queryDto);
        assertPage(page, result);
        verify(referralService).getContactReferrals(eq(contactId), cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertReferralsQuery(query, queryDto);
    }

    @Test
    void getReferral() {
        var referral = referral();
        var referralId = referral.getReferralId();
        when(referralService.getReferral(referralId)).thenReturn(referral);
        var result = controller.getReferral(referralId);
        assertReferral(referral, result);
    }

    @Test
    void createReferral() {
        var dto = referralDto();
        controller.createReferral(dto);
        verify(referralService).createReferral(referralCapt.capture());
        var referral = referralCapt.getValue();
        assertReferral(referral, dto);
    }

    @Test
    void updateReferral() {
        var dto = referralDto();
        controller.updateReferral(dto.getReferralId(), dto);
        verify(referralService).updateReferral(eq(dto.getReferralId()), referralCapt.capture());
        var referral = referralCapt.getValue();
        assertReferral(referral, dto);
    }

    @Test
    void deleteReferral() {
        var referralId = randomLong();
        controller.deleteReferral(referralId);
        verify(referralService).deleteReferral(referralId);
    }
}