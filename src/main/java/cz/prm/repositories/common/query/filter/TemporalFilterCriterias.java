package cz.prm.repositories.common.query.filter;

import java.time.temporal.Temporal;

public class TemporalFilterCriterias extends AbstractFilterCriterias<TemporalFilterCriteria> {

    public TemporalFilterCriterias(String filter, Class<? extends Temporal> filterType) {
        super(filter, filterType);
    }

    protected void addFilterCriteria(String filter) {
        var fc = new TemporalFilterCriteria(filter, this.filterType);
        criterias.add(fc);
    }
}
