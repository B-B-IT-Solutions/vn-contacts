package cz.prm.domain.common;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page<T> {

   private int totalPages;

   private long totalElements;

   private int pageNumber;

   private int pageSize;

   private int numberOfElements;

   private List<T> content;

   public Page(org.springframework.data.domain.Page<T> page) {
      this.totalPages = page.getTotalPages();
      this.totalElements = page.getTotalElements();
      this.pageNumber = page.getNumber();
      this.pageSize = page.getSize();
      this.numberOfElements = page.getNumberOfElements();
      this.content = page.getContent();
   }
}
