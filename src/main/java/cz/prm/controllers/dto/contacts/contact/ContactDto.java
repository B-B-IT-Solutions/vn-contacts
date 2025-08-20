package cz.prm.controllers.dto.contacts.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    @JsonProperty("contactId")
    private Long contactId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("linkedInUrl")
    private String linkedInUrl;

    @JsonProperty("dateOfBirth")
    private Instant dateOfBirth;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("status")
    private String status;

    @JsonProperty("source")
    private String source;

    @JsonProperty("trustScore")
    private Integer trustScore;

    @JsonProperty("occupation")
    private OccupationDto occupation;

    @JsonProperty("labels")
    private List<String> labels;

    @JsonProperty("industries")
    private List<String> industries;

    @JsonProperty("skills")
    private List<String> skills;

    @JsonProperty("products")
    private List<String> products;

    @JsonProperty("targetMarkets")
    private List<String> targetMarkets;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
