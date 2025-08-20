package cz.prm.repositories.contacts.contact;

import static cz.prm.domain.contacts.contact.querydsl.QAbout.about;
import static cz.prm.security.SecurityContextUtils.getUser;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

@Component
public class AboutPredicates {

    public Predicate byContactId(Long contactId) {
        var predicate = dataAccessPredicate();
        return predicate.and(about.contactId.eq(contactId));
    }

    private BooleanExpression dataAccessPredicate() {
        var user = getUser();
        return about.owner.username.eq(user.getUsername());
    }
}