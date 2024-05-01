package cz.prm.repositories.common.query.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterCriteria {

   private String field;
   private String value;
}
