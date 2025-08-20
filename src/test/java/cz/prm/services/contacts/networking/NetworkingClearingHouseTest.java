package cz.prm.services.contacts.networking;

import static cz.prm.utils.CommonUtils.page;
import static cz.prm.utils.assertions.contacts.NetworkingAssertions.assertPage;
import static cz.prm.utils.assertions.contacts.NetworkingAssertions.assertReferralRequirement;
import static cz.prm.utils.data.contacts.ContactUtils.contact;
import static cz.prm.utils.data.contacts.ContactUtils.contacts;
import static cz.prm.utils.data.contacts.NetworkingUtils.referralSuggestions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.services.contacts.contact.data.ContactService;
import cz.prm.services.contacts.networking.data.ReferralSuggestionService;
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
    private ReferralSuggestionService scoringService;
    @Captor
    private ArgumentCaptor<ReferralRequirement> refRequirementCapt;

    private NetworkingClearingHouse clearingHouse;

    @BeforeEach
    void setUp() {
        clearingHouse = new NetworkingClearingHouse(contactService, scoringService);
    }

    @Test
    void getReferralSuggestions() {
        var contact = contact();
        var potentialReferrals = contacts();
        var referralSuggestions = referralSuggestions();
        var page = page(referralSuggestions);

        when(contactService.getContact(contact.getContactId())).thenReturn(contact);
        when(contactService.getPotentialReferrals(any(ReferralRequirement.class))).thenReturn(potentialReferrals);
        when(scoringService.getReferralSuggestions(any(ReferralRequirement.class), eq(potentialReferrals))).thenReturn(page);

        var result = clearingHouse.getReferralSuggestions(contact.getContactId());
        verify(contactService).getPotentialReferrals(refRequirementCapt.capture());
        var refRequirement = refRequirementCapt.getValue();
        assertReferralRequirement(refRequirement, contact);
        assertPage(result, page);
    }
}