package cz.prm.repositories.common.query.filter;

import java.time.temporal.Temporal;

public class DateTimeFilterCriterias extends AbstractFilterCriterias<DateTimeFilterCriteria> {

    public DateTimeFilterCriterias(String filter, Class<? extends Temporal> filterType) {
        super(filter, filterType);
    }

    protected void addFilterCriteria(String filter) {
        var fc = new DateTimeFilterCriteria(filter, this.filterType);
        criterias.add(fc);
    }
}
