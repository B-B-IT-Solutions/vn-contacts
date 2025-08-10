package cz.prm.utils;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUtils.randomInt;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.randomShort;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ComponentTestUtils.uuids;
import static java.lang.String.format;
import static java.time.Instant.now;

import cz.prm.controllers.dto.common.PaginationDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.ContactEditDto;
import cz.prm.controllers.dto.contact.FirstInteractionDto;
import cz.prm.controllers.dto.contact.IdealClientDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.PastClientDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.FirstInteraction;
import cz.prm.domain.contact.IdealClient;
import cz.prm.domain.contact.Occupation;
import cz.prm.domain.contact.PastClient;
import java.util.List;

public class ContactComponentTestUtils {

    public static ContactEditDto contactEditDto() {
        var contact = contactDto();
        var about = aboutDto(contact);
        var ce = new ContactEditDto();
        ce.setContact(contact);
        ce.setAbout(about);
        return ce;
    }

    public static List<Contact> contacts() {
        return newArrayList(contact(), contact(), contact());
    }

    public static Contact contact() {
        var contact = new Contact();
        contact.setFirstName(format("First%s", uuid()));
        contact.setLastName(format("Last%s", uuid()));
        contact.setEmail(uuid());
        contact.setPhoneNumber(uuid());
        contact.setLinkedInUrl(uuid());
        contact.setDateOfBirth(now());
        contact.setStatus(format("Status%s", uuid()));
        contact.setSource(format("Source%s", uuid()));
        contact.setCountry(format("Country%s", uuid()));
        contact.setCity(format("City%s", uuid()));
        contact.setTrustScore(randomInt());
        contact.setOccupation(occupation());
        contact.setLabels(uuids());
        contact.setSkills(uuids());
        contact.setProducts(uuids());
        contact.setTargetMarkets(uuids());
        contact.setIndustries(uuids());
        return contact;
    }

    public static ContactDto contactDto() {
        var dto = new ContactDto();
        dto.setFirstName(uuid());
        dto.setLastName(uuid());
        dto.setEmail(uuid());
        dto.setPhoneNumber(uuid());
        dto.setLinkedInUrl(uuid());
        dto.setDateOfBirth(now());
        dto.setStatus(uuid());
        dto.setSource(uuid());
        dto.setCountry(format("Country%s", uuid()));
        dto.setCity(format("City%s", uuid()));
        dto.setTrustScore(randomInt());
        dto.setOccupation(occupationDto());
        dto.setLabels(uuids());
        dto.setIndustries(uuids());
        dto.setSkills(uuids());
        dto.setProducts(uuids());
        dto.setTargetMarkets(uuids());
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

    public static About about(Contact contact) {
        var about = new About(contact.getContactId());
        about.setDescription(format("description%s", uuid()));
        about.setIdealClients(idealClients());
        about.setPastClients(pastClients());
        about.setContactGoals(format("contactGoals%s", uuid()));
        about.setContactChallenges(format("contactChallenges%s", uuid()));
        about.setFirstInteraction(firstInteraction());
        return about;
    }

    public static AboutDto aboutDto(ContactDto contact) {
        var about = new AboutDto();
        about.setContactId(contact.getContactId());
        about.setDescription(format("description%s", uuid()));
        about.setIdealClients(idealClientsDto());
        about.setPastClients(pastClientsDto());
        about.setContactGoals(format("contactGoals%s", uuid()));
        about.setContactChallenges(format("contactChallenges%s", uuid()));
        about.setFirstInteraction(firstInteractionDto());
        return about;
    }

    public static List<IdealClient> idealClients() {
        return newArrayList(idealClient(), idealClient(), idealClient());
    }

    public static List<IdealClientDto> idealClientsDto() {
        return newArrayList(idealClientDto(), idealClientDto(), idealClientDto());
    }

    public static IdealClient idealClient() {
        var ic = new IdealClient();
        ic.setIdealClientId(randomLong());
        ic.setName(uuid());
        ic.setCharacteristics(uuids());
        ic.setNeeds(uuid());
        ic.setGoals(uuid());
        ic.setOrder(randomShort());
        return ic;
    }

    public static IdealClientDto idealClientDto() {
        var ic = new IdealClientDto();
        ic.setName(uuid());
        ic.setCharacteristics(uuids());
        ic.setNeeds(uuid());
        ic.setGoals(uuid());
        ic.setOrder(randomShort());
        return ic;
    }

    public static List<PastClient> pastClients() {
        return newArrayList(pastClient(), pastClient(), pastClient());
    }

    public static List<PastClientDto> pastClientsDto() {
        return newArrayList(pastClientDto(), pastClientDto(), pastClientDto());
    }

    public static PastClient pastClient() {
        var ic = new PastClient();
        ic.setPastClientId(randomLong());
        ic.setName(uuid());
        ic.setCharacteristics(uuids());
        ic.setProvidedServices(uuid());
        ic.setOutcomes(uuid());
        ic.setOrder(randomShort());
        return ic;
    }

    public static PastClientDto pastClientDto() {
        var ic = new PastClientDto();
        ic.setName(uuid());
        ic.setCharacteristics(uuids());
        ic.setProvidedServices(uuid());
        ic.setOutcomes(uuid());
        ic.setOrder(randomShort());
        return ic;
    }

    public static FirstInteraction firstInteraction() {
        var fi = new FirstInteraction();
        fi.setType(format("type%s", uuid()));
        fi.setSource(format("source%s", uuid()));
        fi.setDate(now());
        fi.setNotes(format("notes%s", uuid()));
        return fi;
    }

    public static FirstInteractionDto firstInteractionDto() {
        var fi = new FirstInteractionDto();
        fi.setType(format("type%s", uuid()));
        fi.setSource(format("source%s", uuid()));
        fi.setDate(now());
        fi.setNotes(format("notes%s", uuid()));
        return fi;
    }

    public static ContactsQueryDto contactsQueryDto() {
        var query = new ContactsQueryDto();
        query.setFilter(new ContactsFilterDto());
        query.setPagination(new PaginationDto());
        return query;
    }
}
