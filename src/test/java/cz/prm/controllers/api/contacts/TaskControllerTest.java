package cz.prm.controllers.api.contacts;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.TaskAssertions.assertPage;
import static cz.prm.utils.assertions.TaskAssertions.assertTask;
import static cz.prm.utils.assertions.TaskAssertions.assertTasksQuery;
import static cz.prm.utils.data.contacts.TaskUtils.task;
import static cz.prm.utils.data.contacts.TaskUtils.taskDto;
import static cz.prm.utils.data.contacts.TaskUtils.tasks;
import static cz.prm.utils.data.contacts.TaskUtils.tasksQueryDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.controllers.mapper.contacts.TaskMapper;
import cz.prm.domain.contacts.task.Task;
import cz.prm.domain.contacts.task.query.TasksQuery;
import cz.prm.services.contacts.task.TaskService;
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
        when(taskService.getTasks(any(TasksQuery.class))).thenReturn(page);

        var result = controller.getTasks(queryDto);
        assertPage(page, result);
        verify(taskService).getTasks(cQueryCapt.capture());
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
        var addedDto = taskDto();
        when(taskService.createTask(any(Task.class))).thenAnswer(returnParamAnswer(0));

        var responseDto = controller.createTask(addedDto);
        verify(taskService).createTask(taskCapt.capture());
        var task = taskCapt.getValue();
        assertTask(task, addedDto);
        assertTask(task, responseDto);
    }

    @Test
    void updateTask() {
        var updatedDto = taskDto();
        var taskId = updatedDto.getTaskId();
        when(taskService.updateTask(eq(taskId), any(Task.class))).thenAnswer(returnParamAnswer(1));

        var responseDto = controller.updateTask(taskId, updatedDto);
        verify(taskService).updateTask(eq(taskId), taskCapt.capture());
        var task = taskCapt.getValue();
        assertTask(task, updatedDto);
        assertTask(task, responseDto);
    }

    @Test
    void deleteTask() {
        var taskId = randomLong();
        controller.deleteTask(taskId);
        verify(taskService).deleteTask(taskId);
    }
}