package cz.prm.repositories.settings;

import cz.prm.domain.settings.GeneralSettings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralSettingsRepository extends JpaRepository<GeneralSettings, Long>, PrmQuerydslPredicateExecutor<GeneralSettings> {

}
