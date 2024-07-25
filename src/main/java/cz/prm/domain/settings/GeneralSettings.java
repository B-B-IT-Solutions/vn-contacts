package cz.prm.domain.settings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOUNT_SETTINGS", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralSettings {

    @Id
    @Column(name = "SETTINGS_ID")
    private Long settingsId;

    @Column(name = "APP_LANGUAGE")
    private String appLanguage;
}
