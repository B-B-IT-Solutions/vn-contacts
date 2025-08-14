package cz.prm.repositories.contact;

import static cz.prm.domain.contact.querydsl.QContact.contact;
import static cz.prm.repositories.common.query.PredicateCriteriaUtils.applyCriteria;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import cz.prm.domain.contact.query.ContactsFilter;
import java.util.List;
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

    public Predicate matchingContacts(List<String> industries, List<String> skills, List<String> products, List<String> targetMarkets) {
        var predicate = new BooleanBuilder();
        predicate.or(contact.industries.any().in(industries));
        predicate.or(contact.skills.any().in(skills));
        predicate.or(contact.products.any().in(products));
        predicate.or(contact.targetMarkets.any().in(targetMarkets));
        return dataAccessPredicate().and(predicate);
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return contact.owner.username.eq(user.getUsername());
    }

    private BooleanBuilder filterPredicates(ContactsFilter filter) {
        var predicate = new BooleanBuilder();
        if (filter.isGlobalFilter()) {
            predicate.or(contact.firstName.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.lastName.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.country.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.city.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.status.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.source.containsIgnoreCase(filter.getGlobalFilter()));
            predicate.or(contact.labels.contains(filter.getGlobalFilter()));
            predicate.or(contact.industries.contains(filter.getGlobalFilter()));
        }
        if (filter.isFirstName()) {
            applyCriteria(predicate, contact.firstName, filter.getFirstName());
        }
        if (filter.isLastName()) {
            applyCriteria(predicate, contact.lastName, filter.getLastName());
        }
        if (filter.isCountry()) {
            applyCriteria(predicate, contact.country, filter.getCountry());
        }
        if (filter.isCity()) {
            applyCriteria(predicate, contact.city, filter.getCity());
        }
        if (filter.isStatus()) {
            applyCriteria(predicate, contact.status, filter.getStatus());
        }
        if (filter.isSource()) {
            applyCriteria(predicate, contact.source, filter.getSource());
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