package cz.prm.repositories.common.query.filter;

import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class FilterCriteria {

   private static final String VALUE_SEPARATOR = ",";
   private static final String FILTER_CRITERIA_REGEX = "^(\\w+)\\((.+)\\)$";
   private static final Pattern FILTER_CRITERIA_PATTERN = Pattern.compile(FILTER_CRITERIA_REGEX);

   private String field;
   private String[] values;
}
