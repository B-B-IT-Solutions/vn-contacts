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
import cz.prm.controllers.dto.contact.ConnectionDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.OccupationDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Connection;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.FirstInteraction;
import cz.prm.domain.contact.IdealClient;
import cz.prm.domain.contact.Occupation;
import cz.prm.domain.contact.PastClient;
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
        contact.setPhoneNumber(uuid());
        contact.setEmails(connections());
        contact.setUrls(connections());
        contact.setOccupation(occupation());
        contact.setLabels(uuids());
        contact.setSkills(uuids());
        contact.setProducts(uuids());
        contact.setTargetMarkets(uuids());
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
        dto.setPhoneNumber(uuid());
        dto.setEmails(connectionsDto());
        dto.setUrls(connectionsDto());
        dto.setOccupation(occupationDto());
        dto.setLabels(uuids());
        dto.setIndustries(uuids());
        dto.setSkills(uuids());
        dto.setProducts(uuids());
        dto.setTargetMarkets(uuids());
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
        return newArrayList(connection(), connection(), connection());
    }

    public static List<ConnectionDto> connectionsDto() {
        return newArrayList(connectionDto(), connectionDto(), connectionDto());
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
        about.setIdealClients(idealClients());
        about.setPastClients(pastClients());
        about.setContactGoals(format("contactGoals%s", uuid()));
        about.setContactChallenges(format("contactChallenges%s", uuid()));
        about.setMyBenefits(format("myBenefits%s", uuid()));
        about.setFirstInteraction(firstInteraction());
        return about;
    }

    public static List<IdealClient> idealClients() {
        return newArrayList(idealClient(), idealClient(), idealClient());
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

    public static List<PastClient> pastClients() {
        return newArrayList(pastClient(), pastClient(), pastClient());
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

    public static FirstInteraction firstInteraction() {
        var fi = new FirstInteraction();
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
