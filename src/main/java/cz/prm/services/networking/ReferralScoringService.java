package cz.prm.services.networking;

import static java.util.stream.Collectors.toList;
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

        for (Contact pr : potentialReferrals) {
            var rs = new ReferralSuggestion(pr);

            var industries = intersection(rr.getIndustries(), pr.getIndustries());
            var products = intersection(rr.getProducts(), pr.getProducts());
            var skills = intersection(rr.getSkills(), pr.getSkills());
            var targetMarkets = intersection(rr.getTargetMarkets(), pr.getTargetMarkets());

            if (!industries.isEmpty()) {
                rs.addReason("Both work in " + String.join(", ", industries) + " industries");
                rs.addScore(industries.size(), 20);
            }

            // Contact's Ideal clients match potential referral's products
            boolean clientServiceMatch = rr.getTargetMarkets().stream().anyMatch(rrTargetMarket -> pr.getProducts().stream().anyMatch(
                prProduct -> prProduct.toLowerCase().contains(rrTargetMarket.toLowerCase().split(" ")[0]) || rrTargetMarket.toLowerCase()
                    .contains(prProduct.toLowerCase().split(" ")[0])));
            if (clientServiceMatch) {
                rs.addReason("Their services align with your target clients");
                rs.addScore(25);
            }

            // Contact's contactIdeal clients match target's services
            boolean serviceClientMatch = pr.getTargetMarkets().stream().anyMatch(client -> rr.getProducts().stream().anyMatch(
                service -> service.toLowerCase().contains(client.toLowerCase().split(" ")[0]) || client.toLowerCase()
                    .contains(service.toLowerCase().split(" ")[0])));
            if (serviceClientMatch) {
                rs.addReason("Your services align with their target clients");
                rs.addScore(25);
            }

            // Complementary services
            List<String> complementaryServices = pr.getProducts().stream()
                .filter(service -> !rr.getProducts().contains(service) && rr.getIndustries().stream().anyMatch(pr.getIndustries()::contains))
                .collect(toList());
            if (!complementaryServices.isEmpty()) {
                rs.addReason("Offers complementary services you don't provcontactIde");
                rs.addScore(15);
            }

//            // Connection strength bonus
//            if ("Strong".equalsIgnoreCase(pr.connectionStrength)) {
//                matchScore += 10;
//                reasons.add("Strong existing relationship");
//            }
//
//            // Location proximity bonus
//            String targetRegion = targetContact.location.contains(",") ? targetContact.location.split(",")[1].trim() : "";
//            if (!targetRegion.isEmpty() && pr.location.contains(targetRegion)) {
//                reasons.add("Located in same region");
//                matchScore += 5;
//            }

            if (rs.isRelevant()) {
                suggestions.add(rs);
            }
        }

        return suggestions.stream().sorted((a, b) -> Integer.compare(b.getScore(), a.getScore())).limit(6).collect(toList());
    }
}
