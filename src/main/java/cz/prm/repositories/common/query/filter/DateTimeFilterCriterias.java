package cz.prm.repositories.common.query.filter;

import java.time.Instant;
import java.time.temporal.Temporal;

public class DateTimeFilterCriterias extends AbstractFilterCriterias<DateTimeFilterCriteria> {

    private Class<? extends Temporal> dateClass;

    public DateTimeFilterCriterias(String filter, Class<? extends Temporal> dateClass) {
        super(filter);
    }

    protected void addFilterCriteria(String filter) {
        var fc = new DateTimeFilterCriteria(filter, Instant.class);
        criterias.add(fc);
    }
}
