package cz.prm.services.contact.data;

import static java.lang.String.format;

import cz.prm.domain.contact.About;
import cz.prm.repositories.contact.AboutPredicates;
import cz.prm.repositories.contact.AboutRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
