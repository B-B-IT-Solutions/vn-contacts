package cz.prm.repositories.common.query.filter;

import lombok.Getter;

@Getter
public enum FilterOperation {
    CONTAINS("contains"),
    NOT_CONTAINS("notContains"),
    STARTS_WITH("startsWith"),
    ENDS_WITH("endsWith"),
    EQUALS("equals"),
    NOT_EQUALS("notEquals"),
    EMPTY("empty"),
    NOT_EMPTY("notEmpty"),
    ARRAY_INCLUDES("arrIncludes"),
    ARRAY_INCLUDES_ALL("arrIncludesAll"),
    BETWEEN("between"),
    GREATER_THAN("greaterThan"),
    GREATER_THAN_OR_EQUAL_TO("greaterThanOrEqualTo"),
    LESS_THAN("lessThan"),
    LESS_THAN_OR_EQUAL_TO("lessThanOrEqualTo");

    private final String name;

    FilterOperation(String name) {
        this.name = name;
    }

    public boolean isOperation(FilterCriteria criteria) {
        return this.name.equals(criteria.getOperation());
    }
}
