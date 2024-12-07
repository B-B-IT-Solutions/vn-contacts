package cz.prm.repositories.common.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.contact.querydsl.QContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PredicateCriteriaUtilsTest {

    private BooleanBuilder mockPredicate;

    @BeforeEach
    void setUp() {
        mockPredicate = new BooleanBuilder();
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