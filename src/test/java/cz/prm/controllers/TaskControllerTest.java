package cz.prm.controllers;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.TaskUtils.task;
import static cz.prm.utils.TaskUtils.taskDto;
import static cz.prm.utils.TaskUtils.tasks;
import static cz.prm.utils.TaskUtils.tasksQueryDto;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.TaskAssertions.assertPage;
import static cz.prm.utils.assertions.TaskAssertions.assertTask;
import static cz.prm.utils.assertions.TaskAssertions.assertTasksQuery;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mappers.TaskMapper;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.query.TasksQuery;
import cz.prm.services.TaskService;
import cz.prm.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;
    @Captor
    private ArgumentCaptor<Task> taskCapt;
    @Captor
    private ArgumentCaptor<TasksQuery> cQueryCapt;

    private TaskMapper mapper = MapperUtils.getTaskMapper();
    private TaskController controller;

    @BeforeEach
    void setUp() {
        controller = new TaskController(taskService, mapper);
    }

    @Test
    void getTasks() {
        var page = page(tasks());
        var queryDto = tasksQueryDto();
        var contactId = randomLong();
        when(taskService.getTasks(eq(contactId), any(TasksQuery.class))).thenReturn(page);

        var result = controller.getTasks(contactId, queryDto);
        assertPage(page, result);
        verify(taskService).getTasks(eq(contactId), cQueryCapt.capture());
        var query = cQueryCapt.getValue();
        assertTasksQuery(query, queryDto);
    }

    @Test
    void getTask() {
        var task = task();
        var taskId = task.getTaskId();
        when(taskService.getTask(taskId)).thenReturn(task);
        var result = controller.getTask(taskId);
        assertTask(task, result);
    }

    @Test
    void createTask() {
        var dto = taskDto();
        controller.createTask(dto);
        verify(taskService).createTask(taskCapt.capture());
        var task = taskCapt.getValue();
        assertTask(task, dto);
    }

    @Test
    void updateTask() {
        var dto = taskDto();
        controller.updateTask(dto.getTaskId(), dto);
        verify(taskService).updateTask(eq(dto.getTaskId()), taskCapt.capture());
        var task = taskCapt.getValue();
        assertTask(task, dto);
    }

    @Test
    void deleteTask() {
        var taskId = randomLong();
        controller.deleteTask(taskId);
        verify(taskService).deleteTask(taskId);
    }
}