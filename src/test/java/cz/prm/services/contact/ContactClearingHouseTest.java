package cz.prm.services.contact;

import cz.prm.services.contact.data.AboutService;
import cz.prm.services.contact.data.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactClearingHouseTest {

    @Mock
    private ContactService contactService;
    @Mock
    private AboutService aboutService;

    private ContactClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new ContactClearingHouse(contactService, aboutService);
    }
}