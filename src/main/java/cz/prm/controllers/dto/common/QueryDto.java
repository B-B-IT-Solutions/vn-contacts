package cz.prm.controllers.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryDto {

    protected String sort;

    protected PaginationDto pagination;
}
