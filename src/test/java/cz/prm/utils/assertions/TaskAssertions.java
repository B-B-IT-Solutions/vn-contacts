package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.query.TasksQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class TaskAssertions {

    public static void assertPage(Page<Task> page, PageDto<TaskDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertThat(page.getContent()).isNotEmpty().hasSameSizeAs(pageDto.getContent());
        page.getContent().forEach(contact -> {
            var dto = pageDto.getContent().stream().filter(c -> Objects.equals(contact.getContactId(), c.getContactId())).findFirst().get();
            assertTask(contact, dto);
        });
    }

    public static void assertPage(Page<Task> page1, PageImpl<Task> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertThat(page1.getContent()).isNotEmpty().hasSameSizeAs(page2.getContent());
        page1.getContent().forEach(contact1 -> {
            var contact2 = page2.getContent().stream().filter(c -> Objects.equals(contact1.getContactId(), c.getContactId())).findFirst().get();
            assertTask(contact1, contact2);
        });
    }

    public static void assertTasks(List<Task> tasks1, List<Task> tasks2) {
        assertThat(tasks1).isNotEmpty().hasSameSizeAs(tasks2);
        tasks1.forEach(c1 -> {
            var c2 = tasks2.stream().filter(u -> Objects.equals(c1.getContactId(), u.getContactId())).findFirst().get();
            assertTask(c1, c2);
        });
    }

    public static void assertTasksDto(List<Task> tasks, List<TaskDto> dtos) {
        assertThat(tasks).isNotEmpty().hasSameSizeAs(dtos);
        tasks.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertTask(u1, u2);
        });
    }

    public static void assertTask(Task task1, Task task2) {
        assertThat(task1.getTaskId()).isEqualTo(task2.getTaskId());
        assertThat(task1.getContactId()).isEqualTo(task2.getContactId());
        assertThat(task1.getDescription()).isEqualTo(task2.getDescription());
        assertThat(task1.getLastEditDate()).isEqualTo(task2.getLastEditDate());
        assertThat(task1.getCreationDate()).isEqualTo(task2.getCreationDate());
        assertThat(task1.getOwner()).isEqualTo(task2.getOwner());
    }

    public static void assertTask(Task task, TaskDto dto) {
        assertThat(task.getTaskId()).isEqualTo(dto.getTaskId());
        assertThat(task.getContactId()).isEqualTo(dto.getContactId());
        assertThat(task.getTitle()).isEqualTo(dto.getTitle());
        assertThat(task.getDescription()).isEqualTo(dto.getDescription());
        assertThat(task.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertThat(task.getCreationDate()).isEqualTo(dto.getCreationDate());
    }

    public static void assertTasksQuery(TasksQuery query, TasksQueryDto dto) {
        assertQuery(query, dto);
    }
}
