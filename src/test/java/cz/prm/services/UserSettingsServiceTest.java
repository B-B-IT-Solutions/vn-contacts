package cz.prm.services;

import static cz.prm.utils.SettingsUtils.settings;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.settings.UserSettings;
import cz.prm.repositories.settings.GeneralSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.UserSettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserSettingsServiceTest {

    @Mock
    private GeneralSettingsRepository generalSettingsRepository;
    @Mock
    private UserSettingsRepository userSettingsRepository;
    @Mock
    private SettingsPredicates predicates;

    private SettingsService settingsService;

    @BeforeEach
    void setUp() {
        settingsService = new SettingsService(generalSettingsRepository, userSettingsRepository, predicates);
    }

    @Test
    void getUserSettings_SettingsExists() {
        var settings = settings();
        var predicate = new BooleanBuilder();
        when(predicates.settings()).thenReturn(predicate);
        when(userSettingsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getUserSettings();
        assertSettings(result, settings);
    }

    @Test
    void getUserSettings_SettingsDoesntExist() {
        var predicate = new BooleanBuilder();
        when(predicates.settings()).thenReturn(predicate);
        when(userSettingsRepository.findOne(predicate)).thenReturn(empty());
        when(userSettingsRepository.saveAndFlush(any(UserSettings.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getUserSettings();
        assertThat(result).isNotNull();
        verify(userSettingsRepository).refresh(result);
    }
}