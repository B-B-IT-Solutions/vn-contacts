package cz.prm.repositories.common.query.filter;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class FilterCriterias {

   private static final String FILTER_CRITERIA_SEPARATOR = "\\+";
   private List<FilterCriteria> criterias = new ArrayList<>();
}
