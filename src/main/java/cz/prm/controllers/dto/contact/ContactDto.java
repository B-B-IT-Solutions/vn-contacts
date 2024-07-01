package cz.prm.controllers.dto.contact;

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

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("telephones")
    private List<ConnectionDto> telephones;

    @JsonProperty("emails")
    private List<ConnectionDto> emails;

    @JsonProperty("urls")
    private List<ConnectionDto> urls;

    @JsonProperty("professions")
    private String professions;

    @JsonProperty("industries")
    private String industries;

    @JsonProperty("labels")
    private List<String> labels;

    @JsonProperty("dateOfBirth")
    private Instant dateOfBirth;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;

    @JsonProperty("creationDate")
    private Instant creationDate;
}
