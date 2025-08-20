package cz.prm.domain.networking;

import static cz.prm.services.networking.data.scoring.ScoringCriteria.COMMON_INDUSTRIES;
import static cz.prm.services.networking.data.scoring.ScoringCriteria.COMPLEMENTARY_SERVICES;
import static cz.prm.services.networking.data.scoring.ScoringCriteria.TARGET_MARKETS_PRODUCTS_MATCH;
import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.networking.ReferralSuggestion;
import org.junit.jupiter.api.Test;

class ReferralSuggestionTest {

    @Test
    void newInstance_NoArgConstructor() {
        var rs = new ReferralSuggestion();
        assertThat(rs.getContact()).isNull();
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).isEmpty();
    }

    @Test
    void newInstance() {
        var contact = contact();
        var rs = new ReferralSuggestion(contact);
        assertThat(rs.getContact()).isEqualTo(contact);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getJustifications()).isEmpty();
        assertThat(rs.getCheckedCriterias()).isEmpty();
    }

    @Test
    void addCheckedCriteria() {
        var rs = new ReferralSuggestion(contact());
        rs.addCheckedCriteria(COMMON_INDUSTRIES);
        assertThat(rs.getCheckedCriterias()).hasSize(1).containsExactly(COMMON_INDUSTRIES);

        rs.addCheckedCriteria(COMPLEMENTARY_SERVICES);
        assertThat(rs.getCheckedCriterias()).hasSize(2).containsExactlyInAnyOrder(COMMON_INDUSTRIES, COMPLEMENTARY_SERVICES);

        rs.addCheckedCriteria(TARGET_MARKETS_PRODUCTS_MATCH);
        assertThat(rs.getCheckedCriterias()).hasSize(3)
            .containsExactlyInAnyOrder(COMMON_INDUSTRIES, COMPLEMENTARY_SERVICES, TARGET_MARKETS_PRODUCTS_MATCH);
    }

    @Test
    void addJustification() {
        var rs = new ReferralSuggestion(contact());
        var reason1 = uuid();
        rs.addJustification(COMMON_INDUSTRIES, reason1);
        assertThat(rs.getJustifications()).hasSize(1).containsEntry(COMMON_INDUSTRIES, reason1);

        var reason2 = uuid();
        rs.addJustification(COMPLEMENTARY_SERVICES, reason2);
        assertThat(rs.getJustifications()).hasSize(2).containsEntry(COMPLEMENTARY_SERVICES, reason2);

        var reason3 = uuid();
        rs.addJustification(TARGET_MARKETS_PRODUCTS_MATCH, reason3);
        assertThat(rs.getJustifications()).hasSize(3).containsEntry(TARGET_MARKETS_PRODUCTS_MATCH, reason3);
    }

    @Test
    void addScore() {
        var scoreSum = 0;
        var rs = new ReferralSuggestion(contact());
        var score1 = randomInt();
        rs.addScore(score1);
        scoreSum += score1;
        assertThat(rs.getScore()).isEqualTo(scoreSum);

        var score2 = randomInt();
        rs.addScore(score2);
        scoreSum += score2;
        assertThat(rs.getScore()).isEqualTo(scoreSum);

        var score3 = randomInt();
        rs.addScore(score3);
        scoreSum += score3;
        assertThat(rs.getScore()).isEqualTo(scoreSum);
    }

    @Test
    void addScoreWithMultiplier() {
        var scoreSum = 0;
        var rs = new ReferralSuggestion(contact());
        var score1 = randomInt();
        rs.addScore(score1, 25);
        scoreSum += (score1 * 25);
        assertThat(rs.getScore()).isEqualTo(scoreSum);

        var score2 = randomInt();
        rs.addScore(score2, 15);
        scoreSum += (score2 * 15);
        assertThat(rs.getScore()).isEqualTo(scoreSum);

        var score3 = randomInt();
        rs.addScore(score3, 10);
        scoreSum += (score3 * 10);
        assertThat(rs.getScore()).isEqualTo(scoreSum);
    }

    @Test
    void isRelevant() {
        var rs = new ReferralSuggestion(contact());
        rs.addScore(10);
        assertThat(rs.isRelevant()).isFalse();
        rs.addScore(10);
        assertThat(rs.isRelevant()).isFalse();
        rs.addScore(5);
        assertThat(rs.isRelevant()).isFalse();
        rs.addJustification(COMMON_INDUSTRIES, uuid());
        assertThat(rs.isRelevant()).isTrue();
    }

    @Test
    void getReasons() {
        var rs = new ReferralSuggestion(contact());
        var reason1 = uuid();
        rs.addJustification(COMMON_INDUSTRIES, reason1);
        assertThat(rs.getReasons()).hasSize(1).containsExactly(reason1);

        var reason2 = uuid();
        rs.addJustification(COMPLEMENTARY_SERVICES, reason2);
        assertThat(rs.getReasons()).hasSize(2).containsExactlyInAnyOrder(reason1, reason2);

        var reason3 = uuid();
        rs.addJustification(TARGET_MARKETS_PRODUCTS_MATCH, reason3);
        assertThat(rs.getReasons()).hasSize(3).containsExactlyInAnyOrder(reason1, reason2, reason3);
    }
}