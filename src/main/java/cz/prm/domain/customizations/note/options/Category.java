package cz.prm.domain.customizations.note.options;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Column(name = "VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;
}
