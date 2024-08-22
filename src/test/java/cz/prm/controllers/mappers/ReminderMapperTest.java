package cz.prm.controllers.mappers;

import static cz.prm.utils.CommonUtils.DEFAULT_PAGE_SIZE;
import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.ReminderUtils.reminder;
import static cz.prm.utils.ReminderUtils.reminderDto;
import static cz.prm.utils.ReminderUtils.reminders;
import static cz.prm.utils.ReminderUtils.remindersQueryDto;
import static cz.prm.utils.assertions.ReminderAssertions.assertPage;
import static cz.prm.utils.assertions.ReminderAssertions.assertReminder;
import static cz.prm.utils.assertions.ReminderAssertions.assertRemindersQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.reminder.query.RemindersQueryDto;
import cz.prm.domain.reminder.query.RemindersQuery;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.Test;

class ReminderMapperTest {

    public static final String DEFAULT_REMINDERS_SORT = "desc(creationDate)";

    private ReminderMapper mapper = MapperUtils.getReminderMapper();

    @Test
    void toPageDto() {
        var page = page(reminders());
        var dtos = mapper.toPageDto(page);
        assertPage(page, dtos);
    }

    @Test
    void toReminderDto() {
        var reminder = reminder();
        var dto = mapper.toReminderDto(reminder);
        assertReminder(reminder, dto);
    }

    @Test
    void toReminder() {
        var dto = reminderDto();
        var reminder = mapper.toReminder(dto);
        assertReminder(reminder, dto);
    }

    @Test
    void toRemindersQuery() {
        var dto = remindersQueryDto();
        var query = mapper.toRemindersQuery(dto);
        assertRemindersQuery(query, dto);
    }

    @Test
    void toNullSafeRemindersQueryNullQuery() {
        var query = mapper.toNullSafeRemindersQuery(null);
        assertNullSafeReminderQuery(query);
    }

    @Test
    void toNullSafeRemindersQueryNotNullQuery() {
        var dto = remindersQueryDto();
        var query = mapper.toNullSafeRemindersQuery(dto);
        assertRemindersQuery(query, dto);
    }

    @Test
    void toNullSafeRemindersQueryNullPagination() {
        var dto = new RemindersQueryDto();
        dto.setPagination(null);
        var query = mapper.toNullSafeRemindersQuery(dto);
        assertNullSafeReminderQuery(query);
    }

    @Test
    void afterRemindersQuery() {
        var target = new RemindersQuery();
        target.setPagination(null);
        mapper.afterRemindersQuery(null, target);
        assertNullSafeReminderQuery(target);
    }

    private void assertNullSafeReminderQuery(RemindersQuery query) {
        assertThat(query.getPagination()).isNotNull();
        assertThat(query.getSort()).isEqualTo(DEFAULT_REMINDERS_SORT);
        var pagination = query.getPagination();
        assertThat(pagination.getPageNumber()).isZero();
        assertThat(pagination.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }
}