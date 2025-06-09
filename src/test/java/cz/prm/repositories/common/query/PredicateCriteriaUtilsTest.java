package cz.prm.repositories.common.query;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.domain.task.Priority.HIGH;
import static cz.prm.domain.task.Priority.LOW;
import static cz.prm.domain.task.Priority.MEDIUM;
import static cz.prm.domain.task.Status.IN_PROGRESS;
import static cz.prm.domain.task.Status.TO_DO;
import static cz.prm.domain.task.Status.WAITING;
import static cz.prm.utils.TimeUtils.useMockTimeZone;
import static cz.prm.utils.TimeUtils.useSystemDefaultTimeZone;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.contact.querydsl.QContact;
import cz.prm.domain.note.querydsl.QNote;
import cz.prm.domain.task.Priority;
import cz.prm.domain.task.Status;
import cz.prm.domain.task.querydsl.QTask;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PredicateCriteriaUtilsTest {

    private BooleanBuilder mockPredicate;

    @BeforeEach
    void setUp() {
        useMockTimeZone();
        mockPredicate = new BooleanBuilder();
    }

    @AfterEach
    void tearDown() {
        useSystemDefaultTimeZone();
    }

    @Test
    void applyCriteriaListPathArrayIncludesOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.labels, "label_001");
        var queryPattern = "label_001 in contact.labels";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludes(category_002)");
        queryPattern = "label_001 in contact.labels && category_002 in note.categories";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludes(category_003)");
        queryPattern = "label_001 in contact.labels && category_002 in note.categories && category_003 in note.categories";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaListPathArrayIncludesOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.labels, "label_001,label_002,label_003");
        var queryPattern = "label_001,label_002,label_003 in contact.labels";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludes(category_001,category_002,category_003)");
        queryPattern = "label_001,label_002,label_003 in contact.labels && (category_001 in note.categories || category_002 in note.categories || "
            + "category_003 in note.categories)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludes(category_004,category_005,category_006)");
        queryPattern = "label_001,label_002,label_003 in contact.labels && (category_001 in note.categories || category_002 in note.categories || "
            + "category_003 in note.categories) && (category_004 in note.categories || category_005 in note.categories || category_006 in note"
            + ".categories)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaListPathArrayIncludesAllOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.labels, "label_001");
        var queryPattern = "label_001 in contact.labels";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludesAll(category_002)");
        queryPattern = "label_001 in contact.labels && category_002 in note.categories";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludesAll(category_003)");
        queryPattern = "label_001 in contact.labels && category_002 in note.categories && category_003 in note.categories";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaListPathArrayIncludesAllOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.labels, "label_001,label_002,label_003");
        var queryPattern = "label_001,label_002,label_003 in contact.labels";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories,
            "arrIncludesAll(category_001,category_002,category_003)");
        queryPattern = "label_001,label_002,label_003 in contact.labels && category_001 in note.categories && category_002 in note.categories && "
            + "category_003 in note.categories";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories,
            "arrIncludesAll(category_004,category_005,category_006)");
        queryPattern = "label_001,label_002,label_003 in contact.labels && category_001 in note.categories && category_002 in note.categories && "
            + "category_003 in note.categories && category_004 in note.categories && category_005 in note.categories && category_006 in note"
            + ".categories";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEnumPathArrayIncludesOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.status, "TO_DO");
        var queryPattern = "task.status = 0";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.priority, "arrIncludes(HIGH)");
        queryPattern = "task.status = 0 && task.priority = 0";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.priority, "arrIncludes(LOW)");
        queryPattern = "task.status = 0 && task.priority = 0 && task.priority = 2";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEnumPathArrayIncludesOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.status, "TO_DO,WAITING,IN_PROGRESS");
        var queryPattern = "task.status = 0 || task.status = 1 || task.status = 2";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.priority, "arrIncludes(HIGH,MEDIUM,LOW)");
        queryPattern = "(task.status = 0 || task.status = 1 || task.status = 2) && (task.priority = 0 || task.priority = 1 || task.priority = 2)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.priority, "arrIncludes(HIGH,LOW)");
        queryPattern = "(task.status = 0 || task.status = 1 || task.status = 2) && (task.priority = 0 || task.priority = 1 || task.priority = 2) &&"
            + " (task.priority = 0 || task.priority = 2)";
        assertThat(predicate).hasToString(queryPattern);
    }

