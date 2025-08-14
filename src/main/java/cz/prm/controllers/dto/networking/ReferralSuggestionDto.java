package cz.prm.controllers.dto.networking;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.prm.domain.contact.Contact;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralSuggestionDto {

    @JsonProperty("contact")
    private Contact contact;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("reasons")
    private List<String> reasons;
}
