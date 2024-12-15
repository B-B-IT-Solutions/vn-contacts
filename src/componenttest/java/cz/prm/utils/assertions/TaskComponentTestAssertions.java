package cz.prm.utils.assertions;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.domain.task.Task;
import java.util.List;
import java.util.Objects;

public class TaskComponentTestAssertions {

    public static void assertTasks(List<Task> tasks, PageDto<TaskDto> pageDto) {
        assertTasks(tasks, pageDto.getContent());
    }

    public static void assertTasks(List<Task> tasks, List<TaskDto> dtos) {
        assertThat(tasks).isNotEmpty().hasSameSizeAs(dtos);
        tasks.forEach(n1 -> {
            var n2 = dtos.stream().filter(n -> Objects.equals(n1.getTaskId(), n.getTaskId())).findFirst().get();
            assertTask(n1, n2);
        });
    }

    public static void assertTask(Task task, TaskDto taskDto) {
        assertThat(task.getTaskId()).isEqualTo(taskDto.getTaskId());
        assertThat(task.getContactId()).isEqualTo(taskDto.getContactId());
        assertThat(task.getTitle()).isEqualTo(taskDto.getTitle());
        assertThat(task.getDescription()).isEqualTo(taskDto.getDescription());
        assertThat(task.isCompleted()).isEqualTo(taskDto.isCompleted());
        assertThat(task.getDueDate()).isCloseTo(taskDto.getDueDate(), within(1, SECONDS));
        assertThat(task.getLastEditDate()).isNotNull();
        assertThat(task.getCreationDate()).isNotNull();
    }
}
