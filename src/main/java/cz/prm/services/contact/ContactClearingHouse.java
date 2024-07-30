package cz.prm.services.contact;

import cz.prm.services.contact.data.AboutService;
import cz.prm.services.contact.data.ContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactClearingHouse {

    private ContactService contactService;

    private AboutService aboutService;

    @Autowired
    public ContactClearingHouse(ContactService contactService, AboutService aboutService) {
        this.contactService = contactService;
        this.aboutService = aboutService;
    }
}
