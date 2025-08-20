package cz.prm.services.contacts.networking.data.scoring;

import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.COMMON_INDUSTRIES;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.COMPLEMENTARY_SERVICES;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.PRODUCTS_TARGET_MARKETS_MATCH;
import static cz.prm.services.contacts.networking.data.scoring.ScoringCriteria.TARGET_MARKETS_PRODUCTS_MATCH;
import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.TestUtils.uuids;
import static cz.prm.utils.data.contacts.ContactUtils.contact;
import static cz.prm.utils.data.contacts.NetworkingUtils.contactPotentialReferral;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.networking.ReferralRequirement;
import cz.prm.domain.contacts.networking.ReferralSuggestion;
import org.junit.jupiter.api.Test;

class ReferralScoringTest {

    @Test
    void toReferralSuggestion() {
        var contact = contact();
        var potentialReferral = contactPotentialReferral(contact);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        var result = refScoring.toReferralSuggestion(potentialReferral);
        assertThat(result).isNotNull();
        assertThat(result.getScore()).isEqualTo(55);
        assertThat(result.getCheckedCriterias()).containsExactlyInAnyOrder(COMMON_INDUSTRIES, COMPLEMENTARY_SERVICES, TARGET_MARKETS_PRODUCTS_MATCH,
            PRODUCTS_TARGET_MARKETS_MATCH);
    }

    @Test
    void toReferralSuggestion_Score_Zero() {
        var contact = contact();
        var potentialReferral = contact();
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        var result = refScoring.toReferralSuggestion(potentialReferral);
        assertThat(result).isNotNull();
        assertThat(result.getScore()).isEqualTo(0);
        assertThat(result.getCheckedCriterias()).containsExactlyInAnyOrder(COMMON_INDUSTRIES, COMPLEMENTARY_SERVICES, TARGET_MARKETS_PRODUCTS_MATCH,
            PRODUCTS_TARGET_MARKETS_MATCH);
    }

    @Test
    void commonIndustries() {
        var industry1 = uuid();
        var contact = contact();
        var potentialReferral = contact();
        contact.getIndustries().add(industry1);
        potentialReferral.getIndustries().add(industry1);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);
        var reason = String.format("Both work in " + String.join(", ", industry1) + " industries");

        refScoring.commonIndustries(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(20);
        assertThat(rs.getJustifications()).containsEntry(COMMON_INDUSTRIES, reason);
        assertThat(rs.getCheckedCriterias()).containsExactly(COMMON_INDUSTRIES);
    }

    @Test
    void commonIndustries_NoIntersection() {
        var contact = contact();
        var potentialReferral = contact();
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        refScoring.commonIndustries(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).containsExactly(COMMON_INDUSTRIES);
    }

    @Test
    void complementaryServices() {
        var industry1 = uuid();
        var product1 = uuid();
        var contact = contact();
        var potentialReferral = contact();
        contact.getIndustries().add(industry1);
        contact.getProducts().add(product1);
        potentialReferral.getIndustries().add(industry1);
        potentialReferral.getProducts().add(product1);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);
        var reason = "Offers complementary services you don't provide";

        refScoring.complementaryServices(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(15);
        assertThat(rs.getJustifications()).containsEntry(COMPLEMENTARY_SERVICES, reason);
        assertThat(rs.getCheckedCriterias()).containsExactly(COMPLEMENTARY_SERVICES);
    }

    @Test
    void complementaryServices_NoIndustriesIntersection() {
        var contact = contact();
        var potentialReferral = contact();
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        refScoring.complementaryServices(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).containsExactly(COMPLEMENTARY_SERVICES);
    }

    @Test
    void complementaryServices_NoProductsDisjunction() {
        var industry1 = uuid();
        var products = uuids();
        var contact = contact();
        var potentialReferral = contact();
        contact.getIndustries().add(industry1);
        contact.setProducts(products);
        potentialReferral.getIndustries().add(industry1);
        potentialReferral.setProducts(products);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        refScoring.complementaryServices(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).containsExactly(COMPLEMENTARY_SERVICES);
    }

    @Test
    void targetMarketsProductsMatch() {
        var product1 = uuid();
        var contact = contact();
        var potentialReferral = contact();
        contact.getTargetMarkets().add(product1);
        potentialReferral.getProducts().add(product1);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);
        var reason = "Their products align with your target clients";

        refScoring.targetMarketsProductsMatch(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(25);
        assertThat(rs.getJustifications()).containsEntry(TARGET_MARKETS_PRODUCTS_MATCH, reason);
        assertThat(rs.getCheckedCriterias()).containsExactly(TARGET_MARKETS_PRODUCTS_MATCH);
    }

    @Test
    void targetMarketsProductsMatch_NoIntersection() {
        var contact = contact();
        var potentialReferral = contact();
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        refScoring.targetMarketsProductsMatch(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).containsExactly(TARGET_MARKETS_PRODUCTS_MATCH);
    }

    @Test
    void productsTargetMarketsMatch() {
        var product1 = uuid();
        var contact = contact();
        var potentialReferral = contact();
        contact.getProducts().add(product1);
        potentialReferral.getTargetMarkets().add(product1);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);
        var reason = "Your services align with their target clients";

        refScoring.productsTargetMarketsMatch(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(25);
        assertThat(rs.getJustifications()).containsEntry(PRODUCTS_TARGET_MARKETS_MATCH, reason);
        assertThat(rs.getCheckedCriterias()).containsExactly(PRODUCTS_TARGET_MARKETS_MATCH);
    }

    @Test
    void productsTargetMarketsMatch_NoIntersection() {
        var contact = contact();
        var potentialReferral = contact();
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        refScoring.productsTargetMarketsMatch(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).containsExactly(PRODUCTS_TARGET_MARKETS_MATCH);
    }
}