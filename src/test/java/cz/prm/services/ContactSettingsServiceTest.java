package cz.prm.services;

import static cz.prm.utils.SettingsUtils.accountSettings;
import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.settings.ContactSettings;
import cz.prm.repositories.settings.GeneralSettingsRepository;
import cz.prm.repositories.settings.SettingsPredicates;
import cz.prm.repositories.settings.UserSettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactSettingsServiceTest {

    @Mock
    private GeneralSettingsRepository generalSettingsRepository;
    @Mock
    private UserSettingsRepository userSettingsRepository;
    @Mock
    private SettingsPredicates predicates;
    @Captor
    private ArgumentCaptor<ContactSettings> userSettingsCapt;

    private SettingsService settingsService;

    @BeforeEach
    void setUp() {
        settingsService = new SettingsService(generalSettingsRepository, userSettingsRepository, predicates);
    }

    @Test
    void getAccountSettings() {
        var predicate = new BooleanBuilder();
        when(predicates.accountSettings()).thenReturn(predicate);
        when(generalSettingsRepository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> settingsService.getAccountSettings());
    }

    @Test
    void getAccountSettings_SettingsNotFound() {
        var settings = accountSettings();
        var predicate = new BooleanBuilder();
        when(predicates.accountSettings()).thenReturn(predicate);
        when(generalSettingsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getAccountSettings();
        assertSettings(result, settings);
    }

    @Test
    void getContactSettings() {
        var settings = contactSettings();
        var predicate = new BooleanBuilder();
        when(predicates.userSettings()).thenReturn(predicate);
        when(userSettingsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getContactSettings();
        assertSettings(result, settings);
    }

    @Test
    void getContactSettings_SettingsNotFound() {
        var predicate = new BooleanBuilder();
        when(predicates.userSettings()).thenReturn(predicate);
        when(userSettingsRepository.findOne(predicate)).thenReturn(empty());
        when(userSettingsRepository.saveAndFlush(any(ContactSettings.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getContactSettings();
        assertThat(result).isNotNull();
        verify(userSettingsRepository).refresh(result);
    }

    @Test
    void updateContactSettings() {
        var settingsInDb = contactSettings();
        var updatedSettings = contactSettings();
        var predicate = new BooleanBuilder();
        when(predicates.userSettings()).thenReturn(predicate);
        when(userSettingsRepository.findOne(predicate)).thenReturn(of(settingsInDb));

        settingsService.updateContactSettings(updatedSettings);
        verify(userSettingsRepository).save(userSettingsCapt.capture());
        var savedSettings = userSettingsCapt.getValue();
        assertUserSettingFieldsUpdated(settingsInDb, updatedSettings, savedSettings);
    }

    private static void assertUserSettingFieldsUpdated(ContactSettings settingsInDb, ContactSettings updatedSettings, ContactSettings savedSettings) {
        assertThat(settingsInDb.getSettingsId()).isEqualTo(savedSettings.getSettingsId());
        assertThat(settingsInDb.getOwner()).isEqualTo(savedSettings.getOwner());
        assertThat(savedSettings.getLabels()).isEqualTo(updatedSettings.getLabels());
        assertThat(savedSettings.getIndustries()).isEqualTo(updatedSettings.getIndustries());
    }
}