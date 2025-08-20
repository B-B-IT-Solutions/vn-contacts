package cz.prm.repositories.contacts.contact;

import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.NetworkingUtils.referralRequirement;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.contact.query.ContactsFilter;
import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class ContactPredicatesTest {

    private ContactPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new ContactPredicates();
    }

    @Test
    void byContactId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byContactId(11L);
            var expectedString = format("contact.owner.username = %s && contact.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void contactsOwnerNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new ContactsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.contacts(filter);
            var expectedString = format("contact.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void contactsOwnerWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new ContactsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.contacts(filter);
            var expectedString = format("contact.owner.username = %s", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("global_filter_01");
            predicate = predicates.contacts(filter);
            expectedString = format(
                "contact.owner.username = %s && (containsIc(contact.firstName,global_filter_01) || containsIc(contact.lastName,global_filter_01) || "
                    + "containsIc(contact.country,global_filter_01) || containsIc(contact.city,global_filter_01) || containsIc(contact.status,"
                    + "global_filter_01) || containsIc(contact.source,global_filter_01) || global_filter_01 in contact.labels || global_filter_01 "
                    + "in contact.industries)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setFirstName("firstName_01");
            predicate = predicates.contacts(filter);
            expectedString = format("contact.owner.username = %s && containsIc(contact.firstName,firstName_01)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setFirstName(null);
            filter.setLastName("lastName_01");
            predicate = predicates.contacts(filter);
            expectedString = format("contact.owner.username = %s && containsIc(contact.lastName,lastName_01)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setLastName(null);
            filter.setCountry("country_1");
            predicate = predicates.contacts(filter);
            expectedString = format("contact.owner.username = %s && containsIc(contact.country,country_1)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setCountry(null);
            filter.setCity("city_1");
            predicate = predicates.contacts(filter);
            expectedString = format("contact.owner.username = %s && containsIc(contact.city,city_1)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setCity(null);
            filter.setLabels("arrIncludes(label_1,label_2,label_3)");
            predicate = predicates.contacts(filter);
            expectedString = format(
                "contact.owner.username = %s && (label_1 in contact.labels || label_2 in contact.labels || label_3 in contact.labels)",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setLabels(null);
            filter.setIndustries("arrIncludes(industry_1,industry_2,industry_3)");
            predicate = predicates.contacts(filter);
            expectedString = format(
                "contact.owner.username = %s && (industry_1 in contact.industries || industry_2 in contact.industries || industry_3 in contact"
                    + ".industries)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void potentialReferrals() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var rr = referralRequirement();
            var query = predicates.potentialReferrals(rr);
            var expectedString = format(
                "contact.owner.username = %s && contact.contactId != %s && (any(contact.industries) in %s || any(contact.skills) in %s || any"
                    + "(contact.products) in %s || any(contact.targetMarkets) in %s)", user.getUsername(), rr.getContactId(), rr.getIndustries(),
                rr.getSkills(), rr.getProducts(), rr.getTargetMarkets());
            assertThat(query).hasToString(expectedString);
        }
    }
}