package cz.prm.custom;

import cz.prm.domain.contacts.contact.Contact;
import cz.prm.repositories.contact.ContactRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestContactRepository extends ContactRepository {

    Contact getByFirstName(String firstName);

    Contact getByLastName(String lastName);
}
