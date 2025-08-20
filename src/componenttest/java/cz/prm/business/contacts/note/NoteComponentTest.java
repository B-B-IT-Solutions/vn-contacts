package cz.prm.business.contacts.note;

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
import static cz.prm.utils.assertions.contacts.NoteComponentTestAssertions.assertContactNote;
import static cz.prm.utils.assertions.contacts.NoteComponentTestAssertions.assertContactNotes;
import static cz.prm.utils.assertions.contacts.NoteComponentTestAssertions.assertReferralNote;
import static cz.prm.utils.assertions.contacts.NoteComponentTestAssertions.assertReferralNotes;
import static cz.prm.utils.data.contacts.NoteComponentTestUtils.noteDto;
import static cz.prm.utils.data.contacts.NoteComponentTestUtils.notesQueryDto;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.contacts.note.NoteDto;
import org.junit.jupiter.api.Test;

public class NoteComponentTest extends NoteComponentTestBase {

    @Test
    void getContactNotesDataAccess() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createContactNotes(USER_1);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user1GetContactNotes(contactId, queryDto);
        assertContactNotes(user1Notes, pageDto);

        pageDto = user2GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Notes = createContactNotes(USER_2);
        userNote = user2Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user2GetContactNotes(contactId, queryDto);
        assertContactNotes(user2Notes, pageDto);

        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Notes = createContactNotes(USER_3);
        userNote = user3Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user3GetContactNotes(contactId, queryDto);
        assertContactNotes(user3Notes, pageDto);

        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getReferralNotesDataAccess() {
        var queryDto = notesQueryDto();
        var referralId = randomLong();
        var pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createReferralNotes(USER_1);
        var userNote = user1Notes.get(0);
        referralId = userNote.getReferralId();

        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertReferralNotes(user1Notes, pageDto);

        pageDto = user2GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Notes = createReferralNotes(USER_2);
        userNote = user2Notes.get(0);
        referralId = userNote.getReferralId();

        pageDto = user2GetReferralNotes(referralId, queryDto);
        assertReferralNotes(user2Notes, pageDto);

        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Notes = createReferralNotes(USER_3);
        userNote = user3Notes.get(0);
        referralId = userNote.getReferralId();

        pageDto = user3GetReferralNotes(referralId, queryDto);
        assertReferralNotes(user3Notes, pageDto);

        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getContactNotesPagination() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createContactNotes(USER_1, 21);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getReferralNotesPagination() {
        var queryDto = notesQueryDto();
        var referralId = randomLong();
        var pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createReferralNotes(USER_1, 21);
        var userNote = user1Notes.get(0);
        referralId = userNote.getReferralId();

        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getContactNotesSorting() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createContactNotes(USER_1, 21);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        queryDto = notesQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetContactNotes(contactId, queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = notesQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetContactNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetContactNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetContactNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetContactNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(text)");
        pageDto = user1GetContactNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getText));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(text)");
        pageDto = user1GetContactNotes(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getText).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getReferralNotesSorting() {
        var queryDto = notesQueryDto();
        var referralId = randomLong();
        var pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createReferralNotes(USER_1, 21);
        var userNote = user1Notes.get(0);
        referralId = userNote.getReferralId();

        queryDto = notesQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetReferralNotes(referralId, queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = notesQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetReferralNotes(referralId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetReferralNotes(referralId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetReferralNotes(referralId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetReferralNotes(referralId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(text)");
        pageDto = user1GetReferralNotes(referralId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getText));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(text)");
        pageDto = user1GetReferralNotes(referralId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(NoteDto::getText).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getContactNotesFilters() {
        var queryDto = notesQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createContactNotes(USER_1, 21);
        var userNote1 = user1Notes.get(0);
        var userNote = user1Notes.get(0);
        contactId = userNote.getContactId();

        queryDto = notesQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = notesQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(userNote1.getText());
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = notesQueryDto();
        filter = queryDto.getFilter();

        filter.setText(userNote1.getText());
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(uuid());
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(startsWithFilter("Text"));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(startsWithFilter(userNote1.getText()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(startsWithFilter("Q"));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(startsWithFilter(uuid()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(endsWithFilter("End"));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(endsWithFilter(userNote1.getText()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(endsWithFilter("Q"));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(endsWithFilter(uuid()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(equalsFilter(userNote1.getText()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(equalsFilter("Q"));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(equalsFilter(" "));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(equalsFilter(uuid()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(notEqualsFilter(userNote1.getText()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setText(notEqualsFilter("Q"));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(notEqualsFilter(uuid()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(notEqualsFilter(" "));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(notEqualsFilter(uuid()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(emptyFilter());
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(notEmptyFilter());
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = notesQueryDto();
        filter = queryDto.getFilter();

        filter.setCategories(null);
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setCategories(arrayIncludesFilter(userNote1.getCategories()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setCategories(arrayIncludesFilter(newArrayList(uuid())));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setCategories(arrayIncludesAllFilter(userNote1.getCategories()));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setCategories(arrayIncludesAllFilter(newArrayList(uuid())));
        pageDto = user1GetContactNotes(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getReferralNotesFilters() {
        var queryDto = notesQueryDto();
        var referralId = randomLong();
        var pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Notes = createReferralNotes(USER_1, 21);
        var userNote1 = user1Notes.get(0);
        var userNote = user1Notes.get(0);
        referralId = userNote.getReferralId();

        queryDto = notesQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = notesQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(userNote1.getText());
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = notesQueryDto();
        filter = queryDto.getFilter();

        filter.setText(userNote1.getText());
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(uuid());
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(startsWithFilter("Text"));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(startsWithFilter(userNote1.getText()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(startsWithFilter("Q"));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(startsWithFilter(uuid()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(endsWithFilter("End"));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(endsWithFilter(userNote1.getText()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(endsWithFilter("Q"));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(endsWithFilter(uuid()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(equalsFilter(userNote1.getText()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setText(equalsFilter("Q"));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(equalsFilter(" "));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(equalsFilter(uuid()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(notEqualsFilter(userNote1.getText()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setText(notEqualsFilter("Q"));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(notEqualsFilter(uuid()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(notEqualsFilter(" "));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(notEqualsFilter(uuid()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setText(emptyFilter());
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setText(notEmptyFilter());
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = notesQueryDto();
        filter = queryDto.getFilter();

        filter.setCategories(null);
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setCategories(arrayIncludesFilter(userNote1.getCategories()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setCategories(arrayIncludesFilter(newArrayList(uuid())));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setCategories(arrayIncludesAllFilter(userNote1.getCategories()));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setCategories(arrayIncludesAllFilter(newArrayList(uuid())));
        pageDto = user1GetReferralNotes(referralId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getContactNote() {
        var note = createContactNote(USER_1);
        var noteId = note.getNoteId();

        var noteDto = user1GetNote(noteId);
        assertContactNote(note, noteDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createContactNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertContactNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createContactNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertContactNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

    @Test
    void getReferralNote() {
        var note = createReferralNote(USER_1);
        var noteId = note.getNoteId();

        var noteDto = user1GetNote(noteId);
        assertReferralNote(note, noteDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createReferralNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertReferralNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        note = createReferralNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertReferralNote(note, noteDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

    @Test
    void createContactNote() {
        var contact = createDecoratedContact(USER_1);
        var toCreateDto = noteDto();
        user1CreateContactNote(contact.getContactId(), toCreateDto);
        var note = getNoteFromDb(toCreateDto);
        var noteId = note.getNoteId();

        var createdDto = user1GetNote(noteId);
        assertContactNote(note, createdDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        contact = createDecoratedContact(USER_2);
        toCreateDto = noteDto();
        user2CreateContactNote(contact.getContactId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user2GetNote(noteId);
        assertContactNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        contact = createDecoratedContact(USER_3);
        toCreateDto = noteDto();
        user3CreateContactNote(contact.getContactId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user3GetNote(noteId);
        assertContactNote(note, createdDto);
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
        assertReferralNote(note, createdDto);
        user2GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        referral = createReferral(USER_2);
        toCreateDto = noteDto();
        user2CreateReferralNote(referral.getReferralId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user2GetNote(noteId);
        assertReferralNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user3GetNoteExpectNotFound(noteId);

        referral = createReferral(USER_3);
        toCreateDto = noteDto();
        user3CreateReferralNote(referral.getReferralId(), toCreateDto);
        note = getNoteFromDb(toCreateDto);
        noteId = note.getNoteId();

        createdDto = user3GetNote(noteId);
        assertReferralNote(note, createdDto);
        user1GetNoteExpectNotFound(noteId);
        user2GetNoteExpectNotFound(noteId);
    }

    @Test
    void updateContactNote() {
        var note = createContactNote(USER_1);
        var noteId = note.getNoteId();
        var updateDto = user1GetNote(noteId);

        updateDto.setText(uuid());
        user1UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertContactNote(note, updateDto);

        user2UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createContactNote(USER_2);
        noteId = note.getNoteId();
        updateDto = user2GetNote(noteId);

        updateDto.setText(uuid());
        user2UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertContactNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createContactNote(USER_3);
        noteId = note.getNoteId();
        updateDto = user3GetNote(noteId);

        updateDto.setText(uuid());
        user3UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertContactNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user2UpdateNoteExpectNotFound(noteId, updateDto);
    }

    @Test
    void updateReferralNote() {
        var note = createReferralNote(USER_1);
        var noteId = note.getNoteId();
        var updateDto = user1GetNote(noteId);

        updateDto.setText(uuid());
        user1UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertReferralNote(note, updateDto);

        user2UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createReferralNote(USER_2);
        noteId = note.getNoteId();
        updateDto = user2GetNote(noteId);

        updateDto.setText(uuid());
        user2UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertReferralNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user3UpdateNoteExpectNotFound(noteId, updateDto);

        note = createReferralNote(USER_3);
        noteId = note.getNoteId();
        updateDto = user3GetNote(noteId);

        updateDto.setText(uuid());
        user3UpdateNote(noteId, updateDto);
        note = getNoteFromDb(updateDto);
        assertReferralNote(note, updateDto);

        user1UpdateNoteExpectNotFound(noteId, updateDto);
        user2UpdateNoteExpectNotFound(noteId, updateDto);
    }

    @Test
    void deleteContactNote() {
        var note = createContactNote(USER_1);
        var noteId = note.getNoteId();
        var noteDto = user1GetNote(noteId);
        assertContactNote(note, noteDto);

        user2DeleteNoteExpectNotFound(noteId);
        user3DeleteNoteExpectNotFound(noteId);
        user1DeleteNote(noteId);
        user1GetNoteExpectNotFound(noteId);

        note = createContactNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertContactNote(note, noteDto);

        user1DeleteNoteExpectNotFound(noteId);
        user3DeleteNoteExpectNotFound(noteId);
        user2DeleteNote(noteId);
        user2GetNoteExpectNotFound(noteId);

        note = createContactNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertContactNote(note, noteDto);

        user1DeleteNoteExpectNotFound(noteId);
        user2DeleteNoteExpectNotFound(noteId);
        user3DeleteNote(noteId);
        user3GetNoteExpectNotFound(noteId);
    }

    @Test
    void deleteReferralNote() {
        var note = createReferralNote(USER_1);
        var noteId = note.getNoteId();
        var noteDto = user1GetNote(noteId);
        assertReferralNote(note, noteDto);

        user2DeleteNoteExpectNotFound(noteId);
        user3DeleteNoteExpectNotFound(noteId);
        user1DeleteNote(noteId);
        user1GetNoteExpectNotFound(noteId);

        note = createReferralNote(USER_2);
        noteId = note.getNoteId();
        noteDto = user2GetNote(noteId);
        assertReferralNote(note, noteDto);

        user1DeleteNoteExpectNotFound(noteId);
        user3DeleteNoteExpectNotFound(noteId);
        user2DeleteNote(noteId);
        user2GetNoteExpectNotFound(noteId);

        note = createReferralNote(USER_3);
        noteId = note.getNoteId();
        noteDto = user3GetNote(noteId);
        assertReferralNote(note, noteDto);

        user1DeleteNoteExpectNotFound(noteId);
        user2DeleteNoteExpectNotFound(noteId);
        user3DeleteNote(noteId);
        user3GetNoteExpectNotFound(noteId);
    }
}
