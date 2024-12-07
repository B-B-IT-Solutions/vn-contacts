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
    NOT_EMPTY("notEmpty");

    private final String name;

    FilterOperation(String name) {
        this.name = name;
    }

    public boolean isOperation(FilterCriteria criteria) {
        return this.name.equals(criteria.getOperation());
    }
}
