package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QContact.contact;
import static cz.prm.repositories.common.query.PredicateCriteriaUtils.applyCriteria;
import static cz.prm.security.SecurityContextUtils.getUser;

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
        var user = getUser();
        return contact.owner.username.eq(user.getUsername());
    }

    private BooleanBuilder filterPredicates(ContactsFilter filter) {
        var predicate = new BooleanBuilder();
        if (filter.isGlobalFilter()) {
            predicate.or(contact.firstName.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.middleName.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.lastName.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.nickName.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.labels.contains(filter.getGlobalFilter()));
            predicate.or(contact.industries.contains(filter.getGlobalFilter()));
        }
        if (filter.isFirstName()) {
            applyCriteria(predicate, contact.firstName, filter.getFirstName());
        }
        if (filter.isMiddleName()) {
            applyCriteria(predicate, contact.middleName, filter.getMiddleName());
        }
        if (filter.isLastName()) {
            applyCriteria(predicate, contact.lastName, filter.getLastName());
        }
        if (filter.isNickName()) {
            applyCriteria(predicate, contact.nickName, filter.getNickName());
        }
        if (filter.isLabels()) {
            applyCriteria(predicate, contact.labels, filter.getLabels());
        }
        if (filter.isIndustries()) {
            applyCriteria(predicate, contact.industries, filter.getIndustries());
        }
        return predicate;
    }
}