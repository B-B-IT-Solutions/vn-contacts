package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.controllers.mappers.NoteMapper;
import cz.prm.services.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("notes")
@RestController
public class NoteController {

    private NoteService noteService;
    private NoteMapper mapper;

    @Autowired
    public NoteController(NoteService noteService, NoteMapper mapper) {
        this.noteService = noteService;
        this.mapper = mapper;
    }

    @GetMapping("/contact/{contactId}")
    public PageDto<NoteDto> getContactNotes(@PathVariable("contactId") Long contactId, NotesQueryDto queryDto) {
        var query = mapper.toNullSafeNotesQuery(queryDto);
        var notes = noteService.getContactNotes(contactId, query);
        return mapper.toPageDto(notes);
    }

    @GetMapping("/referral/{referralId}")
    public PageDto<NoteDto> getReferralNotes(@PathVariable("referralId") Long referralId, NotesQueryDto queryDto) {
        var query = mapper.toNullSafeNotesQuery(queryDto);
        var referral = noteService.getReferralNotes(referralId, query);
        return mapper.toPageDto(referral);
    }

    @GetMapping("/{noteId}")
    public NoteDto getNote(@PathVariable("noteId") Long noteId) {
        var note = noteService.getNote(noteId);
        return mapper.toNoteDto(note);
    }

    @PostMapping("/contact/{contactId}")
    public void createContactNote(@PathVariable("contactId") Long contactId, @RequestBody NoteDto dto) {
        var note = mapper.toNote(dto);
        noteService.createContactNote(contactId, note);
    }

    @PostMapping("/referral/{referralId}")
    public void createReferralNote(@PathVariable("referralId") Long referralId, @RequestBody NoteDto dto) {
        var note = mapper.toNote(dto);
        noteService.createReferralNote(referralId, note);
    }

    @PutMapping("/{noteId}")
    public NoteDto updateNote(@PathVariable("noteId") Long noteId, @RequestBody NoteDto updatedDto) {
        var updatedNote = mapper.toNote(updatedDto);
        var response = noteService.updateNote(noteId, updatedNote);
        return mapper.toNoteDto(response);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable("noteId") Long noteId) {
        noteService.deleteNote(noteId);
    }
}
