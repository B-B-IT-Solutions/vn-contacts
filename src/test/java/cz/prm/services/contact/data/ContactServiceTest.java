package cz.prm.services.contact.data;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.ContactUtils.contactsQuery;
import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static cz.prm.utils.assertions.ContactAssertions.assertPage;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.contact.Contact;
import cz.prm.repositories.contact.AboutRepository;
import cz.prm.repositories.contact.ContactPredicates;
import cz.prm.repositories.contact.ContactRepository;
import cz.prm.repositories.note.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository repository;
    @Mock
    private ContactPredicates predicates;
    @Mock
    private AboutRepository aboutRepository;
    @Mock
    private NoteRepository noteRepository;
    @Captor
    private ArgumentCaptor<Contact> contactCapt;

    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(repository, predicates, aboutRepository, noteRepository);
    }

    @Test
    void getContacts() {
        var contacts = contacts();
        var page = new PageImpl(contacts);
        var query = contactsQuery();
        var predicate = new BooleanBuilder();

        when(predicates.contacts(query.getFilter())).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = contactService.getContacts(query);
        assertPage(result, page);
    }

    @Test
    void getContact() {
        var contact = contact();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(contact.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(contact));
        var result = contactService.getContact(contact.getContactId());
        assertContact(result, contact);
    }

    @Test
    void getContact_EntityNotFound() {
        var contact = contact();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(contact.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> contactService.getContact(contact.getContactId()));
    }

    @Test
    void createContact() {
        var contact = contact();
        contactService.createContact(contact);
        verify(repository).save(contact);
    }

    @Test
    void updateContact() {
        var contactIdDb = contact();
        var updatedContact = contact();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(contactIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(contactIdDb));

        contactService.updateContact(contactIdDb.getContactId(), updatedContact);
        verify(repository).save(contactCapt.capture());
        var savedContact = contactCapt.getValue();
        assertFieldsUpdated(contactIdDb, updatedContact, savedContact);
    }

    @Test
    void updateContact_EntityNotFound() {
        var contactIdDb = contact();
        var updatedContact = contact();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(contactIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> contactService.updateContact(contactIdDb.getContactId(), updatedContact));
    }

    @Test
    void deleteContact() {
        var contactIdDb = contact();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(contactIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(contactIdDb));

        contactService.deleteContact(contactIdDb.getContactId());
        verify(aboutRepository).deleteByContactId(contactIdDb.getContactId());
        verify(noteRepository).deleteByContactId(contactIdDb.getContactId());
        verify(repository).deleteById(contactIdDb.getContactId());
    }

    @Test
    void deleteContact_EntityNotFound() {
        var contactIdDb = contact();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(contactIdDb.getContactId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> contactService.deleteContact(contactIdDb.getContactId()));
        verify(aboutRepository, never()).deleteByContactId(any());
        verify(noteRepository, never()).deleteByContactId(any());
        verify(repository, never()).deleteById(any());
    }

    private void assertFieldsUpdated(Contact contactIdDb, Contact updatedContact, Contact savedContact) {
        assertThat(contactIdDb.getContactId()).isEqualTo(savedContact.getContactId());
        assertThat(contactIdDb.getOwner()).isEqualTo(savedContact.getOwner());
        assertThat(savedContact.getFirstName()).isEqualTo(updatedContact.getFirstName());
        assertThat(savedContact.getLastName()).isEqualTo(updatedContact.getLastName());
        assertThat(savedContact.getMiddleName()).isEqualTo(updatedContact.getMiddleName());
        assertThat(savedContact.getNickName()).isEqualTo(updatedContact.getNickName());
        assertThat(savedContact.getTelephones()).isEqualTo(updatedContact.getTelephones());
        assertThat(savedContact.getEmails()).isEqualTo(updatedContact.getEmails());
        assertThat(savedContact.getUrls()).isEqualTo(updatedContact.getUrls());
        assertThat(savedContact.getDateOfBirth()).isEqualTo(updatedContact.getDateOfBirth());
        assertThat(savedContact.getOccupation()).isEqualTo(updatedContact.getOccupation());
        assertThat(savedContact.getLabels()).isEqualTo(updatedContact.getLabels());
    }
}