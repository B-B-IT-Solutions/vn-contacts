package cz.prm.custom;

import cz.prm.domain.task.Task;
import cz.prm.repositories.task.TaskRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestTaskRepository extends TaskRepository {

    Task getByDescription(String description);
}
