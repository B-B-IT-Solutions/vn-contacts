package cz.prm.domain.settings;

import static jakarta.persistence.FetchType.EAGER;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GENERAL_SETTINGS", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralSettings {

    @Id
    @Column(name = "SETTINGS_ID")
    private Long settingsId;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "GENERAL_SETTINGS_INDUSTRY", joinColumns = @JoinColumn(name = "SETTINGS_ID"))
    @Column(name = "INDUSTRY")
    private List<String> industries;
}
