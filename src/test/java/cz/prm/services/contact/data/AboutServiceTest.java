package cz.prm.services.contact.data;

import static cz.prm.utils.ContactUtils.about;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ContactAssertions.assertAbout;
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
import cz.prm.domain.contact.Meeting;
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
        var contactId = randomLong();
        aboutService.createAbout(contactId);
        verify(repository).save(aboutCapt.capture());
        var savedAbout = aboutCapt.getValue();
        assertThat(savedAbout.getContactId()).isEqualTo(contactId);
    }

    @Test
    void updateAbout() {
        var aboutIdDb = about();
        var updatedAbout = about();
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
        assertMeetingFieldsUpdated(updatedAbout.getFirstMeeting(), savedAbout.getFirstMeeting());
    }

    private void assertMeetingFieldsUpdated(Meeting updatedAbout, Meeting savedAbout) {
        assertThat(savedAbout.getOccurrenceDate()).isEqualTo(updatedAbout.getOccurrenceDate());
        assertThat(savedAbout.getLocation()).isEqualTo(updatedAbout.getLocation());
        assertThat(savedAbout.getComment()).isEqualTo(updatedAbout.getComment());
    }
}