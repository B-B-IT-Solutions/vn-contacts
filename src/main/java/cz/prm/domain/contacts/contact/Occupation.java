package cz.prm.domain.contacts.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Occupation {

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "INDUSTRY")
    private String industry;
}
