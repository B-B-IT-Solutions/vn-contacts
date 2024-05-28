package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.controllers.mappers.NoteMapper;
import cz.prm.services.contact.NotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("notes")
@RestController
public class NotesController {

    private NotesService notesService;
    public NoteMapper mapper;

    public NotesController(NotesService notesService, NoteMapper mapper) {
        this.notesService = notesService;
        this.mapper = mapper;
    }

    @GetMapping("/{contactId")
    public PageDto<NoteDto> getNotes(@PathVariable("contactId") Long contactId, NotesQueryDto queryDto) {
        var query = mapper.toNullSafeNotesQuery(queryDto);
        var contacts = notesService.getNotes(contactId, query);
        return mapper.toPageDto(contacts);
    }

    @GetMapping("/{noteId}")
    public NoteDto getNote(@PathVariable("noteId") Long noteId) {
        var contact = notesService.getNote(noteId);
        return mapper.toContactDto(contact);
    }

    @PostMapping
    public void createNote(@RequestBody NoteDto dto) {
        var contact = mapper.toNote(dto);
        notesService.createNote(contact);
    }

    @PutMapping("/{noteId}")
    public void updateNote(@PathVariable("noteId") Long noteId, @RequestBody NoteDto dto) {
        var contact = mapper.toNote(dto);
        notesService.updateNote(noteId, contact);
    }
}
