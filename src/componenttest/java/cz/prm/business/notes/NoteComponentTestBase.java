package cz.prm.business.notes;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesFilterDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class NoteComponentTestBase extends BusinessComponentTestBase {

    protected static String NOTES_BASE_URL = "notes";
    protected static String CONTACT_NOTES_URL = NOTES_BASE_URL + "/contact/%s";
    protected static String REFERRAL_NOTES_URL = NOTES_BASE_URL + "/referral/%s";
    protected static String NOTE_URL = NOTES_BASE_URL + "/note";
    protected static String NOTE_BY_ID_URL = NOTE_URL + "/%s";

    protected void user1CreateContactNote(Long contactId, NoteDto dto) {
        createContactNote(contactId, dto, USER_1);
    }

    protected void user2CreateContactNote(Long contactId, NoteDto dto) {
        createContactNote(contactId, dto, USER_2);
    }

    protected void user3CreateContactNote(Long contactId, NoteDto dto) {
        createContactNote(contactId, dto, USER_3);
    }

    protected void user1CreateReferralNote(Long referralId, NoteDto dto) {
        createReferralNote(referralId, dto, USER_1);
    }

    protected void user2CreateReferralNote(Long referralId, NoteDto dto) {
        createReferralNote(referralId, dto, USER_2);
    }

    protected void user3CreateReferralNote(Long referralId, NoteDto dto) {
        createReferralNote(referralId, dto, USER_3);
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

    protected void user1DeleteNote(Long noteId) {
        deleteNote(noteId, USER_1);
    }

    protected void user2DeleteNote(Long noteId) {
        deleteNote(noteId, USER_2);
    }

    protected void user3DeleteNote(Long noteId) {
        deleteNote(noteId, USER_3);
    }

    protected PageDto<NoteDto> user1GetContactNotes(Long contactId, NotesQueryDto queryDto) {
        return getContactNotesPage(contactId, queryDto, USER_1);
    }

    protected PageDto<NoteDto> user2GetContactNotes(Long contactId, NotesQueryDto queryDto) {
        return getContactNotesPage(contactId, queryDto, USER_2);
    }

    protected PageDto<NoteDto> user3GetContactNotes(Long contactId, NotesQueryDto queryDto) {
        return getContactNotesPage(contactId, queryDto, USER_3);
    }

    protected PageDto<NoteDto> user1GetReferralNotes(Long referralId, NotesQueryDto queryDto) {
        return getReferralNotesPage(referralId, queryDto, USER_1);
    }

    protected PageDto<NoteDto> user2GetReferralNotes(Long referralId, NotesQueryDto queryDto) {
        return getReferralNotesPage(referralId, queryDto, USER_2);
    }

    protected PageDto<NoteDto> user3GetReferralNotes(Long referralId, NotesQueryDto queryDto) {
        return getReferralNotesPage(referralId, queryDto, USER_3);
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

    protected void createContactNote(Long contactId, NoteDto dto, ComponentTestUser user) {
        var url = format(CONTACT_NOTES_URL, contactId);
        post(url, user, dto);
    }

    protected void createReferralNote(Long referralId, NoteDto dto, ComponentTestUser user) {
        var url = format(REFERRAL_NOTES_URL, referralId);
        post(url, user, dto);
    }

    protected void updateNote(Long noteId, NoteDto dto, ComponentTestUser user) {
        var url = format(NOTE_BY_ID_URL, noteId);
        put(url, user, dto);
    }

    protected void deleteNote(Long noteId, ComponentTestUser user) {
        var url = format(NOTE_BY_ID_URL, noteId);
        delete(url, user);
    }

    protected PageDto<NoteDto> getContactNotesPage(Long contactId, NotesQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(CONTACT_NOTES_URL, contactId);
        return getNotesPage(baseURl, queryDto, user);
    }

    protected PageDto<NoteDto> getReferralNotesPage(Long referralId, NotesQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(REFERRAL_NOTES_URL, referralId);
        return getNotesPage(baseURl, queryDto, user);
    }

    protected PageDto<NoteDto> getNotesPage(String baseURl, NotesQueryDto queryDto, ComponentTestUser user) {
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<NoteDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected NoteDto getNote(Long noteId, ComponentTestUser user) {
        var url = format(NOTE_BY_ID_URL, noteId);
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

    protected void user1DeleteNoteExpectNotFound(Long noteId) {
        deleteNoteExpectNotFound(noteId, USER_1);
    }

    protected void user2DeleteNoteExpectNotFound(Long noteId) {
        deleteNoteExpectNotFound(noteId, USER_2);
    }

    protected void user3DeleteNoteExpectNotFound(Long noteId) {
        deleteNoteExpectNotFound(noteId, USER_3);
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
        var url = format(NOTE_BY_ID_URL, noteId);
        putExpectNotFound(url, user, dto);
    }

    protected void deleteNoteExpectNotFound(Long noteId, ComponentTestUser user) {
        var url = format(NOTE_BY_ID_URL, noteId);
        deleteExpectNotFound(url, user);
    }

    protected void getNoteExpectNotFound(Long noteId, ComponentTestUser user) {
        var url = format(NOTE_BY_ID_URL, noteId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, NotesQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var filters = toUrlFilterParams(queryDto.getFilter());
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(filters) || isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(filters);
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }

    protected String toUrlFilterParams(NotesFilterDto filterDto) {
        var sb = new StringBuilder();
        if (nonNull(filterDto)) {
            if (isNotEmpty(filterDto.getGlobalFilter())) {
                sb.append("filter.globalFilter=");
                sb.append(filterDto.getGlobalFilter());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getText())) {
                sb.append("filter.text=");
                sb.append(filterDto.getText());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getCategories())) {
                sb.append("filter.categories=");
                sb.append(filterDto.getCategories());
                sb.append("&");
            }
        }
        return sb.toString();
    }
}
