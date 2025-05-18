package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdealClientDto {

    @JsonProperty("idealClientId")
    private Long idealClientId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("characteristics")
    private List<String> characteristics;

    @JsonProperty("needs")
    private String needs;

    @JsonProperty("goals")
    private String goals;

    @JsonProperty("order")
    private Short order;
}
