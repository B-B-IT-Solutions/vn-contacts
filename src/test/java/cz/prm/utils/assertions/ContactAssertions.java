package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ConnectionDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.MeetingDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.Meeting;
import cz.prm.domain.contact.Occupation;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.PageImpl;

public class ContactAssertions {

    public static void assertPage(Page<Contact> page, PageDto<ContactDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertContactsDto(page.getContent(), pageDto.getContent());
    }

    public static void assertPage(Page<Contact> page1, PageImpl<Contact> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getNumber());
        assertContacts(page1.getContent(), page2.getContent());
    }

    public static void assertContacts(List<Contact> contacts1, List<Contact> contacts2) {
        assertThat(contacts1).isNotEmpty().hasSameSizeAs(contacts2);
        contacts1.forEach(c1 -> {
            var c2 = contacts2.stream().filter(u -> Objects.equals(c1.getContactId(), u.getContactId())).findFirst().get();
            assertContact(c1, c2);
        });
    }

    public static void assertContactsDto(List<Contact> contacts, List<ContactDto> dtos) {
        assertThat(contacts).isNotEmpty().hasSameSizeAs(dtos);
        contacts.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertContact(u1, u2);
        });
    }

    public static void assertContact(Contact contact1, Contact contact2) {
        assertThat(contact1.getContactId()).isEqualTo(contact2.getContactId());
        assertThat(contact1.getFirstName()).isEqualTo(contact2.getFirstName());
        assertThat(contact1.getMiddleName()).isEqualTo(contact2.getMiddleName());
        assertThat(contact1.getLastName()).isEqualTo(contact2.getLastName());
        assertThat(contact1.getNickName()).isEqualTo(contact2.getNickName());
        assertThat(contact1.getLabels()).containsExactlyElementsOf(contact2.getLabels());
        assertThat(contact1.getIndustries()).containsExactlyElementsOf(contact2.getIndustries());
        assertThat(contact1.getOwner()).isEqualTo(contact2.getOwner());
        assertThat(contact1.getDateOfBirth()).isEqualTo(contact2.getDateOfBirth());
        assertThat(contact1.getLastEditDate()).isEqualTo(contact2.getLastEditDate());
        assertThat(contact1.getCreationDate()).isEqualTo(contact2.getCreationDate());
        assertOccupation(contact1.getOccupation(), contact2.getOccupation());
        assertConnections(contact1.getTelephones(), contact2.getTelephones());
        assertConnections(contact1.getEmails(), contact2.getEmails());
        assertConnections(contact1.getUrls(), contact2.getUrls());
    }

    public static void assertContact(Contact contact, ContactDto dto) {
        assertThat(contact.getContactId()).isEqualTo(dto.getContactId());
        assertThat(contact.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(contact.getMiddleName()).isEqualTo(dto.getMiddleName());
        assertThat(contact.getLastName()).isEqualTo(dto.getLastName());
        assertThat(contact.getNickName()).isEqualTo(dto.getNickName());
        assertThat(contact.getLabels()).containsExactlyElementsOf(dto.getLabels());
        assertThat(contact.getIndustries()).containsExactlyElementsOf(dto.getIndustries());
        assertThat(contact.getDateOfBirth()).isEqualTo(dto.getDateOfBirth());
        assertThat(contact.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertThat(contact.getCreationDate()).isEqualTo(dto.getCreationDate());
        assertOccupationDto(contact.getOccupation(), dto.getOccupation());
        assertConnectionsDto(contact.getTelephones(), dto.getTelephones());
        assertConnectionsDto(contact.getEmails(), dto.getEmails());
        assertConnectionsDto(contact.getUrls(), dto.getUrls());
    }

    public static void assertOccupation(Occupation occupation1, Occupation occupation2) {
        assertThat(occupation1.getJobTitle()).isEqualTo(occupation2.getJobTitle());
        assertThat(occupation1.getCompany()).isEqualTo(occupation2.getCompany());
        assertThat(occupation1.getIndustry()).isEqualTo(occupation2.getIndustry());
    }

    public static void assertOccupationDto(Occupation occupation, OccupationDto dto) {
        assertThat(occupation.getJobTitle()).isEqualTo(dto.getJobTitle());
        assertThat(occupation.getCompany()).isEqualTo(dto.getCompany());
        assertThat(occupation.getIndustry()).isEqualTo(dto.getIndustry());
    }

    public static void assertConnections(List<Connection> cons1, List<Connection> cons2) {
        assertThat(cons1).isNotEmpty().hasSameSizeAs(cons2);
        cons1.forEach(c1 -> {
            var c2 = cons2.stream().filter(u -> Objects.equals(c1.getValue(), u.getValue())).findFirst().get();
            assertConnection(c1, c2);
        });
    }

    public static void assertConnectionsDto(List<Connection> cons, List<ConnectionDto> dtos) {
        assertThat(cons).isNotEmpty().hasSameSizeAs(dtos);
        cons.forEach(c1 -> {
            var c2 = dtos.stream().filter(u -> Objects.equals(c1.getValue(), u.getValue())).findFirst().get();
            assertConnectionDto(c1, c2);
        });
    }

    public static void assertConnection(Connection con1, Connection con2) {
        assertThat(con1.getValue()).isEqualTo(con2.getValue());
        assertThat(con1.getValue()).isEqualTo(con2.getValue());
    }

    public static void assertConnectionDto(Connection connection, ConnectionDto dto) {
        assertThat(connection.getValue()).isEqualTo(dto.getValue());
        assertThat(connection.getValue()).isEqualTo(dto.getValue());
    }

    public static void assertAbout(About about1, About about2) {
        assertThat(about1.getDescription()).isEqualTo(about2.getDescription());
        assertThat(about1.getContactGoals()).isEqualTo(about2.getContactGoals());
        assertThat(about1.getContactChallenges()).isEqualTo(about2.getContactChallenges());
        assertThat(about1.getMyBenefits()).isEqualTo(about2.getMyBenefits());
        assertThat(about1.getContactId()).isEqualTo(about2.getContactId());
        assertMeeting(about1.getFirstMeeting(), about2.getFirstMeeting());
    }

    public static void assertAboutDto(About about, AboutDto dto) {
        assertThat(about.getDescription()).isEqualTo(dto.getDescription());
        assertThat(about.getContactGoals()).isEqualTo(dto.getContactGoals());
        assertThat(about.getContactChallenges()).isEqualTo(dto.getContactChallenges());
        assertThat(about.getMyBenefits()).isEqualTo(dto.getMyBenefits());
        assertThat(about.getContactId()).isEqualTo(dto.getContactId());
        assertMeetingDto(about.getFirstMeeting(), dto.getFirstMeeting());
    }

    public static void assertMeeting(Meeting meeting1, Meeting meeting2) {
        assertThat(meeting1.getOccurrenceDate()).isEqualTo(meeting2.getOccurrenceDate());
        assertThat(meeting1.getLocation()).isEqualTo(meeting2.getLocation());
        assertThat(meeting1.getComment()).isEqualTo(meeting2.getComment());
    }

    public static void assertMeetingDto(Meeting meeting, MeetingDto dto) {
        assertThat(meeting.getOccurrenceDate()).isEqualTo(dto.getOccurrenceDate());
        assertThat(meeting.getLocation()).isEqualTo(dto.getLocation());
        assertThat(meeting.getComment()).isEqualTo(dto.getComment());
    }

    public static void assertContactQuery(ContactsQuery query, ContactsQueryDto dto) {
        assertQuery(query, dto);
        assertContactFilter(query.getFilter(), dto.getFilter());
    }

    public static void assertContactFilter(ContactsFilter filter, ContactsFilterDto dto) {
        assertThat(filter.getGlobalFilter()).isEqualTo(dto.getGlobalFilter());
        assertThat(filter.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(filter.getMiddleName()).isEqualTo(dto.getMiddleName());
        assertThat(filter.getLastName()).isEqualTo(dto.getLastName());
        assertThat(filter.getNickName()).isEqualTo(dto.getNickName());
        assertThat(filter.getLabels()).isEqualTo(dto.getLabels());
        assertThat(filter.getIndustries()).isEqualTo(dto.getIndustries());
    }
}
