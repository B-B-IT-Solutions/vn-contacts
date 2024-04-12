package cz.prm.services.user;

import static java.lang.String.format;

import cz.prm.repositories.user.UserPredicates;
import cz.prm.repositories.user.UserRepository;
import cz.prm.security.PrmUserDetails;
import java.util.function.Supplier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrmUserDetailsService implements UserDetailsService {

   private UserRepository repository;
   private UserPredicates predicates;

   public PrmUserDetailsService(UserRepository repository, UserPredicates predicates) {
      this.repository = repository;
      this.predicates = predicates;
   }

   @Override
   public PrmUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      var predicate = predicates.byEmail(username);
      var optional = repository.findOne(predicate);
      var user = optional.orElseThrow(usernameNotFoundSupplier(username));
      return new PrmUserDetails(user);
   }

   private Supplier<UsernameNotFoundException> usernameNotFoundSupplier(String email) {
      return () -> new UsernameNotFoundException(format("User for given email=[%s] not found!", email));
   }

}
