package cz.prm.controllers.dto.contacts.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupationDto {

    @JsonProperty("jobTitle")
    private String jobTitle;

    @JsonProperty("company")
    private String company;

    @JsonProperty("industry")
    private String industry;
}
