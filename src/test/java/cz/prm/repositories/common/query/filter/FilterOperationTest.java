package cz.prm.repositories.common.query.filter;

import static cz.prm.repositories.common.query.filter.FilterOperation.ARRAY_INCLUDES;
import static cz.prm.repositories.common.query.filter.FilterOperation.ARRAY_INCLUDES_ALL;
import static cz.prm.repositories.common.query.filter.FilterOperation.BETWEEN;
import static cz.prm.repositories.common.query.filter.FilterOperation.BETWEEN_INCLUSIVE;
import static cz.prm.repositories.common.query.filter.FilterOperation.CONTAINS;
import static cz.prm.repositories.common.query.filter.FilterOperation.EMPTY;
import static cz.prm.repositories.common.query.filter.FilterOperation.ENDS_WITH;
import static cz.prm.repositories.common.query.filter.FilterOperation.EQUALS;
import static cz.prm.repositories.common.query.filter.FilterOperation.GREATER_THAN;
import static cz.prm.repositories.common.query.filter.FilterOperation.GREATER_THAN_OR_EQUAL_TO;
import static cz.prm.repositories.common.query.filter.FilterOperation.LESS_THAN;
import static cz.prm.repositories.common.query.filter.FilterOperation.LESS_THAN_OR_EQUAL_TO;
import static cz.prm.repositories.common.query.filter.FilterOperation.NOT_CONTAINS;
import static cz.prm.repositories.common.query.filter.FilterOperation.NOT_EMPTY;
import static cz.prm.repositories.common.query.filter.FilterOperation.NOT_EQUALS;
import static cz.prm.repositories.common.query.filter.FilterOperation.STARTS_WITH;
import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FilterOperationTest {

    private static final FilterCriteria fcRandom = new FilterCriteria(uuid());
    private static final FilterCriteria fcContains = new FilterCriteria("contains(value1)");
    private static final FilterCriteria fcNotContains = new FilterCriteria("notContains(value2)");
    private static final FilterCriteria fcStartsWith = new FilterCriteria("startsWith(value3)");
    private static final FilterCriteria fcEndsWith = new FilterCriteria("endsWith(value4)");
    private static final FilterCriteria fcEquals = new FilterCriteria("equals(value5)");
    private static final FilterCriteria fcNotEquals = new FilterCriteria("notEquals(value6)");
    private static final FilterCriteria fcEmpty = new FilterCriteria("empty(value7)");
    private static final FilterCriteria fcNotEmpty = new FilterCriteria("notEmpty(value8)");
    private static final FilterCriteria fcArrayIncludes = new FilterCriteria("arrIncludes(value9)");
    private static final FilterCriteria fcArrayIncludesAll = new FilterCriteria("arrIncludesAll(value9)");
    private static final FilterCriteria fcBetween = new FilterCriteria("between(value10)");
    private static final FilterCriteria fcBetweenInclusive = new FilterCriteria("betweenInclusive(value11)");
    private static final FilterCriteria fcGreaterThan = new FilterCriteria("greaterThan(value12)");
    private static final FilterCriteria fcGreaterThanOrEqualTo = new FilterCriteria("greaterThanOrEqualTo(value13)");
    private static final FilterCriteria fcLessThan = new FilterCriteria("lessThan(value14)");
    private static final FilterCriteria fcLessThanOrEqualTo = new FilterCriteria("lessThanOrEqualTo(value15)");

    @Test
    void getName() {
        assertThat(CONTAINS.getName()).isEqualTo("contains");
        assertThat(NOT_CONTAINS.getName()).isEqualTo("notContains");
        assertThat(STARTS_WITH.getName()).isEqualTo("startsWith");
        assertThat(ENDS_WITH.getName()).isEqualTo("endsWith");
        assertThat(FilterOperation.EQUALS.getName()).isEqualTo("equals");
        assertThat(FilterOperation.NOT_EQUALS.getName()).isEqualTo("notEquals");
        assertThat(FilterOperation.EMPTY.getName()).isEqualTo("empty");
        assertThat(NOT_EMPTY.getName()).isEqualTo("notEmpty");
        assertThat(ARRAY_INCLUDES.getName()).isEqualTo("arrIncludes");
        assertThat(ARRAY_INCLUDES_ALL.getName()).isEqualTo("arrIncludesAll");
        assertThat(BETWEEN.getName()).isEqualTo("between");
        assertThat(BETWEEN_INCLUSIVE.getName()).isEqualTo("betweenInclusive");
        assertThat(GREATER_THAN.getName()).isEqualTo("greaterThan");
        assertThat(GREATER_THAN_OR_EQUAL_TO.getName()).isEqualTo("greaterThanOrEqualTo");
        assertThat(LESS_THAN.getName()).isEqualTo("lessThan");
        assertThat(LESS_THAN_OR_EQUAL_TO.getName()).isEqualTo("lessThanOrEqualTo");
    }

    @Test
    void isOperationContains() {
        assertThat(CONTAINS.isOperation(fcContains)).isTrue();
        assertThat(CONTAINS.isOperation(fcNotContains)).isFalse();
        assertThat(CONTAINS.isOperation(fcStartsWith)).isFalse();
        assertThat(CONTAINS.isOperation(fcEndsWith)).isFalse();
        assertThat(CONTAINS.isOperation(fcEquals)).isFalse();
        assertThat(CONTAINS.isOperation(fcNotEquals)).isFalse();
        assertThat(CONTAINS.isOperation(fcEmpty)).isFalse();
        assertThat(CONTAINS.isOperation(fcNotEmpty)).isFalse();
        assertThat(CONTAINS.isOperation(fcArrayIncludes)).isFalse();
        assertThat(CONTAINS.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(CONTAINS.isOperation(fcBetween)).isFalse();
        assertThat(CONTAINS.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(CONTAINS.isOperation(fcGreaterThan)).isFalse();
        assertThat(CONTAINS.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(CONTAINS.isOperation(fcLessThan)).isFalse();
        assertThat(CONTAINS.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(CONTAINS.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationNotContains() {
        assertThat(NOT_CONTAINS.isOperation(fcNotContains)).isTrue();
        assertThat(NOT_CONTAINS.isOperation(fcContains)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcStartsWith)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcEndsWith)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcEquals)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcNotEquals)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcEmpty)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcNotEmpty)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcArrayIncludes)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcBetween)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcGreaterThan)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcLessThan)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(NOT_CONTAINS.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationStartsWith() {
        assertThat(STARTS_WITH.isOperation(fcStartsWith)).isTrue();
        assertThat(STARTS_WITH.isOperation(fcEndsWith)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcContains)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcNotContains)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcEquals)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcNotEquals)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcEmpty)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcNotEmpty)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcArrayIncludes)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcBetween)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcGreaterThan)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcLessThan)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(STARTS_WITH.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationEndsWith() {
        assertThat(ENDS_WITH.isOperation(fcEndsWith)).isTrue();
        assertThat(ENDS_WITH.isOperation(fcStartsWith)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcContains)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcNotContains)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcEquals)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcNotEquals)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcEmpty)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcNotEmpty)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcArrayIncludes)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcBetween)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcGreaterThan)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcLessThan)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(ENDS_WITH.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationEquals() {
        assertThat(EQUALS.isOperation(fcEquals)).isTrue();
        assertThat(EQUALS.isOperation(fcNotEquals)).isFalse();
        assertThat(EQUALS.isOperation(fcEndsWith)).isFalse();
        assertThat(EQUALS.isOperation(fcStartsWith)).isFalse();
        assertThat(EQUALS.isOperation(fcContains)).isFalse();
        assertThat(EQUALS.isOperation(fcNotContains)).isFalse();
        assertThat(EQUALS.isOperation(fcEmpty)).isFalse();
        assertThat(EQUALS.isOperation(fcNotEmpty)).isFalse();
        assertThat(EQUALS.isOperation(fcArrayIncludes)).isFalse();
        assertThat(EQUALS.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(EQUALS.isOperation(fcBetween)).isFalse();
        assertThat(EQUALS.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(EQUALS.isOperation(fcGreaterThan)).isFalse();
        assertThat(EQUALS.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(EQUALS.isOperation(fcLessThan)).isFalse();
        assertThat(EQUALS.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(EQUALS.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationNotEquals() {
        assertThat(NOT_EQUALS.isOperation(fcNotEquals)).isTrue();
        assertThat(NOT_EQUALS.isOperation(fcEquals)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcEndsWith)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcStartsWith)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcContains)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcNotContains)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcEmpty)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcNotEmpty)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcArrayIncludes)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcBetween)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcGreaterThan)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcLessThan)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(NOT_EQUALS.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationEmpty() {
        assertThat(EMPTY.isOperation(fcEmpty)).isTrue();
        assertThat(EMPTY.isOperation(fcNotEmpty)).isFalse();
        assertThat(EMPTY.isOperation(fcEndsWith)).isFalse();
        assertThat(EMPTY.isOperation(fcStartsWith)).isFalse();
        assertThat(EMPTY.isOperation(fcContains)).isFalse();
        assertThat(EMPTY.isOperation(fcNotContains)).isFalse();
        assertThat(EMPTY.isOperation(fcNotContains)).isFalse();
        assertThat(EMPTY.isOperation(fcEquals)).isFalse();
        assertThat(EMPTY.isOperation(fcNotEquals)).isFalse();
        assertThat(EMPTY.isOperation(fcArrayIncludes)).isFalse();
        assertThat(EMPTY.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(EMPTY.isOperation(fcBetween)).isFalse();
        assertThat(EMPTY.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(EMPTY.isOperation(fcGreaterThan)).isFalse();
        assertThat(EMPTY.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(EMPTY.isOperation(fcLessThan)).isFalse();
        assertThat(EMPTY.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(EMPTY.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationNotEmpty() {
        assertThat(NOT_EMPTY.isOperation(fcNotEmpty)).isTrue();
        assertThat(NOT_EMPTY.isOperation(fcEmpty)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcEndsWith)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcStartsWith)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcContains)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcNotContains)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcEquals)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcNotEquals)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcArrayIncludes)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcBetween)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcGreaterThan)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcLessThan)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(NOT_EMPTY.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationArrayIncludes() {
        assertThat(ARRAY_INCLUDES.isOperation(fcArrayIncludes)).isTrue();
        assertThat(ARRAY_INCLUDES.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcNotEmpty)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcEmpty)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcEndsWith)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcStartsWith)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcContains)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcNotContains)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcEquals)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcNotEquals)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcBetween)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcGreaterThan)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcLessThan)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(ARRAY_INCLUDES.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationArrayIncludesAll() {
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcArrayIncludesAll)).isTrue();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcArrayIncludes)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcNotEmpty)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcEmpty)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcEndsWith)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcStartsWith)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcContains)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcNotContains)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcEquals)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcNotEquals)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcBetween)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcGreaterThan)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcLessThan)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationBetween() {
        assertThat(BETWEEN.isOperation(fcBetween)).isTrue();
        assertThat(BETWEEN.isOperation(fcBetweenInclusive)).isFalse();
        assertThat(BETWEEN.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(BETWEEN.isOperation(fcArrayIncludes)).isFalse();
        assertThat(BETWEEN.isOperation(fcNotEmpty)).isFalse();
        assertThat(BETWEEN.isOperation(fcEmpty)).isFalse();
        assertThat(BETWEEN.isOperation(fcEndsWith)).isFalse();
        assertThat(BETWEEN.isOperation(fcStartsWith)).isFalse();
        assertThat(BETWEEN.isOperation(fcContains)).isFalse();
        assertThat(BETWEEN.isOperation(fcNotContains)).isFalse();
        assertThat(BETWEEN.isOperation(fcEquals)).isFalse();
        assertThat(BETWEEN.isOperation(fcNotEquals)).isFalse();
        assertThat(BETWEEN.isOperation(fcGreaterThan)).isFalse();
        assertThat(BETWEEN.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(BETWEEN.isOperation(fcLessThan)).isFalse();
        assertThat(BETWEEN.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(BETWEEN.isOperation(fcRandom)).isFalse();
    }

    @Test
    void isOperationBetweenInclusive() {
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcBetweenInclusive)).isTrue();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcBetween)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcArrayIncludesAll)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcArrayIncludes)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcNotEmpty)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcEmpty)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcEndsWith)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcStartsWith)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcContains)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcNotContains)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcEquals)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcNotEquals)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcGreaterThan)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcGreaterThanOrEqualTo)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcLessThan)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcLessThanOrEqualTo)).isFalse();
        assertThat(BETWEEN_INCLUSIVE.isOperation(fcRandom)).isFalse();
    }
}