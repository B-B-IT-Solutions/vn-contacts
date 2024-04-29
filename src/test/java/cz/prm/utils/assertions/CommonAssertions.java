package cz.prm.utils.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.common.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class CommonAssertions {

   public static void assertPageRequest(PageRequest pr, Pagination pagination, Sort sort) {
      assertThat(pr.getPageNumber()).isNotZero().isEqualTo(pagination.getPageNumber());
      assertThat(pr.getPageSize()).isNotZero().isEqualTo(pagination.getPageSize());
      assertThat(pr.getSort()).isEqualTo(sort);
   }
}
