package cz.prm.services;

import static cz.prm.utils.SettingsUtils.settings;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.settings.Settings;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.SettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingsServiceTest {

    @Mock
    private SettingsRepository repository;
    @Mock
    private SettingsPredicates predicates;

    private SettingsService settingsService;

    @BeforeEach
    void setUp() {
        settingsService = new SettingsService(repository, predicates);
    }

    @Test
    void getSettings_SettingsExists() {
        var settings = settings();
        var predicate = new BooleanBuilder();
        when(predicates.settings()).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getSettings();
        assertSettings(result, settings);
    }

    @Test
    void getSettings_SettingsDoesntExist() {
        var predicate = new BooleanBuilder();
        when(predicates.settings()).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        when(repository.saveAndFlush(any(Settings.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getSettings();
        assertThat(result).isNotNull();
    }
}