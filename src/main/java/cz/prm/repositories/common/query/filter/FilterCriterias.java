package cz.prm.repositories.common.query.filter;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class FilterCriterias {

   private static final String FILTER_CRITERIA_SEPARATOR = "\\+";
   private List<FilterCriteria> criterias = new ArrayList<>();

   public FilterCriterias(String filter) {
      parse(filter);
   }

   private void parse(String filter) {
      if (isNotBlank(filter)) {
         var fcs = filter.split(FILTER_CRITERIA_SEPARATOR);
         Stream.of(fcs).forEach(this::addFilterCriteria);
      }
   }

   private void addFilterCriteria(String filter) {
      var fc = new FilterCriteria(filter);
      criterias.add(fc);
   }
}
