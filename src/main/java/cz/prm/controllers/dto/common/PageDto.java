package cz.prm.controllers.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDto<T> {

   @JsonProperty("totalPages")
   private int totalPages;

   @JsonProperty("totalElements")
   private long totalElements;

   @JsonProperty("pageNumber")
   private int pageNumber;

   @JsonProperty("pageSize")
   private int pageSize;

   @JsonProperty("numberOfElements")
   private int numberOfElements;

   @JsonProperty("content")
   private List<T> content;
}
