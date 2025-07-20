package cz.prm.controllers;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.controllers.mappers.TaskMapper;
import cz.prm.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("tasks")
@RestController
public class TaskController {

    private TaskService taskService;
    private TaskMapper mapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @GetMapping
    public PageDto<TaskDto> getTasks(TasksQueryDto queryDto) {
        var query = mapper.toNullSafeTasksQuery(queryDto);
        var tasks = taskService.getTasks(query);
        return mapper.toPageDto(tasks);
    }

    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) {
        var task = taskService.getTask(taskId);
        return mapper.toTaskDto(task);
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto dto) {
        var task = mapper.toTask(dto);
        var response = taskService.createTask(task);
        return mapper.toTaskDto(response);
    }

    @PutMapping("/{taskId}")
    public TaskDto updateTask(@PathVariable("taskId") Long taskId, @RequestBody TaskDto dto) {
        var task = mapper.toTask(dto);
        var response = taskService.updateTask(taskId, task);
        return mapper.toTaskDto(response);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }
}
