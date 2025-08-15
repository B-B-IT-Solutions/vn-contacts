package cz.prm.domain.networking;

import cz.prm.domain.contact.Contact;
import cz.prm.services.networking.data.scoring.ScoringCriteria;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferralSuggestion {

    private static final int RELEVANT_SCORE_THRESHOLD = 20;

    private Contact contact;

    private Integer score;

    private Map<ScoringCriteria, String> justifications;

    private Set<ScoringCriteria> checkedCriterias;

    public ReferralSuggestion() {
        this.score = 0;
        this.justifications = new HashMap<>();
        this.checkedCriterias = new HashSet<>();
    }

    public ReferralSuggestion(Contact contact) {
        this();
        this.contact = contact;
    }

    public void addCheckedCriteria(ScoringCriteria criteria) {
        this.checkedCriterias.add(criteria);
    }

    public void addJustification(ScoringCriteria criteria, String reason) {
        this.justifications.put(criteria, reason);
    }

    public void addScore(Integer count) {
        this.addScore(count, 1);
    }

    public void addScore(Integer count, Integer multiplier) {
        this.score += count * multiplier;
    }

    public boolean isRelevant() {
        return score > RELEVANT_SCORE_THRESHOLD && !justifications.isEmpty();
    }

    public List<String> getReasons() {
        return justifications.values().stream().toList();
    }
}
