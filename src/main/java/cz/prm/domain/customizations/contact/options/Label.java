package cz.prm.domain.customizations.contact.options;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Label {

    @Column(name = "VALUE")
    private String value;

    @Column(name = "COLOR")
    private String color;
}
