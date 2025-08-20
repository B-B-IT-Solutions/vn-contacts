package cz.prm.services.contacts.networking;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.domain.contacts.networking.ReferralSuggestion;
import cz.prm.services.contacts.contact.data.ContactService;
import cz.prm.services.contacts.networking.data.ReferralSuggestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NetworkingClearingHouse {

    private ContactService contactService;
    private ReferralSuggestionService scoringService;

    @Autowired
    public NetworkingClearingHouse(ContactService contactService, ReferralSuggestionService scoringService) {
        this.contactService = contactService;
        this.scoringService = scoringService;
    }

    public Page<ReferralSuggestion> getReferralSuggestions(Long contactId) {
        var contact = contactService.getContact(contactId);
        var rr = new ReferralRequirement(contact);
        var potentialReferrals = contactService.getPotentialReferrals(rr);
        return scoringService.getReferralSuggestions(rr, potentialReferrals);
    }
}
