package cz.prm.repositories.common.query.filter;

public class EnumFilterCriterias extends AbstractFilterCriterias<EnumFilterCriteria> {

    public EnumFilterCriterias(String filter, Class<?> filterType) {
        super(filter, filterType);
    }

    protected void addFilterCriteria(String filter) {
        var fc = new EnumFilterCriteria(filter, super.filterType);
        criterias.add(fc);
    }
}
