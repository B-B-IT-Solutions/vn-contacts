package cz.prm.repositories.task;

import cz.prm.domain.contacts.task.Task;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, PrmQuerydslPredicateExecutor<Task> {

}
