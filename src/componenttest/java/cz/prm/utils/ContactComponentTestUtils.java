package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ComponentTestUtils.uuids;
import static java.lang.String.format;
import static java.time.Instant.now;
import static org.assertj.core.util.Lists.newArrayList;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
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
        contact.setEmail(format("email%s", uuid()));
        contact.setProfessions(format("professions%s", uuid()));
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
        dto.setEmail(uuid());
        dto.setProfessions(uuid());
        dto.setIndustries(uuid());
        dto.setLabels(uuids());
        dto.setDateOfBirth(now());
        return dto;
    }

    public static ContactsQueryDto contactsQueryDto() {
        var query = new ContactsQueryDto();
        query.setFilter(new ContactsFilterDto());
        query.setPagination(new PaginationDto());
        return query;
    }
}
