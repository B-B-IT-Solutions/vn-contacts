package cz.prm.services.networking;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.disjunction;
import static org.apache.commons.collections4.CollectionUtils.intersection;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReferralScoringService {

    public List<ReferralSuggestion> scorePotentialReferrals(ReferralRequirement rr, List<Contact> potentialReferrals) {
        var suggestions = new ArrayList<ReferralSuggestion>();
        potentialReferrals.forEach(pr -> {
            var rs = new ReferralSuggestion(pr);

            var commonIndustries = intersection(rr.getIndustries(), pr.getIndustries());
            if (!commonIndustries.isEmpty()) {
                rs.addReason("Both work in " + String.join(", ", commonIndustries) + " commonIndustries");
                rs.addScore(commonIndustries.size(), 20);
            }

//            var targetMarketsProducts = intersection(rr.getTargetMarkets(), pr.getProducts());
//            if (!targetMarketsProducts.isEmpty()) {
//                rs.addReason("Their products align with your target clients");
//                rs.addScore(25);
//            }
//
//            var productsTargetMarkets = intersection(rr.getProducts(), pr.getTargetMarkets());
//            if (!productsTargetMarkets.isEmpty()) {
//                rs.addReason("Your services align with their target clients");
//                rs.addScore(25);
//            }

            // Complementary services
            var complementaryServices = disjunction(rr.getProducts(), pr.getProducts());
            if (!commonIndustries.isEmpty() && !complementaryServices.isEmpty()) {
                rs.addReason("Offers complementary services you don't provide");
                rs.addScore(15);
            }

            if (rs.isRelevant()) {
                suggestions.add(rs);
            }
        });
        return suggestions.stream().sorted(comparingInt(ReferralSuggestion::getScore).reversed()).limit(7).collect(toList());
    }
}
