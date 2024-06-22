package cz.prm.utils;

import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesQuery;
import java.util.List;

public class NoteUtils {

    public static List<Note> notes() {
        return newArrayList(note(), note(), note());
    }

    public static Note note() {
        var note = new Note();
        note.setNoteId(randomLong());
        note.setContactId(randomLong());
        note.setText(uuid());
        note.setOrder(randomLong());
        note.setLastEditDate(now());
        note.setCreationDate(now());
        note.setOwner(user());
        return note;
    }

    public static NoteDto noteDto() {
        var note = new NoteDto();
        note.setNoteId(randomLong());
        note.setContactId(randomLong());
        note.setText(uuid());
        note.setOrder(randomLong());
        note.setLastEditDate(now());
        note.setCreationDate(now());
        return note;
    }

    public static NotesQuery notesQuery() {
        var query = new NotesQuery();
        query.setPagination(pagination());
        query.setSort(uuid());
        return query;
    }

    public static NotesQueryDto notesQueryDto() {
        var query = new NotesQueryDto();
        query.setPagination(paginationDto());
        query.setSort(uuid());
        return query;
    }
}
