package cz.prm.services.networking.data.scoring;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.uuid;
import static org.apache.commons.collections4.CollectionUtils.intersection;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import org.junit.jupiter.api.Test;

class ScoringCriteriaTest {

    @Test
    void commonIndustries_NoIntersection() {
        var contact = contact();
        var potentialReferral = contact();
        var rs = new ReferralSuggestion(potentialReferral);
        var rr = new ReferralRequirement(contact);

        ScoringCriteria.commonIndustries(rs, rr, potentialReferral);
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
        var reason = String.format("Both work in " + String.join(", ", industry1) + " commonIndustries");

        ScoringCriteria.commonIndustries(rs, rr, potentialReferral);
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
        var reason = String.format("Both work in " + String.join(", ", commonIndustries) + " commonIndustries");

        ScoringCriteria.commonIndustries(rs, rr, potentialReferral);
        assertThat(rs.getScore()).isEqualTo(40);
        assertThat(rs.getReasons()).containsExactly(reason);
    }
}