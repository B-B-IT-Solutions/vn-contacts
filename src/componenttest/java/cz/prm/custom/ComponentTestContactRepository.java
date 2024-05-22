package cz.prm.custom;

import cz.prm.domain.contact.Contact;
import cz.prm.repositories.contact.ContactRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestContactRepository extends ContactRepository {

    Contact getByEmail(String email);
}
