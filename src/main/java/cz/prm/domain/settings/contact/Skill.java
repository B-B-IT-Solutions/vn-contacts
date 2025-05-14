package cz.prm.domain.settings.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Column(name = "VALUE")
    private String value;

    @Column(name = "COLOR")
    private String color;

    public Skill(String value) {
        this.value = value;
    }
}
