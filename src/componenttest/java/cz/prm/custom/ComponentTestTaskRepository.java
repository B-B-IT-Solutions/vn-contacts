package cz.prm.custom;

import cz.prm.domain.contacts.task.Task;
import cz.prm.repositories.contacts.task.TaskRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestTaskRepository extends TaskRepository {

    Task getByDescription(String description);
}
