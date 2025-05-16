package cz.prm.services.contact.data;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

import cz.prm.domain.contact.About;
import cz.prm.domain.contact.IdealClient;
import cz.prm.domain.contact.Meeting;
import cz.prm.repositories.contact.AboutPredicates;
import cz.prm.repositories.contact.AboutRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AboutService {

    private AboutRepository repository;
    private AboutPredicates predicates;

    @Autowired
    public AboutService(AboutRepository repository, AboutPredicates predicates) {
        this.repository = repository;
        this.predicates = predicates;
    }

    public About getAbout(Long contactId) {
        return getByContactId(contactId);
    }

    public void createAbout(Long contactId) {
        var about = new About(contactId);
        repository.save(about);
    }

    public void updateAbout(Long contactId, About updatedAbout) {
        var savedAbout = getByContactId(contactId);
        updateAboutFields(savedAbout, updatedAbout);
        repository.save(savedAbout);
    }

    public void deleteAbout(Long contactId) {
        var about = getByContactId(contactId);
        repository.deleteById(about.getContactId());
    }

    private void updateAboutFields(About savedAbout, About updatedAbout) {
        savedAbout.setDescription(updatedAbout.getDescription());
        savedAbout.setContactGoals(updatedAbout.getContactGoals());
        savedAbout.setContactChallenges(updatedAbout.getContactChallenges());
        savedAbout.setMyBenefits(updatedAbout.getMyBenefits());
        updateIdealClients(savedAbout.getIdealClients(), updatedAbout.getIdealClients());
        updateFirstMeetingFields(savedAbout.getFirstMeeting(), updatedAbout.getFirstMeeting());
    }

    private void updateIdealClients(List<IdealClient> savedIcs, List<IdealClient> updatedIcs) {
        var savedIcsMap = savedIcs.stream().collect(toMap(IdealClient::getIdealClientId, (ic) -> ic));
        var updatedIcsMap = updatedIcs.stream().collect(toMap(IdealClient::getIdealClientId, (ic) -> ic));

        updatedIcs.forEach(uic -> {
            var sic = savedIcsMap.get(uic.getIdealClientId());
            if (isNull(sic)) {
                savedIcs.add(uic);
            } else {
                updateIdealClientFields(sic, uic);
            }
        });

        savedIcsMap.forEach((sicId, sic) -> {
            var uic = updatedIcsMap.get(sicId);
            if (isNull(uic)) {
                savedIcs.remove(sic);
            }
        });
    }

    private void updateIdealClientFields(IdealClient savedIc, IdealClient updatedIc) {
        savedIc.setCharacteristics(updatedIc.getCharacteristics());
        savedIc.setNeeds(updatedIc.getNeeds());
        savedIc.setGoals(updatedIc.getGoals());
    }

    private void updateFirstMeetingFields(Meeting savedMeeting, Meeting updatedMeeting) {
        savedMeeting.setOccurrenceDate(updatedMeeting.getOccurrenceDate());
        savedMeeting.setLocation(updatedMeeting.getLocation());
        savedMeeting.setComment(updatedMeeting.getComment());
    }

    private About getByContactId(Long contactId) {
        var predicate = predicates.byContactId(contactId);
        var optional = repository.findOne(predicate);
        return optional.orElseThrow(entityNotFoundSupplier(contactId));
    }

    private Supplier<EntityNotFoundException> entityNotFoundSupplier(Long contactId) {
        return () -> new EntityNotFoundException(format("About for given contact id=[%s] not found!", contactId));
    }
}
