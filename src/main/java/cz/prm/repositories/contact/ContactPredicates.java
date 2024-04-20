package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QContact.contact;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class ContactPredicates {

   public Predicate contacts(String username) {
      return contact.owner.eq(username);
   }

   public Predicate byContactId(Long userId) {
      return contact.contactId.eq(userId);
   }

}