package cz.prm.business.notes;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class NoteComponentTestBase extends BusinessComponentTestBase {

    protected static String NOTES_URL = "notes";
    protected static String CONTACT_NOTES_URL = NOTES_URL + "/contact/%s";
    protected static String NOTE_URL = NOTES_URL + "/%s";

    protected void user1CreateNote(NoteDto dto) {
        createNote(dto, USER_1);
    }

    protected void user2CreateNote(NoteDto dto) {
        createNote(dto, USER_2);
    }

    protected void user3CreateNote(NoteDto dto) {
        createNote(dto, USER_3);
    }

    protected void user1UpdateNote(Long noteId, NoteDto dto) {
        updateNote(noteId, dto, USER_1);
    }

    protected void user2UpdateNote(Long noteId, NoteDto dto) {
        updateNote(noteId, dto, USER_2);
    }

    protected void user3UpdateNote(Long noteId, NoteDto dto) {
        updateNote(noteId, dto, USER_3);
    }

    protected PageDto<NoteDto> user1GetNotes(Long contactId, NotesQueryDto queryDto) {
        return getNotesPage(contactId, queryDto, USER_1);
    }

    protected PageDto<NoteDto> user2GetNotes(Long contactId, NotesQueryDto queryDto) {
        return getNotesPage(contactId, queryDto, USER_2);
    }

    protected PageDto<NoteDto> user3GetNotes(Long contactId, NotesQueryDto queryDto) {
        return getNotesPage(contactId, queryDto, USER_3);
    }

    protected NoteDto user1GetNote(Long noteId) {
        return getNote(noteId, USER_1);
    }

    protected NoteDto user2GetNote(Long noteId) {
        return getNote(noteId, USER_2);
    }

    protected NoteDto user3GetNote(Long noteId) {
        return getNote(noteId, USER_3);
    }

    protected void createNote(NoteDto dto, ComponentTestUser user) {
        post(NOTES_URL, user, dto);
    }

    protected void updateNote(Long noteId, NoteDto dto, ComponentTestUser user) {
        var url = format(NOTE_URL, noteId);
        put(url, user, dto);
    }

    protected PageDto<NoteDto> getNotesPage(Long contactId, NotesQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(CONTACT_NOTES_URL, contactId);
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<NoteDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected NoteDto getNote(Long noteId, ComponentTestUser user) {
        var url = format(NOTE_URL, noteId);
        var typeRef = new TypeRef<NoteDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void user1UpdateNoteExpectNotFound(Long noteId, NoteDto dto) {
        updateNoteExpectNotFound(noteId, dto, USER_1);
    }

    protected void user2UpdateNoteExpectNotFound(Long noteId, NoteDto dto) {
        updateNoteExpectNotFound(noteId, dto, USER_2);
    }

    protected void user3UpdateNoteExpectNotFound(Long noteId, NoteDto dto) {
        updateNoteExpectNotFound(noteId, dto, USER_3);
    }

    protected void user1GetNoteExpectNotFound(Long noteId) {
        getNoteExpectNotFound(noteId, USER_1);
    }

    protected void user2GetNoteExpectNotFound(Long noteId) {
        getNoteExpectNotFound(noteId, USER_2);
    }

    protected void user3GetNoteExpectNotFound(Long noteId) {
        getNoteExpectNotFound(noteId, USER_3);
    }

    protected void updateNoteExpectNotFound(Long noteId, NoteDto dto, ComponentTestUser user) {
        var url = format(NOTE_URL, noteId);
        putExpectNotFound(url, user, dto);
    }

    protected void getNoteExpectNotFound(Long noteId, ComponentTestUser user) {
        var url = format(NOTE_URL, noteId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, NotesQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }
}
