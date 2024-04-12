package cz.prm.repositories.user;

import static cz.prm.domain.user.querydsl.QUser.user;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class UserPredicates {

   public Predicate byUseId(Long userId) {
      return user.userId.eq(userId);
   }

   public Predicate byEmail(String email) {
      return user.email.eq(email);
   }

}