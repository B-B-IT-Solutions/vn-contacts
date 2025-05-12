package cz.prm.utils;

import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ComponentTestUtils.uuids;
import static cz.prm.utils.TestUtils.randomInt;
import static java.lang.String.format;
import static java.time.Instant.now;
import static org.assertj.core.util.Lists.newArrayList;

import com.google.common.collect.Lists;
import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.contact.ConnectionDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.Meeting;
import cz.prm.domain.contact.Occupation;
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
        contact.setKnowScore(randomInt());
        contact.setLikeScore(randomInt());
        contact.setTrustScore(randomInt());
        contact.setTelephones(connections());
        contact.setEmails(connections());
        contact.setUrls(connections());
        contact.setOccupation(occupation());
        contact.setLabels(uuids());
        contact.setIndustries(uuids());
        contact.setDateOfBirth(now());
        return contact;
    }

    public static ContactDto contactDto() {
        var dto = new ContactDto();
        dto.setFirstName(uuid());
        dto.setMiddleName(uuid());
        dto.setLastName(uuid());
        dto.setNickName(uuid());
        dto.setKnowScore(randomInt());
        dto.setLikeScore(randomInt());
        dto.setTrustScore(randomInt());
        dto.setTelephones(connectionsDto());
        dto.setEmails(connectionsDto());
        dto.setUrls(connectionsDto());
        dto.setOccupation(occupationDto());
        dto.setLabels(uuids());
        dto.setIndustries(uuids());
        dto.setDateOfBirth(now());
        return dto;
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

    public static About about(Contact contact) {
        var about = new About(contact.getContactId());
        about.setDescription(format("description%s", uuid()));
        about.setContactGoals(format("contactGoals%s", uuid()));
        about.setContactChallenges(format("contactChallenges%s", uuid()));
        about.setMyBenefits(format("myBenefits%s", uuid()));
        about.setFirstMeeting(meeting());
        return about;
    }

    public static Meeting meeting() {
        var meeting = new Meeting();
        meeting.setOccurrenceDate(now());
        meeting.setLocation(format("location%s", uuid()));
        meeting.setComment(format("comment%s", uuid()));
        return meeting;
    }

    public static ContactsQueryDto contactsQueryDto() {
        var query = new ContactsQueryDto();
        query.setFilter(new ContactsFilterDto());
        query.setPagination(new PaginationDto());
        return query;
    }
}
