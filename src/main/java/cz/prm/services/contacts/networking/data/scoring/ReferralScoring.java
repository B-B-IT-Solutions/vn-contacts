package cz.prm.services.contacts.networking.data.scoring;

import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.COMMON_INDUSTRIES;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.COMPLEMENTARY_SERVICES;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.PRODUCTS_TARGET_MARKETS_MATCH;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.TARGET_MARKETS_PRODUCTS_MATCH;
import static org.apache.commons.collections4.CollectionUtils.disjunction;
import static org.apache.commons.collections4.CollectionUtils.intersection;

import cz.prm.domain.contacts.contact.Contact;
import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.domain.contacts.networking.ReferralSuggestion;

public class ReferralScoring {

    private ReferralRequirement referralRequirement;

    public ReferralScoring(ReferralRequirement referralRequirement) {
        this.referralRequirement = referralRequirement;
    }

    public ReferralSuggestion toReferralSuggestion(Contact potentialReferral) {
        var rs = new ReferralSuggestion(potentialReferral);
        commonIndustries(rs, potentialReferral);
        complementaryServices(rs, potentialReferral);
        targetMarketsProductsMatch(rs, potentialReferral);
        productsTargetMarketsMatch(rs, potentialReferral);
        return rs;
    }

    public void commonIndustries(ReferralSuggestion rs, Contact potentialReferral) {
        var commonIndustries = intersection(referralRequirement.getIndustries(), potentialReferral.getIndustries());
        if (!commonIndustries.isEmpty()) {
            rs.addJustification(COMMON_INDUSTRIES, "Both work in " + String.join(", ", commonIndustries) + " industries");
            rs.addScore(commonIndustries.size(), 20);
        }
        rs.addCheckedCriteria(COMMON_INDUSTRIES);
    }

    public void complementaryServices(ReferralSuggestion rs, Contact potentialReferral) {
        var commonIndustries = intersection(referralRequirement.getIndustries(), potentialReferral.getIndustries());
        var complementaryServices = disjunction(referralRequirement.getProducts(), potentialReferral.getProducts());
        if (!commonIndustries.isEmpty() && !complementaryServices.isEmpty()) {
            rs.addJustification(COMPLEMENTARY_SERVICES, "Offers complementary services you don't provide");
            rs.addScore(15);
        }
        rs.addCheckedCriteria(COMPLEMENTARY_SERVICES);
    }

    public void targetMarketsProductsMatch(ReferralSuggestion rs, Contact potentialReferral) {
        var targetMarketsProducts = intersection(referralRequirement.getTargetMarkets(), potentialReferral.getProducts());
        if (!targetMarketsProducts.isEmpty()) {
            rs.addJustification(TARGET_MARKETS_PRODUCTS_MATCH, "Their products align with your target clients");
            rs.addScore(25);
        }
        rs.addCheckedCriteria(TARGET_MARKETS_PRODUCTS_MATCH);
    }

    public void productsTargetMarketsMatch(ReferralSuggestion rs, Contact potentialReferral) {
        var productsTargetMarkets = intersection(referralRequirement.getProducts(), potentialReferral.getTargetMarkets());
        if (!productsTargetMarkets.isEmpty()) {
            rs.addJustification(PRODUCTS_TARGET_MARKETS_MATCH, "Your services align with their target clients");
            rs.addScore(25);
        }
        rs.addCheckedCriteria(PRODUCTS_TARGET_MARKETS_MATCH);
    }
}
