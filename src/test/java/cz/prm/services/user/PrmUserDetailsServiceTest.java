package cz.prm.services.user;

import static cz.prm.utils.UserUtils.user;
import static cz.prm.utils.assertions.UserAssertions.assertUserDetails;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.repositories.user.UserPredicates;
import cz.prm.repositories.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class PrmUserDetailsServiceTest {

   @Mock
   private UserRepository repository;
   @Mock
   private UserPredicates predicates;

   private PrmUserDetailsService userService;

   @BeforeEach
   void setUp() {
      userService = new PrmUserDetailsService(repository, predicates);
   }

   @Test
   void loadUserByUsername() {
      var user = user();
      var predicate = new BooleanBuilder();
      when(predicates.byEmail(user.getEmail())).thenReturn(predicate);
      when(repository.findOne(predicate)).thenReturn(of(user));
      var result = userService.loadUserByUsername(user.getEmail());
      assertUserDetails(user, result);
   }

   @Test
   void loadUserByUsername_UsernameNotFound() {
      var user = user();
      var predicate = new BooleanBuilder();
      when(predicates.byEmail(user.getEmail())).thenReturn(predicate);
      when(repository.findOne(predicate)).thenReturn(empty());
      assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(user.getEmail()));
   }
}