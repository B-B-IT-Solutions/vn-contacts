package cz.prm.controllers.dto.contacts.networking;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.prm.controllers.dto.contacts.contact.ContactDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralSuggestionDto {

    @JsonProperty("contact")
    private ContactDto contact;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("reasons")
    private List<String> reasons;
}
