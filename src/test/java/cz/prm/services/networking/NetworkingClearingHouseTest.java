package cz.prm.services.networking;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.assertions.ContactAssertions.assertContacts;
import static cz.prm.utils.assertions.NetworkingAssertions.assertReferralRequirement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.services.contact.data.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NetworkingClearingHouseTest {

    @Mock
    private ContactService contactService;
    @Mock
    private ReferralScoringService scoringService;
    @Captor
    private ArgumentCaptor<ReferralRequirement> refRequirementCapt;

    private NetworkingClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new NetworkingClearingHouse(contactService, scoringService);
    }

    @Test
    void getPotentialReferrals() {
        var contact = contact();
        var potentialReferrals = contacts();

        when(contactService.getContact(contact.getContactId())).thenReturn(contact);
        when(contactService.getPotentialReferrals(any(ReferralRequirement.class))).thenReturn(potentialReferrals);

        var result = clearingHouse.getPotentialReferrals(contact.getContactId());
        verify(contactService).getPotentialReferrals(refRequirementCapt.capture());
        var refRequirement = refRequirementCapt.getValue();
        assertReferralRequirement(refRequirement, contact);
        assertContacts(result, potentialReferrals);
    }
}