package cz.prm.services.user;

import static cz.prm.utils.UserUtils.user;
import static cz.prm.utils.UserUtils.users;
import static cz.prm.utils.assertions.UserAssertions.assertUser;
import static cz.prm.utils.assertions.UserAssertions.assertUsers;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.repositories.user.UserPredicates;
import cz.prm.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

   @Mock
   private UserRepository repository;
   @Mock
   private UserPredicates predicates;

   private UserService userService;

   @BeforeEach
   void setUp() {
      userService = new UserService(repository, predicates);
   }

   @Test
   void getUsers() {
      var users = users();
      when(repository.findAll()).thenReturn(users);
      var result = userService.getUsers();
      assertUsers(result, users);
   }

   @Test
   void getUser() {
      var user = user();
      var predicate = new BooleanBuilder();
      when(predicates.byUseId(user.getUserId())).thenReturn(predicate);
      when(repository.findOne(predicate)).thenReturn(of(user));
      var result = userService.getUser(user.getUserId());
      assertUser(result, user);
   }

   @Test
   void getUser_EntityNotFound() {
      var user = user();
      var predicate = new BooleanBuilder();
      when(predicates.byUseId(user.getUserId())).thenReturn(predicate);
      when(repository.findOne(predicate)).thenReturn(empty());
      assertThrows(EntityNotFoundException.class, () -> userService.getUser(user.getUserId()));
   }

}