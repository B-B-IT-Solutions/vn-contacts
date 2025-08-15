package cz.prm.services.networking.data.scoring;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.uuid;
import static cz.prm.utils.TestUtils.uuids;
import static org.apache.commons.collections4.CollectionUtils.intersection;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import org.junit.jupiter.api.Test;

class ReferralScoringTest {

    @Test
    void commonIndustries_NoIntersection() {
        var contact = contact();
        var potentialReferral = contact();
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);

        refScoring.commonIndustries(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getReasons()).isEmpty();
    }

    @Test
    void commonIndustries_OneIntersection() {
        var industry1 = uuid();
        var contact = contact();
        var potentialReferral = contact();
        contact.getIndustries().add(industry1);
        potentialReferral.getIndustries().add(industry1);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var refScoring = new ReferralScoring(rr);
        var reason = String.format("Both work in " + String.join(", ", industry1) + " commonIndustries");

        refScoring.commonIndustries(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(20);
        assertThat(rs.getReasons()).containsExactly(reason);
    }

    @Test
    void commonIndustries_TwoIntersection() {
        var industry1 = uuid();
        var industry2 = uuid();
        var contact = contact();
        var potentialReferral = contact();
        contact.getIndustries().add(industry1);
        contact.getIndustries().add(industry2);
        potentialReferral.getIndustries().add(industry1);
        potentialReferral.getIndustries().add(industry2);
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);
        var commonIndustries = intersection(rr.getIndustries(), potentialReferral.getIndustries());
        var refScoring = new ReferralScoring(rr);
        var reason = String.format("Both work in " + String.join(", ", commonIndustries) + " commonIndustries");

        refScoring.commonIndustries(rs, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(40);
        assertThat(rs.getReasons()).containsExactly(reason);
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
        assertThat(rs.getReasons()).isEmpty();
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
        assertThat(rs.getReasons()).isEmpty();
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
        assertThat(rs.getReasons()).containsExactly(reason);
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
        assertThat(rs.getReasons()).isEmpty();
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
        assertThat(rs.getReasons()).containsExactly(reason);
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
        assertThat(rs.getReasons()).isEmpty();
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
        assertThat(rs.getReasons()).containsExactly(reason);
    }
}