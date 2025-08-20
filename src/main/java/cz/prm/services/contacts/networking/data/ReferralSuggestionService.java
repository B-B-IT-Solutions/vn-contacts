package cz.prm.services.contacts.networking.data;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.domain.contacts.networking.ReferralSuggestion;
import cz.prm.services.contacts.networking.data.scoring.ReferralScoring;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReferralSuggestionService {

    private static final int SUGGESTIONS_COUNT = 7;

    public Page<ReferralSuggestion> getReferralSuggestions(ReferralRequirement rr, List<Contact> potentialReferrals) {
        var scoring = new ReferralScoring(rr);
        var rss = potentialReferrals.stream().map(scoring::toReferralSuggestion).filter(ReferralSuggestion::isRelevant).collect(toList());
        var list = rss.stream().sorted(comparingInt(ReferralSuggestion::getScore).reversed()).limit(SUGGESTIONS_COUNT).collect(toList());
        return new Page<>(list);
    }
}
