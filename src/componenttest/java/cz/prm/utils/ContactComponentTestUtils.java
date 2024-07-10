package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ComponentTestUtils.uuids;
import static java.lang.String.format;
import static java.time.Instant.now;
import static org.assertj.core.util.Lists.newArrayList;

import com.google.common.collect.Lists;
import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.contact.ConnectionDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import java.util.List;

public class ContactComponentTestUtils {

    public static List<Contact> contacts() {
        return newArrayList(contact(), contact(), contact());
    }

    public static Contact contact() {
        var contact = new Contact();
        contact.setFirstName(format("First%s", uuid()));
        contact.setMiddleName(format("Middle%s", uuid()));
        contact.setLastName(format("Last%s", uuid()));
        contact.setNickName(format("Nick%s", uuid()));
        contact.setTelephones(connections());
        contact.setEmails(connections());
        contact.setUrls(connections());
        contact.setJobTitle(format("jobTitle%s", uuid()));
        contact.setCompany(format("company%s", uuid()));
        contact.setIndustries(format("industries%s", uuid()));
        contact.setLabels(uuids());
        contact.setDateOfBirth(now());
        return contact;
    }

    public static ContactDto contactDto() {
        var dto = new ContactDto();
        dto.setFirstName(uuid());
        dto.setMiddleName(uuid());
        dto.setLastName(uuid());
        dto.setNickName(uuid());
        dto.setTelephones(connectionsDto());
        dto.setEmails(connectionsDto());
        dto.setUrls(connectionsDto());
        dto.setJobTitle(uuid());
        dto.setCompany(uuid());
        dto.setIndustries(uuid());
        dto.setLabels(uuids());
        dto.setDateOfBirth(now());
        return dto;
    }

    public static List<Connection> connections() {
        return Lists.newArrayList(connection(), connection(), connection());
    }

    public static List<ConnectionDto> connectionsDto() {
        return Lists.newArrayList(connectionDto(), connectionDto(), connectionDto());
    }

    public static Connection connection() {
        var connection = new Connection();
        connection.setValue(format("connection%s", uuid()));
        connection.setType(uuid());
        return connection;
    }

    public static ConnectionDto connectionDto() {
        var dto = new ConnectionDto();
        dto.setValue(uuid());
        dto.setType(uuid());
        return dto;
    }

    public static ContactsQueryDto contactsQueryDto() {
        var query = new ContactsQueryDto();
        query.setFilter(new ContactsFilterDto());
        query.setPagination(new PaginationDto());
        return query;
    }
}
