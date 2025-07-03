package cz.prm.services.referral;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.ReferralUtils.referrals;
import static cz.prm.utils.ReferralUtils.referralsQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ReferralAssertions.assertPage;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferral;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralClearingHouseTest {

    @Mock
    private ReferralService referralService;
    private ReferralClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new ReferralClearingHouse(referralService);
    }

    @Test
    void getReferrals() {
        var page = page(referrals());
        var query = referralsQuery();
        when(referralService.getReferrals(query)).thenReturn(page);
        var result = clearingHouse.getReferrals(query);
        assertPage(result, page);
    }

    @Test
    void getContactReferrals() {
        var contactId = randomLong();
        var page = page(referrals());
        var query = referralsQuery();
        when(referralService.getContactReferrals(contactId, query)).thenReturn(page);
        var result = clearingHouse.getContactReferrals(contactId, query);
        assertPage(result, page);
    }

    @Test
    void getReferral() {
        var referral = referral();
        var referralId = referral.getReferralId();
        when(referralService.getReferral(referralId)).thenReturn(referral);
        var result = clearingHouse.getReferral(referralId);
        assertReferral(result, referral);
    }

    @Test
    void createReferral() {
        var referral = referral();
        clearingHouse.createReferral(referral);
        verify(referralService).createReferral(referral);
    }

    @Test
    void updateReferral() {
        var referral = referral();
        var referralId = referral.getReferralId();
        clearingHouse.updateReferral(referralId, referral);
        verify(referralService).updateReferral(referralId, referral);
    }

    @Test
    void deleteReferral() {
        var referral = referral();
        var referralId = referral.getReferralId();
        clearingHouse.deleteReferral(referralId);
        verify(referralService).deleteReferral(referralId);
    }
}