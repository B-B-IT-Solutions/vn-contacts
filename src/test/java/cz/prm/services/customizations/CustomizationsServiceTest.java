package cz.prm.services.customizations;

import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_INDUSTRIES;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_PRODUCTS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_SKILLS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_TARGET_MARKETS;
import static cz.prm.utils.SettingsUtils.contactSettings;
import static cz.prm.utils.SettingsUtils.noteSettings;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.customizations.contact.ContactCustomizations;
import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.repositories.customizations.ContactCustomizationsRepository;
import cz.prm.repositories.customizations.CustomizationsPredicates;
import cz.prm.repositories.customizations.NoteCustomizaitonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomizationsServiceTest {

    @Mock
    private ContactCustomizationsRepository contactCustomizationsRepository;
    @Mock
    private NoteCustomizaitonsRepository noteCustomizaitonsRepository;
    @Mock
    private CustomizationsPredicates predicates;
    @Captor
    private ArgumentCaptor<ContactCustomizations> contactSettingsCapt;
    @Captor
    private ArgumentCaptor<NoteCustomizations> noteSettingsCapt;

    private CustomizationsService settingsService;

    @BeforeEach
    void setUp() {
        settingsService = new CustomizationsService(contactCustomizationsRepository, noteCustomizaitonsRepository, predicates);
    }

    @Test
    void getContactSettings() {
        var settings = contactSettings();
        var predicate = new BooleanBuilder();
        when(predicates.contactSettings()).thenReturn(predicate);
        when(contactCustomizationsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getContactSettings();
        assertSettings(result, settings);
    }

    @Test
    void getContactSettings_SettingsNotFound() {
        var predicate = new BooleanBuilder();
        when(predicates.contactSettings()).thenReturn(predicate);
        when(contactCustomizationsRepository.findOne(predicate)).thenReturn(empty());
        when(contactCustomizationsRepository.saveAndFlush(any(ContactCustomizations.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getContactSettings();
        assertThat(result).isNotNull();
        assertThat(result.getIndustries()).containsExactlyElementsOf(INITIAL_INDUSTRIES);
        assertThat(result.getSkills()).containsExactlyElementsOf(INITIAL_SKILLS);
        assertThat(result.getProducts()).containsExactlyElementsOf(INITIAL_PRODUCTS);
        assertThat(result.getTargetMarkets()).containsExactlyElementsOf(INITIAL_TARGET_MARKETS);
        verify(contactCustomizationsRepository).refresh(result);
    }

    @Test
    void getNoteSettings() {
        var settings = noteSettings();
        var predicate = new BooleanBuilder();
        when(predicates.noteSettings()).thenReturn(predicate);
        when(noteCustomizaitonsRepository.findOne(predicate)).thenReturn(of(settings));

        var result = settingsService.getNoteSettings();
        assertSettings(result, settings);
    }

    @Test
    void getNoteSettings_SettingsNotFound() {
        var predicate = new BooleanBuilder();
        when(predicates.noteSettings()).thenReturn(predicate);
        when(noteCustomizaitonsRepository.findOne(predicate)).thenReturn(empty());
        when(noteCustomizaitonsRepository.saveAndFlush(any(NoteCustomizations.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getNoteSettings();
        assertThat(result).isNotNull();
        verify(noteCustomizaitonsRepository).refresh(result);
    }

    @Test
    void updateContactSettings() {
        var settingsInDb = contactSettings();
        var updatedSettings = contactSettings();
        var predicate = new BooleanBuilder();
        when(predicates.contactSettings()).thenReturn(predicate);
        when(contactCustomizationsRepository.findOne(predicate)).thenReturn(of(settingsInDb));

        settingsService.updateContactSettings(updatedSettings);
        verify(contactCustomizationsRepository).save(contactSettingsCapt.capture());
        var savedSettings = contactSettingsCapt.getValue();
        assertContactSettingFieldsUpdated(settingsInDb, updatedSettings, savedSettings);
    }

    @Test
    void updateNoteSettings() {
        var settingsInDb = noteSettings();
        var updatedSettings = noteSettings();
        var predicate = new BooleanBuilder();
        when(predicates.noteSettings()).thenReturn(predicate);
        when(noteCustomizaitonsRepository.findOne(predicate)).thenReturn(of(settingsInDb));

        settingsService.updateNoteSettings(updatedSettings);
        verify(noteCustomizaitonsRepository).save(noteSettingsCapt.capture());
        var savedSettings = noteSettingsCapt.getValue();
        assertNoteSettingFieldsUpdated(settingsInDb, updatedSettings, savedSettings);
    }

    private static void assertContactSettingFieldsUpdated(ContactCustomizations settingsInDb, ContactCustomizations updatedSettings,
        ContactCustomizations savedSettings) {
        assertThat(settingsInDb.getSettingsId()).isEqualTo(savedSettings.getSettingsId());
        assertThat(settingsInDb.getOwner()).isEqualTo(savedSettings.getOwner());
        assertThat(savedSettings.getLabels()).isEqualTo(updatedSettings.getLabels());
        assertThat(savedSettings.getIndustries()).isEqualTo(updatedSettings.getIndustries());
        assertThat(savedSettings.getSkills()).isEqualTo(updatedSettings.getSkills());
        assertThat(savedSettings.getProducts()).isEqualTo(updatedSettings.getProducts());
        assertThat(savedSettings.getTargetMarkets()).isEqualTo(updatedSettings.getTargetMarkets());
    }

    private static void assertNoteSettingFieldsUpdated(
        NoteCustomizations settingsInDb, NoteCustomizations updatedSettings, NoteCustomizations savedSettings) {
        assertThat(settingsInDb.getSettingsId()).isEqualTo(savedSettings.getSettingsId());
        assertThat(settingsInDb.getOwner()).isEqualTo(savedSettings.getOwner());
        assertThat(savedSettings.getCategories()).isEqualTo(updatedSettings.getCategories());
    }
}