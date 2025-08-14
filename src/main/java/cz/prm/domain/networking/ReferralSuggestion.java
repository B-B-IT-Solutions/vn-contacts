package cz.prm.domain.networking;

import cz.prm.domain.contact.Contact;
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
}
