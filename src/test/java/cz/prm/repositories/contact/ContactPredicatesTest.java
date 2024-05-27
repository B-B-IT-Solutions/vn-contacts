package cz.prm.repositories.contact;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contact.query.ContactsFilter;
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
    void contactsOwner() {
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
    void contactsNoFilterOperation() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user("username_1");
            var filter = new ContactsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.contacts(filter);
            var expectedString = "contact.owner.username = username_1";
            assertThat(predicate).hasToString(expectedString);

            filter.setFirstName("firstName_01");
            predicate = predicates.contacts(filter);
            expectedString = "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setLastName("lastName_01");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01) && containsIc(contact.lastName," + "lastName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setEmail("email_01");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01) && containsIc(contact.lastName,lastName_01) && "
                    + "containsIc(contact.email,email_01)";
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void contactsContainsFilterOperation() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user("username_1");
            var filter = new ContactsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.contacts(filter);
            var expectedString = "contact.owner.username = username_1";
            assertThat(predicate).hasToString(expectedString);

            filter.setFirstName("contains(firstName_01)");
            predicate = predicates.contacts(filter);
            expectedString = "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setMiddleName("contains(middleName_01)");
            predicate = predicates.contacts(filter);
            expectedString = "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01) && containsIc(contact.middleName,"
                + "middleName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setLastName("contains(lastName_01)");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01) && containsIc(contact.middleName,middleName_01)"
                    + " && containsIc(contact.lastName,lastName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setNickName("contains(nickName_01)");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01) && containsIc(contact.middleName,middleName_01)"
                    + " && containsIc(contact.lastName,lastName_01) && containsIc(contact.nickName,nickName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setEmail("contains(email_01)");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && containsIc(contact.firstName,firstName_01) && containsIc(contact.middleName,middleName_01)"
                    + " && containsIc(contact.lastName,lastName_01) && containsIc(contact.nickName,nickName_01) && containsIc(contact.email,"
                    + "email_01)";
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void contactsNotContainsFilterOperation() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user("username_1");
            var filter = new ContactsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.contacts(filter);
            var expectedString = "contact.owner.username = username_1";
            assertThat(predicate).hasToString(expectedString);

            filter.setFirstName("notContains(firstName_01)");
            predicate = predicates.contacts(filter);
            expectedString = "contact.owner.username = username_1 && !containsIc(contact.firstName,firstName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setMiddleName("notContains(middleName_01)");
            predicate = predicates.contacts(filter);
            expectedString = "contact.owner.username = username_1 && !containsIc(contact.firstName,firstName_01) && !containsIc(contact.middleName,"
                + "middleName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setLastName("notContains(lastName_01)");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && !containsIc(contact.firstName,firstName_01) && !containsIc(contact.middleName,middleName_01)"
                    + " && !containsIc(contact.lastName,lastName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setNickName("notContains(nickName_01)");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && !containsIc(contact.firstName,firstName_01) && !containsIc(contact.middleName,middleName_01)"
                    + " && !containsIc(contact.lastName,lastName_01) && !containsIc(contact.nickName,nickName_01)";
            assertThat(predicate).hasToString(expectedString);

            filter.setEmail("notContains(email_01)");
            predicate = predicates.contacts(filter);
            expectedString =
                "contact.owner.username = username_1 && !containsIc(contact.firstName,firstName_01) && !containsIc(contact.middleName,middleName_01)"
                    + " && !containsIc(contact.lastName,lastName_01) && !containsIc(contact.nickName,nickName_01) && !containsIc(contact.email,"
                    + "email_01)";
            assertThat(predicate).hasToString(expectedString);
        }
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
}