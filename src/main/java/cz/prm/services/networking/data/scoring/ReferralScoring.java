package cz.prm.services.networking.data.scoring;

import static org.apache.commons.collections4.CollectionUtils.disjunction;
import static org.apache.commons.collections4.CollectionUtils.intersection;

import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;

public class ReferralScoring {

    private ReferralRequirement referralRequirement;

    public ReferralScoring(ReferralRequirement referralRequirement) {
        this.referralRequirement = referralRequirement;
    }

    public ReferralSuggestion toReferralSuggestion(Contact potentialReferral) {
        var rs = new ReferralSuggestion(potentialReferral);
        commonIndustries(rs, potentialReferral);
        complementaryServices(rs, potentialReferral);
        return rs;
    }

    public void commonIndustries(ReferralSuggestion rs, Contact potentialReferral) {
        var commonIndustries = intersection(referralRequirement.getIndustries(), potentialReferral.getIndustries());
        if (!commonIndustries.isEmpty()) {
            rs.addReason("Both work in " + String.join(", ", commonIndustries) + " commonIndustries");
            rs.addScore(commonIndustries.size(), 20);
        }
    }

    public void complementaryServices(ReferralSuggestion rs, Contact potentialReferral) {
        var commonIndustries = intersection(referralRequirement.getIndustries(), potentialReferral.getIndustries());
        var complementaryServices = disjunction(referralRequirement.getProducts(), potentialReferral.getProducts());
        if (!commonIndustries.isEmpty() && !complementaryServices.isEmpty()) {
            rs.addReason("Offers complementary services you don't provide");
            rs.addScore(15);
        }
    }

    public void targetMarketsProductsMatch(ReferralSuggestion rs, Contact potentialReferral) {
        var targetMarketsProducts = intersection(referralRequirement.getTargetMarkets(), potentialReferral.getProducts());
        if (!targetMarketsProducts.isEmpty()) {
            rs.addReason("Their products align with your target clients");
            rs.addScore(25);
        }
    }

    public void productsTargetMarketsMatch(ReferralSuggestion rs, Contact potentialReferral) {
        var productsTargetMarkets = intersection(referralRequirement.getProducts(), potentialReferral.getTargetMarkets());
        if (!productsTargetMarkets.isEmpty()) {
            rs.addReason("Your services align with their target clients");
            rs.addScore(25);
        }
    }
}
