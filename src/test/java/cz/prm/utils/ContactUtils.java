package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.CommonUtils.pagination;
import static cz.prm.utils.CommonUtils.paginationDto;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.TestUtils.uuids;
import static java.lang.String.format;
import static java.time.Instant.now;

import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ConnectionDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.MeetingDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.Meeting;
import cz.prm.domain.contact.Occupation;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import java.util.List;

public class ContactUtils {

    public static List<Contact> contacts() {
        return newArrayList(contact(), contact(), contact());
    }

    public static Contact contact() {
        var contact = new Contact();
        contact.setContactId(randomLong());
        contact.setFirstName(uuid());
        contact.setMiddleName(uuid());
        contact.setLastName(uuid());
        contact.setNickName(uuid());
        contact.setTelephones(connections());
        contact.setEmails(connections());
        contact.setUrls(connections());
        contact.setOccupation(occupation());
        contact.setLabels(uuids());
        contact.setOwner(user());
        contact.setDateOfBirth(now());
        contact.setLastEditDate(now());
        contact.setCreationDate(now());
        return contact;
    }

    public static ContactDto contactDto() {
        var contact = new ContactDto();
        contact.setContactId(randomLong());
        contact.setFirstName(uuid());
        contact.setMiddleName(uuid());
        contact.setLastName(uuid());
        contact.setNickName(uuid());
        contact.setTelephones(connectionsDto());
        contact.setEmails(connectionsDto());
        contact.setUrls(connectionsDto());
        contact.setOccupation(occupationDto());
        contact.setLabels(uuids());
        contact.setDateOfBirth(now());
        contact.setLastEditDate(now());
        contact.setCreationDate(now());
        return contact;
    }

    public static Occupation occupation() {
        var occupation = new Occupation();
        occupation.setJobTitle(format("jobTitle%s", uuid()));
        occupation.setCompany(format("company%s", uuid()));
        occupation.setIndustry(format("industry%s", uuid()));
        return occupation;
    }

    public static OccupationDto occupationDto() {
        var occupation = new OccupationDto();
        occupation.setJobTitle(format("jobTitle%s", uuid()));
        occupation.setCompany(format("company%s", uuid()));
        occupation.setIndustry(format("industry%s", uuid()));
        return occupation;
    }

    public static List<Connection> connections() {
        return newArrayList(connection(), connection(), connection());
    }

    public static List<ConnectionDto> connectionsDto() {
        return newArrayList(connectionDto(), connectionDto(), connectionDto());
    }

    public static Connection connection() {
        var connection = new Connection();
        connection.setValue(uuid());
        connection.setType(uuid());
        return connection;
    }

    public static ConnectionDto connectionDto() {
        var dto = new ConnectionDto();
        dto.setValue(uuid());
        dto.setType(uuid());
        return dto;
    }

    public static About about() {
        var about = new About();
        about.setDescription(uuid());
        about.setContactId(randomLong());
        about.setOwner(user());
        about.setFirstMeeting(meeting());
        return about;
    }

    public static AboutDto aboutDto() {
        var dto = new AboutDto();
        dto.setDescription(uuid());
        dto.setContactId(randomLong());
        dto.setFirstMeeting(meetingDto());
        return dto;
    }

    public static Meeting meeting() {
        var meeting = new Meeting();
        meeting.setOccurrenceDate(now());
        meeting.setLocation(uuid());
        meeting.setComment(uuid());
        return meeting;
    }

    public static MeetingDto meetingDto() {
        var dto = new MeetingDto();
        dto.setOccurrenceDate(now());
        dto.setLocation(uuid());
        dto.setComment(uuid());
        return dto;
    }

    public static ContactsQuery contactsQuery() {
        var query = new ContactsQuery();
        query.setPagination(pagination());
        query.setFilter(contactsFilter());
        query.setSort(uuid());
        return query;
    }

    public static ContactsQueryDto contactsQueryDto() {
        var query = new ContactsQueryDto();
        query.setPagination(paginationDto());
        query.setFilter(contactsFilterDto());
        query.setSort(uuid());
        return query;
    }

    public static ContactsFilter contactsFilter() {
        var filter = new ContactsFilter();
        filter.setFirstName(uuid());
        filter.setLastName(uuid());
        filter.setEmail(uuid());
        return filter;
    }

    public static ContactsFilterDto contactsFilterDto() {
        var filter = new ContactsFilterDto();
        filter.setFirstName(uuid());
        filter.setLastName(uuid());
        filter.setEmail(uuid());
        return filter;
    }
}
