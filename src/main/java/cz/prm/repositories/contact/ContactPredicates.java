package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QContact.contact;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class ContactPredicates {

   public Predicate byContactId(Long userId) {
      return contact.userId.eq(userId);
   }

   public Predicate byEmail(String email) {
      return contact.email.eq(email);
   }

}