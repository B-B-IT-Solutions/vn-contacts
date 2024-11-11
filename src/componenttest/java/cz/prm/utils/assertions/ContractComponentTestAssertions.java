package cz.prm.utils.assertions;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ConnectionDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.MeetingDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.Meeting;
import cz.prm.domain.contact.Occupation;
import java.util.List;
import java.util.Objects;

public class ContractComponentTestAssertions {

    private static final String ANOMYSATION_STRING = "*****";

    public static void assertContacts(List<Contact> contacts, PageDto<ContactDto> pageDto) {
        assertContacts(contacts, pageDto.getContent());
    }

    public static void assertContacts(List<Contact> contacts, List<ContactDto> dtos) {
        assertThat(contacts).isNotEmpty().hasSameSizeAs(dtos);
        contacts.forEach(u1 -> {
            var u2 = dtos.stream().filter(u -> Objects.equals(u1.getContactId(), u.getContactId())).findFirst().get();
            assertContact(u1, u2);
        });
    }

    public static void assertContact(Contact contact, ContactDto contactDto) {
        assertThat(contact.getContactId()).isEqualTo(contactDto.getContactId());
        assertThat(contact.getFirstName()).isEqualTo(contactDto.getFirstName());
        assertThat(contact.getMiddleName()).isEqualTo(contactDto.getMiddleName());
//        assertThat(contact.getLastName()).isEqualTo(contactDto.getLastName());
        assertThat(contact.getLastName()).isEqualTo(ANOMYSATION_STRING);
        assertThat(contact.getMiddleName()).isEqualTo(contactDto.getMiddleName());
        assertThat(contact.getLabels()).containsExactlyElementsOf(contactDto.getLabels());
        assertThat(contact.getIndustries()).containsExactlyElementsOf(contactDto.getIndustries());
        assertThat(contact.getDateOfBirth()).isCloseTo(contactDto.getDateOfBirth(), within(1, SECONDS));
        assertThat(contact.getLastEditDate()).isNotNull();
        assertThat(contact.getCreationDate()).isNotNull();
        assertOccupationDto(contact.getOccupation(), contactDto.getOccupation());
        assertConnectionsDto(contact.getTelephones(), contactDto.getTelephones());
        assertConnectionsDto(contact.getEmails(), contactDto.getEmails());
        assertConnectionsDto(contact.getUrls(), contactDto.getUrls());
    }

    public static void assertOccupationDto(Occupation occupation, OccupationDto dto) {
        assertThat(occupation.getJobTitle()).isEqualTo(dto.getJobTitle());
        assertThat(occupation.getCompany()).isEqualTo(dto.getCompany()).contains(ANOMYSATION_STRING);
        assertThat(occupation.getIndustry()).isEqualTo(dto.getIndustry());

        var firstLetter = occupation.getCompany().charAt(0);
        var anonymCompany = format("%s%s", firstLetter, ANOMYSATION_STRING);
        assertThat(dto.getCompany()).isEqualTo(anonymCompany);
    }

    public static void assertConnectionsDto(List<Connection> cons, List<ConnectionDto> dtos) {
        assertThat(cons).isNotEmpty().hasSameSizeAs(dtos);
        cons.forEach(c1 -> {
            var c2 = dtos.stream().filter(u -> Objects.equals(c1.getValue(), u.getValue())).findFirst().get();
            assertConnectionDto(c1, c2);
        });
    }

    public static void assertConnectionDto(Connection connection, ConnectionDto dto) {
        assertThat(connection.getValue()).isEqualTo(dto.getValue());
        assertThat(connection.getValue()).isEqualTo(dto.getValue());
    }

    public static void assertAboutDto(About about, AboutDto dto) {
        assertThat(about.getDescription()).isEqualTo(dto.getDescription());
        assertThat(about.getContactId()).isEqualTo(dto.getContactId());
        assertMeetingDto(about.getFirstMeeting(), dto.getFirstMeeting());
    }

    public static void assertMeetingDto(Meeting meeting, MeetingDto dto) {
        assertThat(meeting.getOccurrenceDate()).isEqualTo(dto.getOccurrenceDate());
        assertThat(meeting.getLocation()).isEqualTo(dto.getLocation());
        assertThat(meeting.getComment()).isEqualTo(dto.getComment());
    }
}