//    @Test
//    void applyCriteriaEnumPathArrayIncludesAllOperation() {
//        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.labels, "label_001");
//        var queryPattern = "label_001 in contact.labels";
//        assertThat(predicate).hasToString(queryPattern);
//
//        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludesAll(category_002)");
//        queryPattern = "label_001 in contact.labels && category_002 in note.categories";
//        assertThat(predicate).hasToString(queryPattern);
//
//        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories, "arrIncludesAll(category_003)");
//        queryPattern = "label_001 in contact.labels && category_002 in note.categories && category_003 in note.categories";
//        assertThat(predicate).hasToString(queryPattern);
//    }
//
//    @Test
//    void applyCriteriaEnumPathArrayIncludesAllOperationWithMultipleFilterValues() {
//        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.labels, "label_001,label_002,label_003");
//        var queryPattern = "label_001,label_002,label_003 in contact.labels";
//        assertThat(predicate).hasToString(queryPattern);
//
//        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories,
//            "arrIncludesAll(category_001,category_002,category_003)");
//        queryPattern = "label_001,label_002,label_003 in contact.labels && category_001 in note.categories && category_002 in note.categories && "
//            + "category_003 in note.categories";
//        assertThat(predicate).hasToString(queryPattern);
//
//        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QNote.note.categories,
//            "arrIncludesAll(category_004,category_005,category_006)");
//        queryPattern = "label_001,label_002,label_003 in contact.labels && category_001 in note.categories && category_002 in note.categories && "
//            + "category_003 in note.categories && category_004 in note.categories && category_005 in note.categories && category_006 in note"
//            + ".categories";
//        assertThat(predicate).hasToString(queryPattern);
//    }

    @Test
    void applyCriteriaDateEqualsOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "15 Dec 2024", Instant.class);
        var queryPattern = "task.dueDate = 2024-12-14T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "equals(17 Dec 2024)", Instant.class);
        queryPattern = "task.dueDate = 2024-12-14T23:00:00Z && task.lastEditDate = 2024-12-16T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "equals(19 Dec 2024)", Instant.class);
        queryPattern = "task.dueDate = 2024-12-14T23:00:00Z && task.lastEditDate = 2024-12-16T23:00:00Z && task.creationDate = 2024-12-18T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateEqualsOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "15 Dec 2024", Instant.class);
        var queryPattern = "task.dueDate = 2024-12-14T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "equals(10 Dec 2024,11 Dec 2024,12 Dec 2024)",
            Instant.class);
        queryPattern = "task.dueDate = 2024-12-14T23:00:00Z && (task.lastEditDate = 2024-12-09T23:00:00Z || task.lastEditDate = "
            + "2024-12-10T23:00:00Z || task.lastEditDate = 2024-12-11T23:00:00Z)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "equals(21 Dec 2024,22 Dec 2024,23 Dec 2024)",
            Instant.class);
        queryPattern =
            "task.dueDate = 2024-12-14T23:00:00Z && (task.lastEditDate = 2024-12-09T23:00:00Z || task.lastEditDate = 2024-12-10T23:00:00Z || task"
                + ".lastEditDate = 2024-12-11T23:00:00Z) && (task.creationDate = 2024-12-20T23:00:00Z || task.creationDate = 2024-12-21T23:00:00Z "
                + "|| task.creationDate = 2024-12-22T23:00:00Z)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateNotEqualsOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "15 Dec 2024", LocalDate.class);
        var queryPattern = "task.dueDate = 2024-12-15";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "notEquals(17 Dec 2024)", LocalDate.class);
        queryPattern = "task.dueDate = 2024-12-15 && task.lastEditDate != 2024-12-17";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "notEquals(19 Dec 2024)", LocalDate.class);
        queryPattern = "task.dueDate = 2024-12-15 && task.lastEditDate != 2024-12-17 && task.creationDate != 2024-12-19";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateNotEqualsOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "notEquals(10 Dec 2024,11 Dec 2024,12 Dec 2024)",
            LocalDate.class);
        var queryPattern = "task.lastEditDate != 2024-12-10 || task.lastEditDate != 2024-12-11 || task.lastEditDate != 2024-12-12";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "notEquals(21 Dec 2024,22 Dec 2024,23 Dec 2024)",
            LocalDate.class);
        queryPattern = "(task.lastEditDate != 2024-12-10 || task.lastEditDate != 2024-12-11 || task.lastEditDate != 2024-12-12) && (task"
            + ".creationDate != 2024-12-21 || task.creationDate != 2024-12-22 || task.creationDate != 2024-12-23)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateGreaterThanOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "greaterThan(15 Dec 2024)", LocalDateTime.class);
        var queryPattern = "task.dueDate > 2024-12-15T00:00";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "greaterThan(17 Dec 2024)", LocalDateTime.class);
        queryPattern = "task.dueDate > 2024-12-15T00:00 && task.lastEditDate > 2024-12-17T00:00";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "greaterThan(19 Dec 2024)", LocalDateTime.class);
        queryPattern = "task.dueDate > 2024-12-15T00:00 && task.lastEditDate > 2024-12-17T00:00 && task.creationDate > 2024-12-19T00:00";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateGreaterThanOrEqualsToOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "greaterThanOrEqualTo(15 Dec 2024)", Instant.class);
        var queryPattern = "task.dueDate >= 2024-12-14T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "greaterThanOrEqualTo(17 Dec 2024)", Instant.class);
        queryPattern = "task.dueDate >= 2024-12-14T23:00:00Z && task.lastEditDate >= 2024-12-16T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "greaterThanOrEqualTo(19 Dec 2024)", Instant.class);
        queryPattern =
            "task.dueDate >= 2024-12-14T23:00:00Z && task.lastEditDate >= 2024-12-16T23:00:00Z && task.creationDate >= " + "2024-12-18T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateLessThanOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "lessThan(15 Dec 2024)", Instant.class);
        var queryPattern = "task.dueDate < 2024-12-14T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "lessThan(17 Dec 2024)", Instant.class);
        queryPattern = "task.dueDate < 2024-12-14T23:00:00Z && task.lastEditDate < 2024-12-16T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "lessThan(19 Dec 2024)", Instant.class);
        queryPattern = "task.dueDate < 2024-12-14T23:00:00Z && task.lastEditDate < 2024-12-16T23:00:00Z && task.creationDate < 2024-12-18T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateLessThanOrEqualsToOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "lessThanOrEqualTo(15 Dec 2024)", Instant.class);
        var queryPattern = "task.dueDate <= 2024-12-14T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "lessThanOrEqualTo(17 Dec 2024)", LocalDate.class);
        queryPattern = "task.dueDate <= 2024-12-14T23:00:00Z && task.lastEditDate <= 2024-12-17";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "lessThanOrEqualTo(19 Dec 2024)",
            LocalDateTime.class);
        queryPattern = "task.dueDate <= 2024-12-14T23:00:00Z && task.lastEditDate <= 2024-12-17 && task.creationDate <= 2024-12-19T00:00";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaDateBetweenOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "between()", Instant.class);
        var queryPattern = "com.querydsl.core.BooleanBuilder@0";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "between(,)", Instant.class);
        queryPattern = "com.querydsl.core.BooleanBuilder@0";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "between(15 Dec 2024)", LocalDate.class);
        queryPattern = "com.querydsl.core.BooleanBuilder@0";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "between(15 Dec 2024,)", LocalDateTime.class);
        queryPattern = "com.querydsl.core.BooleanBuilder@0";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.dueDate, "between(15 Dec 2024,17 Dec 2024)", Instant.class);
        queryPattern = "task.dueDate between 2024-12-14T23:00:00Z and 2024-12-16T23:00:00Z";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.lastEditDate, "between(17 Dec 2024, 19 Dec 2024)",
            LocalDate.class);
        queryPattern = "task.dueDate between 2024-12-14T23:00:00Z and 2024-12-16T23:00:00Z && task.lastEditDate between 2024-12-17 and 2024-12-19";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QTask.task.creationDate, "between(11 Dec 2024, 15 Dec 2024)",
            LocalDateTime.class);
        queryPattern = "task.dueDate between 2024-12-14T23:00:00Z and 2024-12-16T23:00:00Z && task.lastEditDate between 2024-12-17 and 2024-12-19 "
            + "&& task.creationDate between 2024-12-11T00:00 and 2024-12-15T00:00";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaNoOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "firstName_001");
        var queryPattern = "containsIc(contact.firstName,firstName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "lastName_001");
        queryPattern = "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "nickName_001");
        queryPattern = "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001) && containsIc(contact.nickName,"
            + "nickName_001)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaContainsOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "contains(firstName_001)");
        var queryPattern = "containsIc(contact.firstName,firstName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "contains(lastName_001)");
        queryPattern = "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "contains(nickName_001)");
        queryPattern = "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001) && containsIc(contact.nickName,"
            + "nickName_001)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaContainsOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "contains(firstName_001,firstName_002,firstName_003)");
        var queryPattern = "containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact"
            + ".firstName,firstName_003)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "contains(lastName_001,lastName_002,lastName_003)");
        queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact.firstName,"
            + "firstName_003)) && (containsIc(contact.lastName,lastName_001) || containsIc(contact.lastName,lastName_002) || containsIc(contact"
            + ".lastName,lastName_003))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "containsIc(nickName_001,nickName_002,nickName_003)");
        queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact.firstName,"
            + "firstName_003)) && (containsIc(contact.lastName,lastName_001) || containsIc(contact.lastName,lastName_002) || containsIc(contact"
            + ".lastName,lastName_003)) && (containsIc(contact.nickName,nickName_001) || containsIc(contact.nickName,nickName_002) || containsIc"
            + "(contact.nickName," + "nickName_003))";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaNotContainsOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "notContains(firstName_001)");
        var queryPattern = "!containsIc(contact.firstName,firstName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "notContains(lastName_001)");
        queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.lastName,lastName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "notContains(nickName_001)");
        queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.lastName,lastName_001) && !containsIc(contact.nickName,"
            + "nickName_001)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaNotContainsOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "notContains(firstName_001,firstName_002,firstName_003)");
        var queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && !containsIc(contact"
            + ".firstName,firstName_003)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "notContains(lastName_001,lastName_002,lastName_003)");
        queryPattern =
            "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && !containsIc(contact.firstName,"
                + "firstName_003) && !containsIc(contact.lastName,lastName_001) && !containsIc(contact.lastName,lastName_002) && !containsIc(contact"
                + ".lastName,lastName_003)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "notContains(nickName_001,nickName_002,nickName_003)");
        queryPattern =
            "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && !containsIc(contact.firstName,"
                + "firstName_003) && !containsIc(contact.lastName,lastName_001) && !containsIc(contact.lastName,lastName_002) && !containsIc(contact"
                + ".lastName,lastName_003) && !containsIc(contact.nickName,nickName_001) && !containsIc(contact.nickName,nickName_002) && "
                + "!containsIc(contact" + ".nickName," + "nickName_003)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaStartsWithOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "startsWith(firstName_001)");
        var queryPattern = "startsWithIgnoreCase(contact.firstName,firstName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "startsWith(lastName_001)");
        queryPattern = "startsWithIgnoreCase(contact.firstName,firstName_001) && startsWithIgnoreCase(contact.lastName,lastName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "startsWith(nickName_001)");
        queryPattern =
            "startsWithIgnoreCase(contact.firstName,firstName_001) && startsWithIgnoreCase(contact.lastName,lastName_001) && startsWithIgnoreCase"
                + "(contact.nickName,nickName_001)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaStartsWithOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "startsWith(firstName_001,firstName_002,firstName_003)");
        var queryPattern =
            "startsWithIgnoreCase(contact.firstName,firstName_001) || startsWithIgnoreCase(contact.firstName,firstName_002) || startsWithIgnoreCase"
                + "(contact.firstName,firstName_003)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "startsWith(lastName_001,lastName_002,lastName_003)");
        queryPattern = "(startsWithIgnoreCase(contact.firstName,firstName_001) || startsWithIgnoreCase(contact.firstName,firstName_002) || "
            + "startsWithIgnoreCase(contact.firstName,firstName_003)) && (startsWithIgnoreCase(contact.lastName,lastName_001) || "
            + "startsWithIgnoreCase(contact.lastName,lastName_002) || startsWithIgnoreCase(contact" + ".lastName,lastName_003))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "startsWith(nickName_001,nickName_002,nickName_003)");
        queryPattern = "(startsWithIgnoreCase(contact.firstName,firstName_001) || startsWithIgnoreCase(contact.firstName,firstName_002) || "
            + "startsWithIgnoreCase(contact.firstName,firstName_003)) && (startsWithIgnoreCase(contact.lastName,lastName_001) || "
            + "startsWithIgnoreCase(contact.lastName,lastName_002) || startsWithIgnoreCase(contact.lastName,lastName_003)) && (startsWithIgnoreCase"
            + "(contact.nickName,nickName_001) || startsWithIgnoreCase(contact.nickName,nickName_002) || startsWithIgnoreCase(contact.nickName,"
            + "nickName_003))";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEndsWithOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "endsWith(firstName_001)");
        var queryPattern = "endsWithIgnoreCase(contact.firstName,firstName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "endsWith(lastName_001)");
        queryPattern = "endsWithIgnoreCase(contact.firstName,firstName_001) && endsWithIgnoreCase(contact.lastName,lastName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "endsWith(nickName_001)");
        queryPattern =
            "endsWithIgnoreCase(contact.firstName,firstName_001) && endsWithIgnoreCase(contact.lastName,lastName_001) && endsWithIgnoreCase(contact"
                + ".nickName,nickName_001)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEndsWithOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "endsWith(firstName_001,firstName_002,firstName_003)");
        var queryPattern =
            "endsWithIgnoreCase(contact.firstName,firstName_001) || endsWithIgnoreCase(contact.firstName,firstName_002) || endsWithIgnoreCase(contact"
                + ".firstName,firstName_003)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "endsWith(lastName_001,lastName_002,lastName_003)");
        queryPattern =
            "(endsWithIgnoreCase(contact.firstName,firstName_001) || endsWithIgnoreCase(contact.firstName,firstName_002) || endsWithIgnoreCase"
                + "(contact.firstName,firstName_003)) && (endsWithIgnoreCase(contact.lastName,lastName_001) || endsWithIgnoreCase(contact.lastName,"
                + "lastName_002) || endsWithIgnoreCase(contact.lastName,lastName_003))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "endsWith(nickName_001,nickName_002,nickName_003)");
        queryPattern =
            "(endsWithIgnoreCase(contact.firstName,firstName_001) || endsWithIgnoreCase(contact.firstName,firstName_002) || endsWithIgnoreCase"
                + "(contact.firstName,firstName_003)) && (endsWithIgnoreCase(contact.lastName,lastName_001) || endsWithIgnoreCase(contact.lastName,"
                + "lastName_002) || endsWithIgnoreCase(contact.lastName,lastName_003)) && (endsWithIgnoreCase(contact.nickName,nickName_001) || "
                + "endsWithIgnoreCase(contact.nickName,nickName_002) || endsWithIgnoreCase(contact.nickName,nickName_003))";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEqualsWithOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "equals(firstName_001)");
        var queryPattern = "eqIc(contact.firstName,firstName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "equals(lastName_001)");
        queryPattern = "eqIc(contact.firstName,firstName_001) && eqIc(contact.lastName,lastName_001)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "equals(nickName_001)");
        queryPattern = "eqIc(contact.firstName,firstName_001) && eqIc(contact.lastName,lastName_001) && eqIc(contact.nickName,nickName_001)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEqualsWithOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "equals(firstName_001,firstName_002,firstName_003)");
        var queryPattern = "eqIc(contact.firstName,firstName_001) || eqIc(contact.firstName,firstName_002) || eqIc(contact.firstName,firstName_003)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "equals(lastName_001,lastName_002,lastName_003)");
        queryPattern =
            "(eqIc(contact.firstName,firstName_001) || eqIc(contact.firstName,firstName_002) || eqIc(contact.firstName,firstName_003)) && (eqIc"
                + "(contact.lastName,lastName_001) || eqIc(contact.lastName,lastName_002) || eqIc(contact.lastName,lastName_003))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "equals(nickName_001,nickName_002,nickName_003)");
        queryPattern =
            "(eqIc(contact.firstName,firstName_001) || eqIc(contact.firstName,firstName_002) || eqIc(contact.firstName,firstName_003)) && (eqIc"
                + "(contact.lastName,lastName_001) || eqIc(contact.lastName,lastName_002) || eqIc(contact.lastName,lastName_003)) && (eqIc(contact"
                + ".nickName,nickName_001) || eqIc(contact.nickName,nickName_002) || eqIc(contact.nickName,nickName_003))";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaNotEqualsWithOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "notEquals(firstName_001)");
        var queryPattern = "!(eqIc(contact.firstName,firstName_001))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "notEquals(lastName_001)");
        queryPattern = "!(eqIc(contact.firstName,firstName_001)) && !(eqIc(contact.lastName,lastName_001))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "notEquals(nickName_001)");
        queryPattern = "!(eqIc(contact.firstName,firstName_001)) && !(eqIc(contact.lastName,lastName_001)) && !(eqIc(contact.nickName,nickName_001))";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaNotEqualsWithOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "notEquals(firstName_001,firstName_002,firstName_003)");
        var queryPattern =
            "!(eqIc(contact.firstName,firstName_001)) && !(eqIc(contact.firstName,firstName_002)) && !(eqIc(contact.firstName," + "firstName_003))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "notEquals(lastName_001,lastName_002,lastName_003)");
        queryPattern =
            "!(eqIc(contact.firstName,firstName_001)) && !(eqIc(contact.firstName,firstName_002)) && !(eqIc(contact.firstName,firstName_003)) &&"
                + " !(eqIc(contact.lastName,lastName_001)) && !(eqIc(contact.lastName,lastName_002)) && !(eqIc(contact.lastName,lastName_003))";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "notEquals(nickName_001,nickName_002,nickName_003)");
        queryPattern =
            "!(eqIc(contact.firstName,firstName_001)) && !(eqIc(contact.firstName,firstName_002)) && !(eqIc(contact.firstName,firstName_003)) &&"
                + " !(eqIc(contact.lastName,lastName_001)) && !(eqIc(contact.lastName,lastName_002)) && !(eqIc(contact.lastName,lastName_003)) &&"
                + " !(eqIc(contact.nickName,nickName_001)) && !(eqIc(contact.nickName,nickName_002)) && !(eqIc(contact.nickName,nickName_003))";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaEmpyWithOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "empty( )");
        var queryPattern = "empty(contact.firstName)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "empty( )");
        queryPattern = "empty(contact.firstName) && empty(contact.lastName)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "empty( )");
        queryPattern = "empty(contact.firstName) && empty(contact.lastName) && empty(contact.nickName)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void applyCriteriaNotEmpyWithOperation() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "notEmpty( )");
        var queryPattern = "!empty(contact.firstName)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "notEmpty( )");
        queryPattern = "!empty(contact.firstName) && !empty(contact.lastName)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName, "notEmpty( )");
        queryPattern = "!empty(contact.firstName) && !empty(contact.lastName) && !empty(contact.nickName)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void containsAndNotContainsOperationFilter() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "contains(firstName_001)+notContains(firstName_002)");
        var queryPattern = "containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "contains(lastName_001)+notContains(lastName_002)");
        queryPattern = "containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && containsIc(contact.lastName,"
            + "lastName_001) && !containsIc(contact.lastName,lastName_002)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "contains(nickName_001)+notContains(nickName_002)");
        queryPattern = "containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && containsIc(contact.lastName,"
            + "lastName_001) && !containsIc(contact.lastName,lastName_002) && containsIc(contact.nickName,nickName_001) && !containsIc(contact"
            + ".nickName,nickName_002)";
        assertThat(predicate).hasToString(queryPattern);
    }

    @Test
    void containsAndNotContainsOperationWithMultipleFilterValues() {
        var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
            "contains(firstName_001,firstName_002,firstName_003)+notContains(firstName_004,firstName_005,firstName_006)");
        var queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact"
            + ".firstName,firstName_003)) && !containsIc(contact.firstName,firstName_004) && !containsIc(contact.firstName,firstName_005) && "
            + "!containsIc(contact.firstName,firstName_006)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
            "contains(lastName_001,lastName_002,lastName_003)+notContains(lastName_004,lastName_005,lastName_006)");
        queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact.firstName,"
            + "firstName_003)) && !containsIc(contact.firstName,firstName_004) && !containsIc(contact.firstName,firstName_005) && !containsIc(contact"
            + ".firstName,firstName_006) && (containsIc(contact.lastName,lastName_001) || containsIc(contact.lastName,lastName_002) || containsIc"
            + "(contact.lastName,lastName_003)) && !containsIc(contact.lastName,lastName_004) && !containsIc(contact.lastName,lastName_005) && "
            + "!containsIc(contact.lastName,lastName_006)";
        assertThat(predicate).hasToString(queryPattern);

        predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.nickName,
            "contains(nickName_001,nickName_002,nickName_003)+notContains(nickName_004,nickName_005,nickName_006)");
        queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact.firstName,"
            + "firstName_003)) && !containsIc(contact.firstName,firstName_004) && !containsIc(contact.firstName,firstName_005) && !containsIc(contact"
            + ".firstName,firstName_006) && (containsIc(contact.lastName,lastName_001) || containsIc(contact.lastName,lastName_002) || containsIc"
            + "(contact.lastName,lastName_003)) && !containsIc(contact.lastName,lastName_004) && !containsIc(contact.lastName,lastName_005) && "
            + "!containsIc(contact.lastName,lastName_006) && (containsIc(contact.nickName,nickName_001) || containsIc(contact.nickName,"
            + "nickName_002) || containsIc(contact.nickName,nickName_003)) && !containsIc(contact.nickName,nickName_004) && !containsIc(contact"
            + ".nickName,nickName_005) && !containsIc" + "(contact.nickName,nickName_006)";
        assertThat(predicate).hasToString(queryPattern);
    }
}