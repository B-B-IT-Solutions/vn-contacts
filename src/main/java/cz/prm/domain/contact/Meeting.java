package cz.prm.domain.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    @Column(name = "OCCURRENCE_DATE")
    private Instant occurrenceDate;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "COMMENT")
    private String comment;
}
