package cz.prm.utils.assertions;

import static cz.prm.utils.TimeComponentTestUtils.ONE_SECOND_OFFSET;
import static cz.prm.utils.assertions.ConditionsUtils.nullOrEquals;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.FirstInteractionDto;
import cz.prm.controllers.dto.contact.IdealClientDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.PastClientDto;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.FirstInteraction;
import cz.prm.domain.contact.IdealClient;
import cz.prm.domain.contact.Occupation;
import cz.prm.domain.contact.PastClient;
import java.util.List;
import java.util.Objects;

public class ContractComponentTestAssertions {

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
        assertThat(contact.getLastName()).isEqualTo(contactDto.getLastName());
        assertThat(contact.getEmail()).isEqualTo(contactDto.getEmail());
        assertThat(contact.getPhoneNumber()).isEqualTo(contactDto.getPhoneNumber());
        assertThat(contact.getLinkedInUrl()).isEqualTo(contactDto.getLinkedInUrl());
        assertThat(contact.getDateOfBirth()).isCloseTo(contactDto.getDateOfBirth(), within(1, SECONDS));
        assertThat(contact.getStatus()).isEqualTo(contactDto.getStatus());
        assertThat(contact.getSource()).isEqualTo(contactDto.getSource());
        assertThat(contact.getCountry()).isEqualTo(contactDto.getCountry());
        assertThat(contact.getCity()).isEqualTo(contactDto.getCity());
        assertThat(contact.getTrustScore()).isEqualTo(contactDto.getTrustScore());
        assertThat(contact.getLabels()).isNotEmpty().containsExactlyElementsOf(contactDto.getLabels());
        assertThat(contact.getIndustries()).isNotEmpty().containsExactlyElementsOf(contactDto.getIndustries());
        assertThat(contact.getSkills()).containsExactlyElementsOf(contactDto.getSkills());
        assertThat(contact.getProducts()).containsExactlyElementsOf(contactDto.getProducts());
        assertThat(contact.getTargetMarkets()).containsExactlyElementsOf(contactDto.getTargetMarkets());
        assertThat(contact.getLastEditDate()).isNotNull();
        assertThat(contact.getCreationDate()).isNotNull();
        assertOccupationDto(contact.getOccupation(), contactDto.getOccupation());
    }

    public static void assertOccupationDto(Occupation occupation, OccupationDto dto) {
        assertThat(occupation.getJobTitle()).isEqualTo(dto.getJobTitle());
        assertThat(occupation.getCompany()).isEqualTo(dto.getCompany());
        assertThat(occupation.getIndustry()).isEqualTo(dto.getIndustry());
    }

    public static void assertAbout(About about, AboutDto dto) {
        assertThat(about.getDescription()).isEqualTo(dto.getDescription());
        assertThat(about.getContactGoals()).isEqualTo(dto.getContactGoals());
        assertThat(about.getContactChallenges()).isEqualTo(dto.getContactChallenges());
        assertThat(about.getContactId()).isEqualTo(dto.getContactId());
        assertIdealClientsDto(about.getIdealClients(), dto.getIdealClients());
        assertPastClientsDto(about.getPastClients(), dto.getPastClients());
        assertMeetingDto(about.getFirstInteraction(), dto.getFirstInteraction());
    }

    public static void assertIdealClientsDto(List<IdealClient> ics, List<IdealClientDto> dtos) {
        assertThat(ics).isNotEmpty().hasSameSizeAs(dtos);
        ics.forEach(c1 -> {
            var c2 = dtos.stream().filter(u -> Objects.equals(c1.getName(), u.getName())).findFirst().get();
            assertIdealClientDto(c1, c2);
        });
    }

    public static void assertIdealClientDto(IdealClient ic, IdealClientDto dto) {
        assertThat(ic.getIdealClientId()).isNotNull();
        assertThat(dto.getIdealClientId()).is(nullOrEquals(ic.getIdealClientId()));
        assertThat(ic.getName()).isEqualTo(dto.getName());
        assertThat(ic.getCharacteristics()).containsExactlyElementsOf(dto.getCharacteristics());
        assertThat(ic.getNeeds()).isEqualTo(dto.getNeeds());
        assertThat(ic.getGoals()).isEqualTo(dto.getGoals());
        assertThat(ic.getOrder()).isEqualTo(dto.getOrder());
    }

    public static void assertPastClientsDto(List<PastClient> pcs, List<PastClientDto> dtos) {
        assertThat(pcs).isNotEmpty().hasSameSizeAs(dtos);
        pcs.forEach(c1 -> {
            var c2 = dtos.stream().filter(u -> Objects.equals(c1.getName(), u.getName())).findFirst().get();
            assertPastClientDto(c1, c2);
        });
    }

    public static void assertPastClientDto(PastClient pc, PastClientDto dto) {
        assertThat(pc.getPastClientId()).isNotNull();
        assertThat(dto.getPastClientId()).is(nullOrEquals(pc.getPastClientId()));
        assertThat(pc.getName()).isEqualTo(dto.getName());
        assertThat(pc.getCharacteristics()).containsExactlyElementsOf(dto.getCharacteristics());
        assertThat(pc.getProvidedServices()).isEqualTo(dto.getProvidedServices());
        assertThat(pc.getOutcomes()).isEqualTo(dto.getOutcomes());
        assertThat(pc.getOrder()).isEqualTo(dto.getOrder());
    }

    public static void assertMeetingDto(FirstInteraction fi, FirstInteractionDto dto) {
        assertThat(fi.getType()).isEqualTo(dto.getType());
        assertThat(fi.getSource()).isEqualTo(dto.getSource());
        assertThat(fi.getDate()).isCloseTo(dto.getDate(), ONE_SECOND_OFFSET);
        assertThat(fi.getNotes()).isEqualTo(dto.getNotes());
    }
}
