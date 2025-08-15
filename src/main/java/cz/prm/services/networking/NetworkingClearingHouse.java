package cz.prm.services.networking;

import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import cz.prm.services.contact.data.ContactService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NetworkingClearingHouse {

    private ContactService contactService;
    private ReferralScoringService scoringService;

    @Autowired
    public NetworkingClearingHouse(ContactService contactService, ReferralScoringService scoringService) {
        this.contactService = contactService;
        this.scoringService = scoringService;
    }

    public List<ReferralSuggestion> getPotentialReferrals(Long contactId) {
        var contact = contactService.getContact(contactId);
        var rr = new ReferralRequirement(contact);
        var potentialReferrals = contactService.getPotentialReferrals(rr);
        return scoringService.scorePotentialReferrals(rr, potentialReferrals);
    }
}
