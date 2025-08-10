package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.CommonAssertions.assertQuery;
import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.DecoratedContactDto;
import cz.prm.controllers.dto.contact.FirstInteractionDto;
import cz.prm.controllers.dto.contact.IdealClientDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.PastClientDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.DecoratedContact;
import cz.prm.domain.contact.FirstInteraction;
import cz.prm.domain.contact.IdealClient;
import cz.prm.domain.contact.Occupation;
import cz.prm.domain.contact.PastClient;
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

    public static void assertDecoratedContact(DecoratedContact ce1, DecoratedContact ce2) {
        assertContact(ce1.getContact(), ce2.getContact());
        assertAbout(ce1.getAbout(), ce2.getAbout());
    }

    public static void assertDecoratedContact(DecoratedContact ce, DecoratedContactDto dto) {
        assertContact(ce.getContact(), dto.getContact());
        assertAbout(ce.getAbout(), dto.getAbout());
    }

    public static void assertContact(Contact contact1, Contact contact2) {
        assertThat(contact1.getContactId()).isEqualTo(contact2.getContactId());
        assertThat(contact1.getFirstName()).isEqualTo(contact2.getFirstName());
        assertThat(contact1.getLastName()).isEqualTo(contact2.getLastName());
        assertThat(contact1.getEmail()).isEqualTo(contact2.getEmail());
        assertThat(contact1.getPhoneNumber()).isEqualTo(contact2.getPhoneNumber());
        assertThat(contact1.getLinkedInUrl()).isEqualTo(contact2.getLinkedInUrl());
        assertThat(contact1.getDateOfBirth()).isEqualTo(contact2.getDateOfBirth());
        assertThat(contact1.getStatus()).isEqualTo(contact2.getStatus());
        assertThat(contact1.getSource()).isEqualTo(contact2.getSource());
        assertThat(contact1.getCountry()).isEqualTo(contact2.getCountry());
        assertThat(contact1.getCity()).isEqualTo(contact2.getCity());
        assertThat(contact1.getTrustScore()).isEqualTo(contact2.getTrustScore());
        assertThat(contact1.getLabels()).containsExactlyElementsOf(contact2.getLabels());
        assertThat(contact1.getIndustries()).containsExactlyElementsOf(contact2.getIndustries());
        assertThat(contact1.getSkills()).containsExactlyElementsOf(contact2.getSkills());
        assertThat(contact1.getProducts()).containsExactlyElementsOf(contact2.getProducts());
        assertThat(contact1.getTargetMarkets()).containsExactlyElementsOf(contact2.getTargetMarkets());
        assertThat(contact1.getOwner()).isEqualTo(contact2.getOwner());
        assertThat(contact1.getLastEditDate()).isEqualTo(contact2.getLastEditDate());
        assertThat(contact1.getCreationDate()).isEqualTo(contact2.getCreationDate());
        assertOccupation(contact1.getOccupation(), contact2.getOccupation());
    }

    public static void assertContact(Contact contact, ContactDto dto) {
        assertThat(contact.getContactId()).isEqualTo(dto.getContactId());
        assertThat(contact.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(contact.getLastName()).isEqualTo(dto.getLastName());
        assertThat(contact.getEmail()).isEqualTo(dto.getEmail());
        assertThat(contact.getPhoneNumber()).isEqualTo(dto.getPhoneNumber());
        assertThat(contact.getLinkedInUrl()).isEqualTo(dto.getLinkedInUrl());
        assertThat(contact.getDateOfBirth()).isEqualTo(dto.getDateOfBirth());
        assertThat(contact.getStatus()).isEqualTo(dto.getStatus());
        assertThat(contact.getSource()).isEqualTo(dto.getSource());
        assertThat(contact.getCountry()).isEqualTo(dto.getCountry());
        assertThat(contact.getCity()).isEqualTo(dto.getCity());
        assertThat(contact.getTrustScore()).isEqualTo(dto.getTrustScore());
        assertThat(contact.getLabels()).containsExactlyElementsOf(dto.getLabels());
        assertThat(contact.getIndustries()).containsExactlyElementsOf(dto.getIndustries());
        assertThat(contact.getSkills()).containsExactlyElementsOf(dto.getSkills());
        assertThat(contact.getProducts()).containsExactlyElementsOf(dto.getProducts());
        assertThat(contact.getTargetMarkets()).containsExactlyElementsOf(dto.getTargetMarkets());
        assertThat(contact.getLastEditDate()).isEqualTo(dto.getLastEditDate());
        assertThat(contact.getCreationDate()).isEqualTo(dto.getCreationDate());
        assertOccupationDto(contact.getOccupation(), dto.getOccupation());
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

    public static void assertAbout(About about1, About about2) {
        assertThat(about1.getDescription()).isEqualTo(about2.getDescription());
        assertThat(about1.getContactGoals()).isEqualTo(about2.getContactGoals());
        assertThat(about1.getContactChallenges()).isEqualTo(about2.getContactChallenges());
        assertThat(about1.getContactId()).isEqualTo(about2.getContactId());
        assertIdealClients(about1.getIdealClients(), about2.getIdealClients());
        assertPastClients(about1.getPastClients(), about2.getPastClients());
        assertFirstInteraction(about1.getFirstInteraction(), about2.getFirstInteraction());
    }

    public static void assertAbout(About about, AboutDto dto) {
        assertThat(about.getDescription()).isEqualTo(dto.getDescription());
        assertThat(about.getContactGoals()).isEqualTo(dto.getContactGoals());
        assertThat(about.getContactChallenges()).isEqualTo(dto.getContactChallenges());
        assertThat(about.getContactId()).isEqualTo(dto.getContactId());
        assertIdealClientsDto(about.getIdealClients(), dto.getIdealClients());
        assertPastClientsDto(about.getPastClients(), dto.getPastClients());
        assertFirstInteractionDto(about.getFirstInteraction(), dto.getFirstInteraction());
    }

    public static void assertIdealClients(List<IdealClient> ics1, List<IdealClient> ics2) {
        assertThat(ics1).isNotEmpty().hasSameSizeAs(ics2);
        ics1.forEach(c1 -> {
            var c2 = ics2.stream().filter(u -> {
                if (isNull(c1.getIdealClientId()) && isNull(u.getIdealClientId())) {
                    return Objects.equals(c1.getGoals(), u.getGoals());
                }
                return Objects.equals(c1.getIdealClientId(), u.getIdealClientId());
            }).findFirst().get();
            assertIdealClient(c1, c2);
        });
    }

    public static void assertIdealClientsDto(List<IdealClient> ics, List<IdealClientDto> dtos) {
        assertThat(ics).isNotEmpty().hasSameSizeAs(dtos);
        ics.forEach(c1 -> {
            var c2 = dtos.stream().filter(u -> Objects.equals(c1.getIdealClientId(), u.getIdealClientId())).findFirst().get();
            assertIdealClientDto(c1, c2);
        });
    }

    public static void assertIdealClient(IdealClient ic1, IdealClient ic2) {
        assertThat(ic1.getIdealClientId()).isEqualTo(ic2.getIdealClientId());
        assertThat(ic1.getName()).isEqualTo(ic2.getName());
        assertThat(ic1.getCharacteristics()).containsExactlyElementsOf(ic2.getCharacteristics());
        assertThat(ic1.getNeeds()).isEqualTo(ic2.getNeeds());
        assertThat(ic1.getGoals()).isEqualTo(ic2.getGoals());
        assertThat(ic1.getOrder()).isEqualTo(ic2.getOrder());
    }

    public static void assertIdealClientDto(IdealClient ic, IdealClientDto dto) {
        assertThat(ic.getIdealClientId()).isEqualTo(dto.getIdealClientId());
        assertThat(ic.getName()).isEqualTo(dto.getName());
        assertThat(ic.getCharacteristics()).containsExactlyElementsOf(dto.getCharacteristics());
        assertThat(ic.getNeeds()).isEqualTo(dto.getNeeds());
        assertThat(ic.getGoals()).isEqualTo(dto.getGoals());
        assertThat(ic.getOrder()).isEqualTo(dto.getOrder());
    }

    public static void assertPastClients(List<PastClient> pcs1, List<PastClient> pcs2) {
        assertThat(pcs1).isNotEmpty().hasSameSizeAs(pcs2);
        pcs1.forEach(c1 -> {
            var c2 = pcs2.stream().filter(u -> {
                if (isNull(c1.getPastClientId()) && isNull(u.getPastClientId())) {
                    return Objects.equals(c1.getOutcomes(), u.getOutcomes());
                }
                return Objects.equals(c1.getPastClientId(), u.getPastClientId());
            }).findFirst().get();
            assertPastClient(c1, c2);
        });
    }

    public static void assertPastClientsDto(List<PastClient> pcs, List<PastClientDto> dtos) {
        assertThat(pcs).isNotEmpty().hasSameSizeAs(dtos);
        pcs.forEach(c1 -> {
            var c2 = dtos.stream().filter(u -> Objects.equals(c1.getPastClientId(), u.getPastClientId())).findFirst().get();
            assertPastClientDto(c1, c2);
        });
    }

    public static void assertPastClient(PastClient pc1, PastClient pc2) {
        assertThat(pc1.getPastClientId()).isEqualTo(pc2.getPastClientId());
        assertThat(pc1.getName()).isEqualTo(pc2.getName());
        assertThat(pc1.getCharacteristics()).containsExactlyElementsOf(pc2.getCharacteristics());
        assertThat(pc1.getProvidedServices()).isEqualTo(pc2.getProvidedServices());
        assertThat(pc1.getOutcomes()).isEqualTo(pc2.getOutcomes());
        assertThat(pc1.getOrder()).isEqualTo(pc2.getOrder());
    }

    public static void assertPastClientDto(PastClient pc, PastClientDto dto) {
        assertThat(pc.getPastClientId()).isEqualTo(dto.getPastClientId());
        assertThat(pc.getName()).isEqualTo(dto.getName());
        assertThat(pc.getCharacteristics()).containsExactlyElementsOf(dto.getCharacteristics());
        assertThat(pc.getProvidedServices()).isEqualTo(dto.getProvidedServices());
        assertThat(pc.getOutcomes()).isEqualTo(dto.getOutcomes());
        assertThat(pc.getOrder()).isEqualTo(dto.getOrder());
    }

    public static void assertFirstInteraction(FirstInteraction fi1, FirstInteraction fi2) {
        assertThat(fi1.getDate()).isEqualTo(fi2.getDate());
        assertThat(fi1.getSource()).isEqualTo(fi2.getSource());
        assertThat(fi1.getNotes()).isEqualTo(fi2.getNotes());
    }

    public static void assertFirstInteractionDto(FirstInteraction fi, FirstInteractionDto dto) {
        assertThat(fi.getType()).isEqualTo(dto.getType());
        assertThat(fi.getSource()).isEqualTo(dto.getSource());
        assertThat(fi.getDate()).isEqualTo(dto.getDate());
        assertThat(fi.getNotes()).isEqualTo(dto.getNotes());
    }

    public static void assertContactQuery(ContactsQuery query, ContactsQueryDto dto) {
        assertQuery(query, dto);
        assertContactFilter(query.getFilter(), dto.getFilter());
    }

    public static void assertContactFilter(ContactsFilter filter, ContactsFilterDto dto) {
        assertThat(filter.getGlobalFilter()).isEqualTo(dto.getGlobalFilter());
        assertThat(filter.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(filter.getLastName()).isEqualTo(dto.getLastName());
        assertThat(filter.getStatus()).isEqualTo(dto.getStatus());
        assertThat(filter.getSource()).isEqualTo(dto.getSource());
        assertThat(filter.getLabels()).isEqualTo(dto.getLabels());
        assertThat(filter.getIndustries()).isEqualTo(dto.getIndustries());
    }
}
