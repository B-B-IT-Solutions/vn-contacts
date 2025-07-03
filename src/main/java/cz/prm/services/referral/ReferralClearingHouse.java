package cz.prm.services.referral;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesQuery;
import cz.prm.domain.referral.Referral;
import cz.prm.domain.referral.query.ReferralsQuery;
import cz.prm.services.note.NoteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReferralClearingHouse {

    private ReferralService referralService;
    private NoteService noteService;

    @Autowired
    public ReferralClearingHouse(ReferralService referralService, NoteService noteService) {
        this.referralService = referralService;
        this.noteService = noteService;
    }

    public Page<Referral> getReferrals(ReferralsQuery query) {
        return referralService.getReferrals(query);
    }

    public Page<Referral> getContactReferrals(Long contactId, ReferralsQuery query) {
        return referralService.getContactReferrals(contactId, query);
    }

    public Referral getReferral(Long referralId) {
        return referralService.getReferral(referralId);
    }

    public Page<Note> getNotes(Long referralId, NotesQuery query) {
        return noteService.getReferralNotes(referralId, query);
    }

    public void createReferral(Referral referral) {
        referralService.createReferral(referral);
    }

    public void createNote(Long referralId, Note note) {
        note.setReferralId(referralId);
        noteService.createNote(note);
    }

    public void updateReferral(Long referralId, Referral uReferral) {
        referralService.updateReferral(referralId, uReferral);
    }

    public void updateNote(Long noteId, Note uNote) {
        noteService.updateNote(noteId, uNote);
    }

    public void deleteReferral(Long referralId) {
        referralService.deleteReferral(referralId);
    }

    public void deleteNote(Long noteId) {
        noteService.deleteNote(noteId);
    }
}
