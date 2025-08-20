package cz.prm.custom;

import cz.prm.domain.contacts.contact.About;
import cz.prm.repositories.contacts.contact.AboutRepository;
import org.springframework.context.annotation.Primary;

@Primary
public interface ComponentTestAboutRepository extends AboutRepository {

    About getByContactId(Long contactId);
}
