package cz.prm.domain.settings;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Industry {

    @Column(name = "NAME")
    private String name;

    @Column(name = "SYSTEM_DEFINED")
    private boolean systemDefined;
}
