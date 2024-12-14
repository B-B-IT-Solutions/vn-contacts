package cz.prm.repositories.common.query.filter;

import java.time.Instant;

public class DateTimeFilterCriterias extends AbstractFilterCriterias<DateTimeFilterCriteria> {

    public DateTimeFilterCriterias(String filter) {
        super(filter);
    }

    protected void addFilterCriteria(String filter) {
        var fc = new DateTimeFilterCriteria(filter, Instant.class);
        criterias.add(fc);
    }
}
