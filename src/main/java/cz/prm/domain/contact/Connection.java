package cz.prm.domain.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    @Column(name = "VALUE")
    private String value;

    @Column(name = "TYPE")
    private String type;
}
