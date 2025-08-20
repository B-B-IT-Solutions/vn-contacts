package cz.prm.services.contact.data;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import cz.prm.domain.contacts.contact.About;
import cz.prm.domain.contacts.contact.FirstInteraction;
import cz.prm.domain.contacts.contact.IdealClient;
import cz.prm.domain.contacts.contact.PastClient;
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

    public About createAbout(Long contactId, About about) {
        about.setContactId(contactId);
        return repository.save(about);
    }

    public About updateAbout(Long contactId, About updatedAbout) {
        var savedAbout = getByContactId(contactId);
        updateAboutFields(savedAbout, updatedAbout);
        return repository.save(savedAbout);
    }

    public void deleteAbout(Long contactId) {
        var about = getByContactId(contactId);
        repository.deleteById(about.getContactId());
    }

    private void updateAboutFields(About savedAbout, About updatedAbout) {
        savedAbout.setDescription(updatedAbout.getDescription());
        savedAbout.setGoals(updatedAbout.getGoals());
        updateIdealClients(savedAbout.getIdealClients(), updatedAbout.getIdealClients());
        updatePastClients(savedAbout.getPastClients(), updatedAbout.getPastClients());
        updateFirstInteraction(savedAbout.getFirstInteraction(), updatedAbout.getFirstInteraction());
    }

    private void updateIdealClients(List<IdealClient> savedIcs, List<IdealClient> newAndUpdatedIcs) {
        var savedIcsMap = savedIcs.stream().collect(toMap(IdealClient::getIdealClientId, (ic) -> ic));
        var newIcs = newAndUpdatedIcs.stream().filter((ic) -> isNull(ic.getIdealClientId())).collect(toList());
        var updatedIcs = newAndUpdatedIcs.stream().filter((ic) -> nonNull(ic.getIdealClientId())).collect(toList());
        var updatedIcsMap = updatedIcs.stream().collect(toMap(IdealClient::getIdealClientId, (ic) -> ic));

        savedIcs.addAll(newIcs);
        updatedIcs.forEach(uic -> {
            var sic = savedIcsMap.get(uic.getIdealClientId());
            if (isNull(sic)) {
                savedIcs.add(uic);
            } else {
                updateIdealClient(sic, uic);
            }
        });

        savedIcsMap.forEach((sicId, sic) -> {
            var uic = updatedIcsMap.get(sicId);
            if (isNull(uic)) {
                savedIcs.remove(sic);
            }
        });
    }

    private void updateIdealClient(IdealClient savedIc, IdealClient updatedIc) {
        savedIc.setCharacteristics(updatedIc.getCharacteristics());
        savedIc.setNeeds(updatedIc.getNeeds());
        savedIc.setGoals(updatedIc.getGoals());
    }

    private void updatePastClients(List<PastClient> savedPcs, List<PastClient> newAndUpdatedPcs) {
        var savedPcsMap = savedPcs.stream().collect(toMap(PastClient::getPastClientId, (ic) -> ic));
        var newPcs = newAndUpdatedPcs.stream().filter((ic) -> isNull(ic.getPastClientId())).collect(toList());
        var updatedPcs = newAndUpdatedPcs.stream().filter((ic) -> nonNull(ic.getPastClientId())).collect(toList());
        var updatedPcsMap = updatedPcs.stream().collect(toMap(PastClient::getPastClientId, (ic) -> ic));

        savedPcs.addAll(newPcs);
        updatedPcs.forEach(upc -> {
            var spc = savedPcsMap.get(upc.getPastClientId());
            if (isNull(spc)) {
                savedPcs.add(upc);
            } else {
                updatePastClient(spc, upc);
            }
        });

        savedPcsMap.forEach((spcId, spc) -> {
            var upc = updatedPcsMap.get(spcId);
            if (isNull(upc)) {
                savedPcs.remove(spc);
            }
        });
    }

    private void updatePastClient(PastClient savedPc, PastClient updatedPc) {
        savedPc.setCharacteristics(updatedPc.getCharacteristics());
        savedPc.setProvidedServices(updatedPc.getProvidedServices());
        savedPc.setOutcomes(updatedPc.getOutcomes());
    }

    private void updateFirstInteraction(FirstInteraction savedFi, FirstInteraction updatedFi) {
        savedFi.setType(updatedFi.getType());
        savedFi.setSource(updatedFi.getSource());
        savedFi.setDate(updatedFi.getDate());
        savedFi.setNotes(updatedFi.getNotes());
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
