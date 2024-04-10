package cz.prm.repositories.user;

import static cz.prm.domain.querydsl.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class UserPredicates {

   public Predicate byUseId(Long userId) {
      return user.userId.eq(userId);
   }

}