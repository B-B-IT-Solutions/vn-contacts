package cz.prm.repositories.user;

import cz.prm.domain.user.User;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, PrmQuerydslPredicateExecutor<User> {

}
