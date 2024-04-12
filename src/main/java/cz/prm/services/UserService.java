package cz.prm.services;

import static java.lang.String.format;

import cz.prm.domain.User;
import cz.prm.repositories.user.UserPredicates;
import cz.prm.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   private UserRepository repository;
   private UserPredicates predicates;

   public UserService(UserRepository repository, UserPredicates predicates) {
      this.repository = repository;
      this.predicates = predicates;
   }

   public List<User> getUsers() {
      return repository.findAll();
   }

   public User getUser(Long userId) {
      var predicate = predicates.byUseId(userId);
      var optional = repository.findOne(predicate);
      return optional.orElseThrow(exceptionSupplier(userId));
   }

   private Supplier<EntityNotFoundException> exceptionSupplier(Long userId) {
      return () -> new EntityNotFoundException(format("User for given id=[%s] not found!", userId));
   }

}
