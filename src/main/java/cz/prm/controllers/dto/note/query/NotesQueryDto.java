package cz.prm.controllers.dto.note.query;

import cz.prm.controllers.dto.common.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class NotesQueryDto extends QueryDto {

    private NotesFilterDto filter;
}
