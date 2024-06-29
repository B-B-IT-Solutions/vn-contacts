package cz.prm.repositories.settings;

import cz.prm.domain.settings.Settings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long>, PrmQuerydslPredicateExecutor<Settings> {

}
