package cz.prm.services.contact.data;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ContactUtils.about;
import static cz.prm.utils.ContactUtils.idealClient;
import static cz.prm.utils.ContactUtils.pastClient;
import static cz.prm.utils.MockitoUtils.returnParamAnswer;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.TestUtils.uuids;
import static cz.prm.utils.assertions.ContactAssertions.assertAbout;
import static cz.prm.utils.assertions.ContactAssertions.assertFirstInteraction;
import static cz.prm.utils.assertions.ContactAssertions.assertIdealClients;
import static cz.prm.utils.assertions.ContactAssertions.assertPastClients;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.contact.About;
import cz.prm.repositories.contact.AboutPredicates;
import cz.prm.repositories.contact.AboutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AboutServiceTest {

    @Mock
    private AboutRepository repository;
    @Mock
    private AboutPredicates predicates;
    @Captor
    private ArgumentCaptor<About> aboutCapt;

    private AboutService aboutService;

    @BeforeEach
    void setUp() {
        aboutService = new AboutService(repository, predicates);
    }

    @Test
    void getContact() {
        var about = about();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(about.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(about));
        var result = aboutService.getAbout(about.getContactId());
        assertAbout(result, about);
    }

    @Test
    void getContact_EntityNotFound() {
        var about = about();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(about.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> aboutService.getAbout(about.getContactId()));
    }

    @Test
    void createAbout() {
        var about = about();
        var contactId = randomLong();
        assertThat(about.getContactId()).isNotEqualTo(contactId);
        when(repository.save(about)).thenAnswer(returnParamAnswer(0));

        var response = aboutService.createAbout(contactId, about);
        verify(repository).save(aboutCapt.capture());
        var savedAbout = aboutCapt.getValue();
        assertThat(savedAbout.getContactId()).isEqualTo(contactId);
        assertThat(response.getContactId()).isEqualTo(contactId);
    }

    @Test
    void updateAbout() {
        var aboutIdDb = about();
        var updatedAbout = about();

        var updatedIc = aboutIdDb.getIdealClients().get(0);
        updatedIc.setCharacteristics(uuids());
        updatedIc.setNeeds(uuid());
        updatedIc.setGoals(uuid());
        var updatedIcs = newArrayList(updatedIc, idealClient(null), idealClient(null), idealClient(null));
        updatedAbout.getIdealClients().addAll(updatedIcs);

        var updatedPc = aboutIdDb.getPastClients().get(0);
        updatedPc.setCharacteristics(uuids());
        updatedPc.setProvidedServices(uuid());
        updatedPc.setOutcomes(uuid());
        var updatedPcs = newArrayList(updatedPc, pastClient(null), pastClient(null), pastClient(null));
        updatedAbout.getPastClients().addAll(updatedPcs);

        var predicate = new BooleanBuilder();
        when(predicates.byContactId(aboutIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(aboutIdDb));

        aboutService.updateAbout(aboutIdDb.getContactId(), updatedAbout);
        verify(repository).save(aboutCapt.capture());
        var savedAbout = aboutCapt.getValue();
        assertFieldsUpdated(aboutIdDb, updatedAbout, savedAbout);
    }

    @Test
    void updateAbout_EntityNotFound() {
        var aboutIdDb = about();
        var updatedAbout = about();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(aboutIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> aboutService.updateAbout(aboutIdDb.getContactId(), updatedAbout));
    }

    @Test
    void deleteAbout() {
        var aboutIdDb = about();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(aboutIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(aboutIdDb));
        aboutService.deleteAbout(aboutIdDb.getContactId());
        verify(repository).deleteById(aboutIdDb.getContactId());
    }

    @Test
    void deleteAbout_EntityNotFound() {
        var aboutIdDb = about();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(aboutIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> aboutService.deleteAbout(aboutIdDb.getContactId()));
        verify(repository, never()).deleteById(any());
    }

    private void assertFieldsUpdated(About aboutIdDb, About updatedAbout, About savedAbout) {
        assertThat(aboutIdDb.getContactId()).isEqualTo(savedAbout.getContactId());
        assertThat(aboutIdDb.getOwner()).isEqualTo(savedAbout.getOwner());
        assertThat(savedAbout.getDescription()).isEqualTo(updatedAbout.getDescription());
        assertThat(savedAbout.getContactGoals()).isEqualTo(updatedAbout.getContactGoals());
        assertThat(savedAbout.getContactChallenges()).isEqualTo(updatedAbout.getContactChallenges());
        assertIdealClients(updatedAbout.getIdealClients(), savedAbout.getIdealClients());
        assertPastClients(updatedAbout.getPastClients(), savedAbout.getPastClients());
        assertFirstInteraction(updatedAbout.getFirstInteraction(), savedAbout.getFirstInteraction());
    }
}