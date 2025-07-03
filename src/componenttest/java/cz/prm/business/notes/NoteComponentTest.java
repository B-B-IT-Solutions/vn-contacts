package cz.prm.business.notes;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.arrayIncludesAllFilter;
import static cz.prm.utils.ComponentTestUtils.arrayIncludesFilter;
import static cz.prm.utils.ComponentTestUtils.emptyFilter;
import static cz.prm.utils.ComponentTestUtils.endsWithFilter;
import static cz.prm.utils.ComponentTestUtils.equalsFilter;
import static cz.prm.utils.ComponentTestUtils.notEmptyFilter;
import static cz.prm.utils.ComponentTestUtils.notEqualsFilter;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.startsWithFilter;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.NoteComponentTestUtils.noteDto;
import static cz.prm.utils.NoteComponentTestUtils.notesQueryDto;
import static cz.prm.utils.assertions.NoteComponentTestAssertions.assertNote;
import static cz.prm.utils.assertions.NoteComponentTestAssertions.assertNotes;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.note.NoteDto;
import org.junit.jupiter.api.Test;

public class NoteComponentTest extends NoteComponentTestBase {

    @Test
    void getNotesDataAccess() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createNotes(USER_1);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user1GetNotes(contactId, queryDto);
        assertNotes(user1Notes, pageDto);

        pageDto = user2GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Notes = createNotes(USER_2);
        userNote = user2Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user2GetNotes(contactId, queryDto);
        assertNotes(user2Notes, pageDto);

        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Notes = createNotes(USER_3);
        userNote = user3Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user3GetNotes(contactId, queryDto);
        assertNotes(user3Notes, pageDto);

        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getNotesPagination() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createNotes(USER_1, 21);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getNotesSorting() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createNotes(USER_1, 21);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        queryDto = notesQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetNotes(contactId, queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = notesQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(contactId)");
        pageDto = user1GetNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getContactId));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(contactId)");
        pageDto = user1GetNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getContactId).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getNotesFilters() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createNotes(USER_1, 21);
        var userNote1 = user1Notes.get(0);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        queryDto = notesQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = notesQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(userNote1.getText());
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = notesQueryDto();
        filter = queryDto.getFilter();

        filter.setTitle(userNote1.getText());
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setTitle(uuid());
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(startsWithFilter("Text"));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setTitle(startsWithFilter(userNote1.getText()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setTitle(startsWithFilter("Q"));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(startsWithFilter(uuid()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(endsWithFilter("End"));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setTitle(endsWithFilter(userNote1.getText()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setTitle(endsWithFilter("Q"));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(endsWithFilter(uuid()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(equalsFilter(userNote1.getText()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setTitle(equalsFilter("Q"));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(equalsFilter(" "));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(equalsFilter(uuid()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(notEqualsFilter(userNote1.getText()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setTitle(notEqualsFilter("Q"));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setTitle(notEqualsFilter(uuid()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setTitle(notEqualsFilter(" "));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setTitle(notEqualsFilter(uuid()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setTitle(emptyFilter());
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setTitle(notEmptyFilter());
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = notesQueryDto();
        filter = queryDto.getFilter();

        filter.setCategories(null);
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setCategories(arrayIncludesFilter(userNote1.getCategories()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setCategories(arrayIncludesFilter(newArrayList(uuid())));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setCategories(arrayIncludesAllFilter(userNote1.getCategories()));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setCategories(arrayIncludesAllFilter(newArrayList(uuid())));
        pageDto = user1GetNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getNote() {
        var note = createNote(USER_1);
        var noteId = note.getNoteId();

        var noteDto = user1GetNote(noteId);
        assertNote(note, noteDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

    @Test
    void createContactNote() {
        var contact = createContact(USER_1);
        var toCreateDto = noteDto();
        user1CreateContactNote(contact.getContactId(), toCreateDto);
        var note = getNoteFromDb(toCreateDto);
        var noteId = note.getNoteId();

        var createdDto = user1GetNote(noteId);
        assertNote(note, createdDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        contact = createContact(USER_2);
        toCreateDto = noteDto();
        user2CreateContactNote(contact.getContactId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user2GetNote(noteId);
        assertNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        contact = createContact(USER_3);
        toCreateDto = noteDto();
        user3CreateContactNote(contact.getContactId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user3GetNote(noteId);
        assertNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

    @Test
    void createReferralNote() {
        var referral = createReferral(USER_1);
        var toCreateDto = noteDto();
        user1CreateReferralNote(referral.getReferralId(), toCreateDto);
        var note = getNoteFromDb(toCreateDto);
        var noteId = note.getNoteId();

        var createdDto = user1GetNote(noteId);
        assertNote(note, createdDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        referral = createReferral(USER_2);
        toCreateDto = noteDto();
        user2CreateReferralNote(referral.getReferralId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user2GetNote(noteId);
        assertNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        referral = createReferral(USER_3);
        toCreateDto = noteDto();
        user3CreateReferralNote(referral.getReferralId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user3GetNote(noteId);
        assertNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

    @Test
    void updateNote() {
        var note = createNote(USER_1);
        var noteId = note.getNoteId();
        var updateDto = user1GetNote(noteId);

        updateDto.setText(uuid());
        user1UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertNote(note, updateDto);

        user2UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createNote(USER_2);
        noteId = note.getNoteId();
        updateDto = user2GetNote(noteId);

        updateDto.setText(uuid());
        user2UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createNote(USER_3);
        noteId = note.getNoteId();
        updateDto = user3GetNote(noteId);

        updateDto.setText(uuid());
        user3UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user2UpdateNoteExpectNotFound(noteId, updateDto);
    }

    @Test
    void deleteNote() {
        var note = createNote(USER_1);
        var noteId = note.getNoteId();
        var noteDto = user1GetNote(noteId);
        assertNote(note, noteDto);

        user2DeleteNoteExpectNotFound(noteId);
        user3DeleteNoteExpectNotFound(noteId);
        user1DeleteNote(noteId);
        user1GetNoteExpectNotFound(noteId);

        note = createNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertNote(note, noteDto);

        user1DeleteNoteExpectNotFound(noteId);
        user3DeleteNoteExpectNotFound(noteId);
        user2DeleteNote(noteId);
        user2GetNoteExpectNotFound(noteId);

        note = createNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertNote(note, noteDto);

        user1DeleteNoteExpectNotFound(noteId);
        user2DeleteNoteExpectNotFound(noteId);
        user3DeleteNote(noteId);
        user3GetNoteExpectNotFound(noteId);
    }
}
