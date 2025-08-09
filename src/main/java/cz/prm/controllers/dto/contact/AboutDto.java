package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("idealClients")
    private List<IdealClientDto> idealClients;

    @JsonProperty("pastClients")
    private List<PastClientDto> pastClients;

    @JsonProperty("contactGoals")
    private String contactGoals;

    @JsonProperty("contactChallenges")
    private String contactChallenges;

    @JsonProperty("firstInteraction")
    private FirstInteractionDto firstInteraction;

    @JsonProperty("contactId")
    private Long contactId;
}
