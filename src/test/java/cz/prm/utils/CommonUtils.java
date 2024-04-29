package cz.prm.utils;

import static cz.prm.utils.TestUtils.randomInt;

import cz.prm.domain.common.Page;
import cz.prm.domain.common.Pagination;
import java.util.List;

public class CommonUtils {

   public static <T> Page<T> page(List<T> content) {
      var page = new Page<T>();
      page.setContent(content);
      page.setTotalPages(randomInt());
      page.setTotalElements(randomInt());
      page.setNumberOfElements(randomInt());
      page.setPageSize(randomInt());
      page.setPageNumber(randomInt());
      return page;
   }

   public static Pagination pagination() {
      var pagination = new Pagination();
      pagination.setPageSize(randomInt());
      pagination.setPageNumber(randomInt());
      return pagination;
   }
}
