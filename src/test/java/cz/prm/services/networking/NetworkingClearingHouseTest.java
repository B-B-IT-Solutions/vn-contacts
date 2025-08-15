package cz.prm.services.networking;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.NetworkingUtils.referralSuggestions;
import static cz.prm.utils.assertions.NetworkingAssertions.assertReferralRequirement;
import static cz.prm.utils.assertions.NetworkingAssertions.assertReferralSuggestions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.services.contact.data.ContactService;
import cz.prm.services.networking.data.ReferralSuggestionService;
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

        when(contactService.getContact(contact.getContactId())).thenReturn(contact);
        when(contactService.getPotentialReferrals(any(ReferralRequirement.class))).thenReturn(potentialReferrals);
        when(scoringService.getReferralSuggestions(any(ReferralRequirement.class), eq(potentialReferrals))).thenReturn(referralSuggestions);

        var result = clearingHouse.getReferralSuggestions(contact.getContactId());
        verify(contactService).getPotentialReferrals(refRequirementCapt.capture());
        var refRequirement = refRequirementCapt.getValue();
        assertReferralRequirement(refRequirement, contact);
        assertReferralSuggestions(result, referralSuggestions);
    }
}