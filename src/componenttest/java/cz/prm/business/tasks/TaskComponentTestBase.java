package cz.prm.business.tasks;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.task.TaskDto;
import cz.prm.controllers.dto.task.query.TasksFilterDto;
import cz.prm.controllers.dto.task.query.TasksQueryDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class TaskComponentTestBase extends BusinessComponentTestBase {

    protected static String TASKS_BASE_URL = "tasks";
    protected static String CONTACT_TASKS_URL = TASKS_BASE_URL + "/contact/%s";
    protected static String TASK_URL = TASKS_BASE_URL + "/task";
    protected static String TASK_BY_ID_URL = TASK_URL + "/%s";

    protected void user1CreateTask(TaskDto dto) {
        createTask(dto, USER_1);
    }

    protected void user2CreateTask(TaskDto dto) {
        createTask(dto, USER_2);
    }

    protected void user3CreateTask(TaskDto dto) {
        createTask(dto, USER_3);
    }

    protected void user1UpdateTask(Long taskId, TaskDto dto) {
        updateTask(taskId, dto, USER_1);
    }

    protected void user2UpdateTask(Long taskId, TaskDto dto) {
        updateTask(taskId, dto, USER_2);
    }

    protected void user3UpdateTask(Long taskId, TaskDto dto) {
        updateTask(taskId, dto, USER_3);
    }

    protected void user1DeleteTask(Long taskId) {
        deleteTask(taskId, USER_1);
    }

    protected void user2DeleteTask(Long taskId) {
        deleteTask(taskId, USER_2);
    }

    protected void user3DeleteTask(Long taskId) {
        deleteTask(taskId, USER_3);
    }

    protected PageDto<TaskDto> user1GetTasks(Long contactId, TasksQueryDto queryDto) {
        return getTasksPage(contactId, queryDto, USER_1);
    }

    protected PageDto<TaskDto> user2GetTasks(Long contactId, TasksQueryDto queryDto) {
        return getTasksPage(contactId, queryDto, USER_2);
    }

    protected PageDto<TaskDto> user3GetTasks(Long contactId, TasksQueryDto queryDto) {
        return getTasksPage(contactId, queryDto, USER_3);
    }

    protected TaskDto user1GetTask(Long taskId) {
        return getTask(taskId, USER_1);
    }

    protected TaskDto user2GetTask(Long taskId) {
        return getTask(taskId, USER_2);
    }

    protected TaskDto user3GetTask(Long taskId) {
        return getTask(taskId, USER_3);
    }

    protected void createTask(TaskDto dto, ComponentTestUser user) {
        post(TASK_URL, user, dto);
    }

    protected void updateTask(Long taskId, TaskDto dto, ComponentTestUser user) {
        var url = format(TASK_BY_ID_URL, taskId);
        put(url, user, dto);
    }

    protected void deleteTask(Long taskId, ComponentTestUser user) {
        var url = format(TASK_BY_ID_URL, taskId);
        delete(url, user);
    }

    protected PageDto<TaskDto> getTasksPage(Long contactId, TasksQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(CONTACT_TASKS_URL, contactId);
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<TaskDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected TaskDto getTask(Long taskId, ComponentTestUser user) {
        var url = format(TASK_BY_ID_URL, taskId);
        var typeRef = new TypeRef<TaskDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void user1UpdateTaskExpectNotFound(Long taskId, TaskDto dto) {
        updateTaskExpectNotFound(taskId, dto, USER_1);
    }

    protected void user2UpdateTaskExpectNotFound(Long taskId, TaskDto dto) {
        updateTaskExpectNotFound(taskId, dto, USER_2);
    }

    protected void user3UpdateTaskExpectNotFound(Long taskId, TaskDto dto) {
        updateTaskExpectNotFound(taskId, dto, USER_3);
    }

    protected void user1DeleteTaskExpectNotFound(Long taskId) {
        deleteTaskExpectNotFound(taskId, USER_1);
    }

    protected void user2DeleteTaskExpectNotFound(Long taskId) {
        deleteTaskExpectNotFound(taskId, USER_2);
    }

    protected void user3DeleteTaskExpectNotFound(Long taskId) {
        deleteTaskExpectNotFound(taskId, USER_3);
    }

    protected void user1GetTaskExpectNotFound(Long taskId) {
        getTaskExpectNotFound(taskId, USER_1);
    }

    protected void user2GetTaskExpectNotFound(Long taskId) {
        getTaskExpectNotFound(taskId, USER_2);
    }

    protected void user3GetTaskExpectNotFound(Long taskId) {
        getTaskExpectNotFound(taskId, USER_3);
    }

    protected void updateTaskExpectNotFound(Long taskId, TaskDto dto, ComponentTestUser user) {
        var url = format(TASK_BY_ID_URL, taskId);
        putExpectNotFound(url, user, dto);
    }

    protected void deleteTaskExpectNotFound(Long taskId, ComponentTestUser user) {
        var url = format(TASK_BY_ID_URL, taskId);
        deleteExpectNotFound(url, user);
    }

    protected void getTaskExpectNotFound(Long taskId, ComponentTestUser user) {
        var url = format(TASK_BY_ID_URL, taskId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, TasksQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var filters = toUrlFilterParams(queryDto.getFilter());
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(filters) || isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(filters);
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }

    protected String toUrlFilterParams(TasksFilterDto filterDto) {
        var sb = new StringBuilder();
        if (nonNull(filterDto)) {
            if (isNotEmpty(filterDto.getGlobalFilter())) {
                sb.append("filter.globalFilter=");
                sb.append(filterDto.getGlobalFilter());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getTitle())) {
                sb.append("filter.title=");
                sb.append(filterDto.getTitle());
                sb.append("&");
            }
            if (nonNull(filterDto.getCompleted())) {
                sb.append("filter.completed=");
                sb.append(filterDto.getCompleted());
                sb.append("&");
            }
        }
        return sb.toString();
    }
}
