package cz.prm.controllers.dto.contacts.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecoratedContactDto {

    @JsonProperty("contact")
    private ContactDto contact;

    @JsonProperty("about")
    private AboutDto about;
}
