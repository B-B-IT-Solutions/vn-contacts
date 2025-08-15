package cz.prm.domain.networking;

import static cz.prm.utils.ContactUtils.contact;
import static cz.prm.utils.TestUtils.randomInt;
import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReferralSuggestionTest {

    @Test
    void newInstance() {
        var contact = contact();
        var rs = new ReferralSuggestion(contact);
        assertThat(rs.getContact()).isEqualTo(contact);
        assertThat(rs.getScore()).isEqualTo(0);
        assertThat(rs.getReasons()).isEmpty();
    }

    @Test
    void addReason() {
        var rs = new ReferralSuggestion(contact());
        var reason1 = uuid();
        rs.addReason(reason1);
        assertThat(rs.getReasons()).hasSize(1).contains(reason1);

        var reason2 = uuid();
        rs.addReason(reason2);
        assertThat(rs.getReasons()).hasSize(2).contains(reason1, reason2);

        var reason3 = uuid();
        rs.addReason(reason3);
        assertThat(rs.getReasons()).hasSize(3).contains(reason1, reason2, reason3);
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
        rs.addReason(uuid());
        assertThat(rs.isRelevant()).isTrue();
    }
}