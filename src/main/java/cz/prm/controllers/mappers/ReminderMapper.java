package cz.prm.controllers.mappers;

import static cz.prm.domain.reminder.query.RemindersQuery.DEFAULT_REMINDERS_SORT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.reminder.ReminderDto;
import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import cz.prm.domain.reminder.Reminder;
import cz.prm.domain.reminder.query.RemindersQuery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    PageDto<ReminderDto> toPageDto(Page<Reminder> reminders);

    @Mapping(target = "recurrence", source = "recurrence.value")
    ReminderDto toReminderDto(Reminder reminder);

    @Mapping(target = "recurrence.value", source = "recurrence")
    Reminder toReminder(ReminderDto dto);

    RemindersQuery toRemindersQuery(RemindersQueryDto dto);

    default RemindersQuery toNullSafeRemindersQuery(RemindersQueryDto dto) {
        if (isNull(dto)) {
            return new RemindersQuery();
        }
        return toRemindersQuery(dto);
    }

    @AfterMapping
    default void afterRemindersQuery(RemindersQueryDto source, @MappingTarget RemindersQuery target) {
        if (isNull(target.getPagination())) {
            target.setPagination(new Pagination());
        }
        if (isBlank(target.getSort())) {
            target.setSort(DEFAULT_REMINDERS_SORT);
        }
    }
}
