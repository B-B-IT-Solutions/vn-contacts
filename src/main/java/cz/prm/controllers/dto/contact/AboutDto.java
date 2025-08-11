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

    @JsonProperty("contactId")
    private Long contactId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("goals")
    private String goals;

    @JsonProperty("idealClients")
    private List<IdealClientDto> idealClients;

    @JsonProperty("pastClients")
    private List<PastClientDto> pastClients;

    @JsonProperty("firstInteraction")
    private FirstInteractionDto firstInteraction;
}
