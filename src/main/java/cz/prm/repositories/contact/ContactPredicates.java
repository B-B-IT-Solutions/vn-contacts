package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QContact.contact;
import static cz.prm.security.SecurityContextUtils.getUsername;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class ContactPredicates {

   public Predicate contacts() {
      return dataAccessPredicate();
   }

   public Predicate byContactId(Long userId) {
      var predicate = dataAccessPredicate();
      return predicate.and(contact.contactId.eq(userId));
   }

   public BooleanExpression dataAccessPredicate() {
      var username = getUsername();
      return contact.owner.eq(username);
   }

}