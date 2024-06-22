package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.note.NoteDto;
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
        var contact = new Note();
        contact.setContactId(contactId);
        contact.setText(format("Text%s", uuid()));
        contact.setOrder(randomLong());
        return contact;
    }

    public static NoteDto noteDto() {
        var dto = new NoteDto();
        dto.setText(uuid());
        return dto;
    }

    public static NotesQueryDto notesQueryDto() {
        var query = new NotesQueryDto();
        query.setPagination(new PaginationDto());
        return query;
    }
}
