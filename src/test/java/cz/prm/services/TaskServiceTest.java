package cz.prm.services;

import static cz.prm.utils.TaskUtils.task;
import static cz.prm.utils.TaskUtils.tasks;
import static cz.prm.utils.TaskUtils.tasksQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.TaskAssertions.assertPage;
import static cz.prm.utils.assertions.TaskAssertions.assertTask;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.task.Task;
import cz.prm.repositories.task.TaskPredicates;
import cz.prm.repositories.task.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;
    @Mock
    private TaskPredicates predicates;
    @Captor
    private ArgumentCaptor<Task> taskCapt;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(repository, predicates);
    }

    @Test
    void getTasks() {
        var tasks = tasks();
        var page = new PageImpl(tasks);
        var query = tasksQuery();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();

        when(predicates.byContactId(contactId)).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = taskService.getTasks(contactId, query);
        assertPage(result, page);
    }

    @Test
    void getTask() {
        var task = task();
        var predicate = new BooleanBuilder();
        when(predicates.byTaskId(task.getTaskId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(task));
        var result = taskService.getTask(task.getTaskId());
        assertTask(result, task);
    }

    @Test
    void getTask_EntityNotFound() {
        var task = task();
        var predicate = new BooleanBuilder();
        when(predicates.byTaskId(task.getTaskId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.getTask(task.getTaskId()));
    }

    @Test
    void createTask() {
        var task = task();
        taskService.createTask(task);
        verify(repository).save(task);
    }

    @Test
    void updateTask() {
        var taskIdDb = task();
        var updatedTask = task();
        var predicate = new BooleanBuilder();
        when(predicates.byTaskId(taskIdDb.getTaskId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(taskIdDb));

        taskService.updateTask(taskIdDb.getTaskId(), updatedTask);
        verify(repository).save(taskCapt.capture());
        var savedTask = taskCapt.getValue();
        assertFieldsUpdated(taskIdDb, updatedTask, savedTask);
    }

    @Test
    void updateTask_EntityNotFound() {
        var taskIdDb = task();
        var updatedTask = task();
        var predicate = new BooleanBuilder();
        when(predicates.byTaskId(taskIdDb.getTaskId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(taskIdDb.getTaskId(), updatedTask));
    }

    @Test
    void deleteTask() {
        var taskIdDb = task();
        var taskId = taskIdDb.getTaskId();
        var predicate = new BooleanBuilder();
        when(predicates.byTaskId(taskId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(taskIdDb));

        taskService.deleteTask(taskId);
        verify(repository).deleteById(taskId);
    }

    @Test
    void deleteTask_EntityNotFound() {
        var taskIdDb = task();
        var taskId = taskIdDb.getTaskId();
        var predicate = new BooleanBuilder();
        when(predicates.byTaskId(taskId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(taskId));
    }

    private void assertFieldsUpdated(Task taskIdDb, Task updatedTask, Task savedTask) {
        assertThat(taskIdDb.getTaskId()).isEqualTo(savedTask.getTaskId());
        assertThat(taskIdDb.getContactId()).isEqualTo(savedTask.getContactId());
        assertThat(taskIdDb.getOwner()).isEqualTo(savedTask.getOwner());
        assertThat(taskIdDb.getCreationDate()).isEqualTo(savedTask.getCreationDate());
        assertThat(savedTask.getTitle()).isEqualTo(updatedTask.getTitle());
        assertThat(savedTask.getDescription()).isEqualTo(updatedTask.getDescription());
        assertThat(savedTask.isCompleted()).isEqualTo(updatedTask.isCompleted());
    }
}