package cz.prm.services;

import static cz.prm.domain.common.PageRequests.getPageRequest;
import static java.lang.String.format;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.task.Task;
import cz.prm.domain.task.query.TasksQuery;
import cz.prm.repositories.task.TaskPredicates;
import cz.prm.repositories.task.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskService {

    private TaskRepository repository;
    private TaskPredicates predicates;

    public TaskService(TaskRepository repository, TaskPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public Page<Task> getTasks(Long contactId, TasksQuery query) {
        var pageRequest = getPageRequest(query.getPagination(), query.resolveSort());
        var predicate = predicates.byContactId(contactId, query.getFilter());
        var page = repository.findAll(predicate, pageRequest);
        return new Page<>(page);
    }

    public Task getTask(Long taskId) {
        return getTaskById(taskId);
    }

    public void createTask(Task task) {
        repository.save(task);
    }

    public void updateTask(Long taskId, Task updatedTask) {
        var savedTask = getTaskById(taskId);
        updateTaskFields(savedTask, updatedTask);
        repository.save(savedTask);
    }

    public void deleteTask(Long taskId) {
        var savedTask = getTaskById(taskId);
        repository.deleteById(savedTask.getTaskId());
    }

    private void updateTaskFields(Task savedTask, Task updatedTask) {
        savedTask.setTitle(updatedTask.getTitle());
        savedTask.setDescription(updatedTask.getDescription());
        savedTask.setCompleted(updatedTask.isCompleted());
    }

    private Task getTaskById(Long taskId) {
        var predicate = predicates.byTaskId(taskId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(taskId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long userId) {
        return () -> new EntityNotFoundException(format("Task for given id=[%s] not found!", userId));
    }
}
