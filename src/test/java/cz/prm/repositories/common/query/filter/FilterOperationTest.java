package cz.prm.repositories.common.query.filter;

import static cz.prm.repositories.common.query.filter.FilterOperation.ARRAY_INCLUDES;
import static cz.prm.repositories.common.query.filter.FilterOperation.ARRAY_INCLUDES_ALL;
import static cz.prm.repositories.common.query.filter.FilterOperation.CONTAINS;
import static cz.prm.repositories.common.query.filter.FilterOperation.EMPTY;
import static cz.prm.repositories.common.query.filter.FilterOperation.ENDS_WITH;
import static cz.prm.repositories.common.query.filter.FilterOperation.EQUALS;
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
    private static final FilterCriteria fcArrayIncludesAll = new FilterCriteria("arrIncludesAll(value10)");

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
        assertThat(ARRAY_INCLUDES_ALL.isOperation(fcRandom)).isFalse();
    }
}