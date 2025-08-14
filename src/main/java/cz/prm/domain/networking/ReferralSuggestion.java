package cz.prm.domain.networking;

import cz.prm.domain.contact.Contact;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralSuggestion {

    private Contact contact;

    private Integer score;

    private List<String> reasons;

    public ReferralSuggestion(Contact contact) {
        this.contact = contact;
        this.score = 0;
        this.reasons = new ArrayList<>();
    }

    public void addReason(String reason) {
        this.reasons.add(reason);
    }

    public void addScore(Integer count) {
        this.addScore(count, 1);
    }

    public void addScore(Integer count, Integer multiplier) {
        this.score += count * multiplier;
    }

    public boolean isRelevant() {
        return score > 20 && !reasons.isEmpty();
    }
}
