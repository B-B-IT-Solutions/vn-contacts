package cz.prm.controllers.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {

    @JsonProperty("occurrenceDate")
    private Instant occurrenceDate;

    @JsonProperty("location")
    private String location;

    @JsonProperty("comment")
    private String comment;
}
