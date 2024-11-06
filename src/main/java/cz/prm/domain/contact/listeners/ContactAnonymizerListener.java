package cz.prm.domain.contact.listeners;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.apache.commons.lang3.ObjectUtils;

public class ContactAnonymizerListener {

    private static final String ANOMYSATION_STRING = "*****";

    @PrePersist
    @PreUpdate
    public void anonymize(Contact contact) {
        var initLastName = contact.getLastName();
        anonymizeLastName(contact);
        anonymizeOccupation(contact);
        anonymizeEmails(contact, initLastName);
    }

    private void anonymizeLastName(Contact contact) {
        contact.setLastName(ANOMYSATION_STRING);
    }

    private void anonymizeOccupation(Contact contact) {
        var occupation = contact.getOccupation();
        if (nonNull(occupation)) {
            var company = occupation.getCompany();
            if (isNotBlank(company)) {
                var firstLetter = company.charAt(0);
                var anonymCompany = format("%s%s", firstLetter, ANOMYSATION_STRING);
                occupation.setCompany(anonymCompany);
            }
        }
    }

    private void anonymizeEmails(Contact contact, String lastName) {
        if (ObjectUtils.isNotEmpty(contact.getEmails())) {
            var emails = contact.getEmails();
            emails.forEach(e -> anonymizeConnection(e, lastName));
        }
    }

    private void anonymizeConnection(Connection connection, String lastName) {
        var initValue = connection.getValue();
        if (isNotBlank(initValue)) {
            var initValueLowerCase = initValue.toLowerCase();
            var lastNameLowerCase = lastName.toLowerCase();
            var anonymValue = "";
            if (containsIgnoreCase(initValueLowerCase, lastNameLowerCase)) {
                anonymValue = initValueLowerCase.replace(lastNameLowerCase, ANOMYSATION_STRING);
            } else {
                var domain = initValueLowerCase.substring(initValueLowerCase.indexOf("@"));
                anonymValue = ANOMYSATION_STRING + domain;
            }
            connection.setValue(anonymValue);
        }
    }
}
