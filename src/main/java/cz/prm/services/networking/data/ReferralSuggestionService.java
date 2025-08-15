package cz.prm.services.networking.data;

import static cz.prm.services.networking.data.scoring.ScoringCriteria.commonIndustries;
import static cz.prm.services.networking.data.scoring.ScoringCriteria.complementaryServices;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReferralSuggestionService {

    public List<ReferralSuggestion> getReferralSuggestions(ReferralRequirement rr, List<Contact> potentialReferrals) {
        var rss = potentialReferrals.stream().map(pr -> toReferralSuggestion(rr, pr)).filter(ReferralSuggestion::isRelevant).collect(toList());
        return rss.stream().sorted(comparingInt(ReferralSuggestion::getScore).reversed()).limit(7).collect(toList());
    }

    public ReferralSuggestion toReferralSuggestion(ReferralRequirement rr, Contact potentialReferral) {
        var rs = new ReferralSuggestion(potentialReferral);
        commonIndustries(rs, rr, potentialReferral);
        complementaryServices(rs, rr, potentialReferral);
        return rs;
    }
}
