package cz.prm.services.networking.data;

import static cz.prm.utils.ContactUtils.contacts;
import static cz.prm.utils.NetworkingUtils.referralRequirement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReferralSuggestionServiceTest {

    private ReferralSuggestionService scoringService;

    @BeforeEach
    void setUp() {
        scoringService = new ReferralSuggestionService();
    }

    @Test
    void scorePotentialReferrals() {
        var rr = referralRequirement();
        var potentialReferrals = contacts();

        var result = scoringService.getReferralSuggestions(rr, potentialReferrals);
//        assertContacts(result, potentialReferrals);
    }
}