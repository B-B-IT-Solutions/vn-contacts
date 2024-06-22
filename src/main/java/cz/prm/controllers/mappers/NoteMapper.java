package cz.prm.controllers.mappers;

import static cz.prm.domain.note.query.NotesQuery.DEFAULT_NOTES_SORT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.note.NoteDto;
import cz.prm.controllers.dto.note.query.NotesQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import cz.prm.domain.note.Note;
import cz.prm.domain.note.query.NotesQuery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    PageDto<NoteDto> toPageDto(Page<Note> notes);

    NoteDto toContactDto(Note note);

    @Mapping(target = "owner", ignore = true)
    Note toNote(NoteDto dto);

    NotesQuery toNotesQuery(NotesQueryDto dto);

    default NotesQuery toNullSafeNotesQuery(NotesQueryDto dto) {
        if (isNull(dto)) {
            return new NotesQuery();
        }
        return toNotesQuery(dto);
    }

    @AfterMapping
    default void afterNotesQuery(NotesQueryDto source, @MappingTarget NotesQuery target) {
        if (isNull(target.getPagination())) {
            target.setPagination(new Pagination());
        }
        if (isBlank(target.getSort())) {
            target.setSort(DEFAULT_NOTES_SORT);
        }
    }
}
