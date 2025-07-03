package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ComponentTestUtils.uuids;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesFilterDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.domain.note.Note;
import java.util.List;

public class NoteComponentTestUtils {

    public static List<Note> notes() {
        return newArrayList(note(), note(), note());
    }

    public static Note note() {
        return note(randomLong());
    }

    public static Note note(long contactId) {
        var note = new Note();
        note.setContactId(contactId);
        note.setText(format("Text-%s-End", uuid()));
        note.setCategories(uuids());
        return note;
    }

    public static NoteDto noteDto(long contactId) {
        var dto = new NoteDto();
        dto.setContactId(contactId);
        dto.setText(uuid());
        dto.setCategories(uuids());
        return dto;
    }

    public static NotesQueryDto notesQueryDto() {
        var query = new NotesQueryDto();
        query.setPagination(new PaginationDto());
        query.setFilter(new NotesFilterDto());
        return query;
    }
}
