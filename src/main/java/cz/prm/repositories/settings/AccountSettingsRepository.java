package cz.prm.repositories.settings;

import cz.prm.domain.settings.AccountSettings;
import cz.prm.repositories.customisations.executors.PrmQuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSettingsRepository extends JpaRepository<AccountSettings, Long>, PrmQuerydslPredicateExecutor<AccountSettings> {

}
