package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QContact.contact;
import static cz.prm.repositories.common.query.PredicateCriteriaUtils.applyCriteria;
import static cz.prm.security.SecurityContextUtils.getUsername;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import cz.prm.domain.contact.query.ContactsFilter;
import org.springframework.stereotype.Component;

@Component
public class ContactPredicates {

   public Predicate contacts(ContactsFilter filter) {
      var predicate = dataAccessPredicate();
      return predicate.and(filterPredicates(filter));
   }

   public Predicate byContactId(Long userId) {
      var predicate = dataAccessPredicate();
      return predicate.and(contact.contactId.eq(userId));
   }

   private BooleanExpression dataAccessPredicate() {
      var username = getUsername();
      return contact.owner.eq(username);
   }

   private BooleanBuilder filterPredicates(ContactsFilter filter) {
      var predicate = new BooleanBuilder();
      if (filter.isFirstName()) {
         applyCriteria(predicate, contact.firstName, filter.getFirstName());
      }
      if (filter.isLastName()) {
         applyCriteria(predicate, contact.lastName, filter.getLastName());
      }
      if (filter.isEmail()) {
         applyCriteria(predicate, contact.email, filter.getEmail());
      }
      return predicate;
   }
}