package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PastClientDto {

    @JsonProperty("pastClientId")
    private Long pastClientId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("characteristics")
    private List<String> characteristics;

    @JsonProperty("providedServices")
    private String providedServices;

    @JsonProperty("outcomes")
    private String outcomes;

    @JsonProperty("order")
    private Short order;
}
