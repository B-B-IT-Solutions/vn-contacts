package cz.prm.services.referral;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.NoteUtils.note;
import static cz.prm.utils.NoteUtils.notes;
import static cz.prm.utils.NoteUtils.notesQuery;
import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.ReferralUtils.referrals;
import static cz.prm.utils.ReferralUtils.referralsQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.NoteAssertions.assertNotesPage;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferral;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferralsPage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.domain.note.Note;
import cz.prm.services.note.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralClearingHouseTest {

    @Mock
    private ReferralService referralService;
    @Mock
    private NoteService noteService;
    @Captor
    private ArgumentCaptor<Note> noteCapt;

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
    void getNotes() {
        var page = page(notes());
        var query = notesQuery();
        var referralId = randomLong();

        when(noteService.getReferralNotes(referralId, query)).thenReturn(page);
        var result = clearingHouse.getNotes(referralId, query);
        assertNotesPage(result, page);
    }

    @Test
    void createReferral() {
        var referral = referral();
        clearingHouse.createReferral(referral);
        verify(referralService).createReferral(referral);
    }

    @Test
    void createNote() {
        var note = note();
        var referralId = randomLong();
        assertThat(note.getReferralId()).isNotEqualTo(referralId);

        clearingHouse.createNote(referralId, note);
        verify(noteService).createNote(noteCapt.capture());
        var savedNoted = noteCapt.getValue();
        assertThat(savedNoted.getReferralId()).isEqualTo(referralId);
    }

    @Test
    void updateReferral() {
        var referral = referral();
        var referralId = referral.getReferralId();
        clearingHouse.updateReferral(referralId, referral);
        verify(referralService).updateReferral(referralId, referral);
    }

    @Test
    void updateNote() {
        var note = note();
        var noteId = note.getNoteId();
        clearingHouse.updateNote(noteId, note);
        verify(noteService).updateNote(noteId, note);
    }

    @Test
    void deleteReferral() {
        var referralId = randomLong();
        clearingHouse.deleteReferral(referralId);
        verify(referralService).deleteReferral(referralId);
    }

    @Test
    void deleteNote() {
        var noteId = randomLong();
        clearingHouse.deleteNote(noteId);
        verify(noteService).deleteNote(noteId);
    }
}