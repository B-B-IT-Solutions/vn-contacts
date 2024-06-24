package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.controllers.mappers.NoteMapper;
import cz.prm.services.NoteService;
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

    public NoteController(NoteService noteService, NoteMapper mapper) {
        this.noteService = noteService;
        this.mapper = mapper;
    }

    @GetMapping("/contact/{contactId}")
    public PageDto<NoteDto> getNotes(@PathVariable("contactId") Long contactId, NotesQueryDto queryDto) {
        var query = mapper.toNullSafeNotesQuery(queryDto);
        var notes = noteService.getNotes(contactId, query);
        return mapper.toPageDto(notes);
    }

    @GetMapping("/note/{noteId}")
    public NoteDto getNote(@PathVariable("noteId") Long noteId) {
        var note = noteService.getNote(noteId);
        return mapper.toContactDto(note);
    }

    @PostMapping("/note")
    public void createNote(@RequestBody NoteDto dto) {
        var note = mapper.toNote(dto);
        noteService.createNote(note);
    }

    @PutMapping("/note/{noteId}")
    public void updateNote(@PathVariable("noteId") Long noteId, @RequestBody NoteDto dto) {
        var note = mapper.toNote(dto);
        noteService.updateNote(noteId, note);
    }

    @PutMapping("/note/{noteId}")
    public void deleteNote(@PathVariable("noteId") Long noteId) {
        noteService.deleteNote(noteId);
    }
}
