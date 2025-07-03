package cz.prm.services.referral;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.NoteUtils.notes;
import static cz.prm.utils.NoteUtils.notesQuery;
import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.ReferralUtils.referrals;
import static cz.prm.utils.ReferralUtils.referralsQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.NoteAssertions.assertNotesPage;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferral;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferralsPage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.services.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralClearingHouseTest {

    @Mock
    private ReferralService referralService;
    @Mock
    private NoteService noteService;
    private ReferralClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new ReferralClearingHouse(referralService, noteService);
    }

    @Test
    void getReferrals() {
        var page = page(referrals());
        var query = referralsQuery();
        when(referralService.getReferrals(query)).thenReturn(page);
        var result = clearingHouse.getReferrals(query);
        assertReferralsPage(result, page);
    }

    @Test
    void getContactReferrals() {
        var contactId = randomLong();
        var page = page(referrals());
        var query = referralsQuery();
        when(referralService.getContactReferrals(contactId, query)).thenReturn(page);
        var result = clearingHouse.getContactReferrals(contactId, query);
        assertReferralsPage(result, page);
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
    void getReferralNotes() {
        var page = page(notes());
        var query = notesQuery();
        var referralId = randomLong();

        when(noteService.getReferralNotes(referralId, query)).thenReturn(page);
        var result = clearingHouse.getReferralNotes(referralId, query);
        assertNotesPage(result, page);
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