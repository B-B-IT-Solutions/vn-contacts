package cz.prm.services;

import static cz.prm.utils.UserUtils.users;
import static cz.prm.utils.assertions.UserAssertions.assertUsers;
import static org.mockito.Mockito.when;

import cz.prm.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

   @Mock
   private UserRepository repository;

   private UserService userService;

   @BeforeEach
   void setUp() {
      userService = new UserService(repository);
   }

   @Test
   void getUsers() {
      var users = users();
      when(repository.findAll()).thenReturn(users);
      var result = userService.getUsers();
      assertUsers(result, users);
   }

}