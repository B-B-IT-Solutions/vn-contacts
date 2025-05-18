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

    @JsonProperty("contactGoals")
    private String contactGoals;

    @JsonProperty("contactChallenges")
    private String contactChallenges;

    @JsonProperty("myBenefits")
    private String myBenefits;

    @JsonProperty("firstMeeting")
    private MeetingDto firstMeeting;

    @JsonProperty("contactId")
    private Long contactId;
}
