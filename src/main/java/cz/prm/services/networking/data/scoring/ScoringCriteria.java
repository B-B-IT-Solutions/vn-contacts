package cz.prm.services.networking.data.scoring;

import static org.apache.commons.collections4.CollectionUtils.disjunction;
import static org.apache.commons.collections4.CollectionUtils.intersection;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;

public class ScoringCriteria {

    public static void commonIndustries(ReferralSuggestion rs, ReferralRequirement rr, Contact potentialReferral) {
        var commonIndustries = intersection(rr.getIndustries(), potentialReferral.getIndustries());
        if (!commonIndustries.isEmpty()) {
            rs.addReason("Both work in " + String.join(", ", commonIndustries) + " commonIndustries");
            rs.addScore(commonIndustries.size(), 20);
        }
    }

    public static void complementaryServices(ReferralSuggestion rs, ReferralRequirement rr, Contact potentialReferral) {
        var commonIndustries = intersection(rr.getIndustries(), potentialReferral.getIndustries());
        var complementaryServices = disjunction(rr.getProducts(), potentialReferral.getProducts());
        if (!commonIndustries.isEmpty() && !complementaryServices.isEmpty()) {
            rs.addReason("Offers complementary services you don't provide");
            rs.addScore(15);
        }
    }

    public static void targetMarketsProductsMatch(ReferralSuggestion rs, ReferralRequirement rr, Contact potentialReferral) {
        var targetMarketsProducts = intersection(rr.getTargetMarkets(), potentialReferral.getProducts());
        if (!targetMarketsProducts.isEmpty()) {
            rs.addReason("Their products align with your target clients");
            rs.addScore(25);
        }
    }

    public static void productsTargetMarketsMatch(ReferralSuggestion rs, ReferralRequirement rr, Contact potentialReferral) {
        var productsTargetMarkets = intersection(rr.getProducts(), potentialReferral.getTargetMarkets());
        if (!productsTargetMarkets.isEmpty()) {
            rs.addReason("Your services align with their target clients");
            rs.addScore(25);
        }
    }
}
