package cz.prm.domain.contact.listeners;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.Occupation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactAnonymizerListenerTest {

    private static final String ANOMYSATION_STRING = "*****";

    private ContactAnonymizerListener listener;
    private Contact contact;

    @BeforeEach
    void setUp() {
        listener = new ContactAnonymizerListener();
        contact = new Contact();
    }

    @Test
    void anonymizeLastName() {
        var initLastName = uuid();
        contact.setLastName(initLastName);
        assertThat(contact.getLastName()).isEqualTo(initLastName);

        listener.anonymize(contact);
        assertThat(contact.getLastName()).isNotEqualTo(initLastName).isEqualTo(ANOMYSATION_STRING);
    }

    @Test
    void anonymizeOccupation() {
        var initCompany = uuid();
        var occupation = new Occupation();
        occupation.setCompany(initCompany);
        contact.setOccupation(occupation);
        assertThat(occupation.getCompany()).isEqualTo(initCompany);

        listener.anonymize(contact);
        var firstLetter = initCompany.charAt(0);
        var expectedCompany = format("%s%s", firstLetter, ANOMYSATION_STRING);
        assertThat(occupation.getCompany()).isNotEqualTo(initCompany).isEqualTo(expectedCompany);
    }

    @Test
    void anonymizeEmail_EmailContainsLastName() {
        var lastName = "Bobusky";
        var initEmail = "BoBuskySERGEJ@gmail.com";
        var connection = new Connection();
        connection.setValue(initEmail);
        contact.setLastName(lastName);
        contact.setEmails(newArrayList(connection));
        assertThat(connection.getValue()).isEqualTo(initEmail);

        listener.anonymize(contact);

        var initEmailLowerCase = initEmail.toLowerCase();
        var lastNameLowerCase = lastName.toLowerCase();
        var expectedEmail = initEmailLowerCase.replace(lastNameLowerCase, ANOMYSATION_STRING);
        assertThat(connection.getValue()).isNotEqualTo(initEmail).isEqualTo(expectedEmail);
    }

    @Test
    void anonymizeEmail_EmailDoesnotContainLastName() {
        var lastName = uuid();
        var initEmail = "bobuskysergej@gmail.com";
        var connection = new Connection();
        connection.setValue(initEmail);
        contact.setLastName(lastName);
        contact.setEmails(newArrayList(connection));
        assertThat(connection.getValue()).isEqualTo(initEmail);

        listener.anonymize(contact);

        var initEmailLowerCase = initEmail.toLowerCase();
        var lastNameLowerCase = lastName.toLowerCase();
        var expectedEmail = initEmailLowerCase.replace(lastNameLowerCase, ANOMYSATION_STRING);
        assertThat(connection.getValue()).isNotEqualTo(initEmail).isEqualTo(expectedEmail);
    }
}