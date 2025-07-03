package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.TestUtils.uuids;
import static java.time.Instant.now;

import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesFilterDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesFilter;
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
        note.setReferralId(randomLong());
        note.setText(uuid());
        note.setCategories(uuids());
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
        note.setCategories(uuids());
        note.setLastEditDate(now());
        note.setCreationDate(now());
        return note;
    }

    public static NotesQuery notesQuery() {
        var query = new NotesQuery();
        query.setPagination(pagination());
        query.setFilter(notesFilter());
        query.setSort(uuid());
        return query;
    }

    public static NotesQueryDto notesQueryDto() {
        var query = new NotesQueryDto();
        query.setPagination(paginationDto());
        query.setFilter(notesFilterDto());
        query.setSort(uuid());
        return query;
    }

    public static NotesFilter notesFilter() {
        var filter = new NotesFilter();
        filter.setGlobalFilter(uuid());
        filter.setTitle(uuid());
        filter.setCategories(uuid());
        return filter;
    }

    public static NotesFilterDto notesFilterDto() {
        var filter = new NotesFilterDto();
        filter.setGlobalFilter(uuid());
        filter.setTitle(uuid());
        filter.setCategories(uuid());
        return filter;
    }
}
