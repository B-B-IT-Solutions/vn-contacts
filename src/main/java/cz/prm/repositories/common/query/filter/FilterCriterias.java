package cz.prm.repositories.common.query.filter;

import lombok.Getter;

@Getter
public class FilterCriterias extends AbstractFilterCriterias<FilterCriteria> {

    public FilterCriterias(String filter) {
        super(filter);
    }

    protected void addFilterCriteria(String filter) {
        var fc = new FilterCriteria(filter);
        criterias.add(fc);
    }
}
