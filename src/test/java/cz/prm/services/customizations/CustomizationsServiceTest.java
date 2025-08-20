package cz.prm.services.customizations;

import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_INDUSTRIES;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_PRODUCTS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_SKILLS;
import static cz.prm.domain.customizations.contact.InitContactCustomizations.INITIAL_TARGET_MARKETS;
import static cz.prm.utils.assertions.SettingsAssertions.assertSettings;
import static cz.prm.utils.data.customizations.CustomizationsUtils.contactCustomizations;
import static cz.prm.utils.data.customizations.CustomizationsUtils.noteCustomizations;
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
    private ArgumentCaptor<ContactCustomizations> contactCustomizationsCapt;
    @Captor
    private ArgumentCaptor<NoteCustomizations> noteCustomizationsCapt;

    private CustomizationsService settingsService;

    @BeforeEach
    void setUp() {
        settingsService = new CustomizationsService(contactCustomizationsRepository, noteCustomizaitonsRepository, predicates);
    }

    @Test
    void getContactCustomizations() {
        var customizations = contactCustomizations();
        var predicate = new BooleanBuilder();
        when(predicates.contactCustomizations()).thenReturn(predicate);
        when(contactCustomizationsRepository.findOne(predicate)).thenReturn(of(customizations));

        var result = settingsService.getContactCustomizations();
        assertSettings(result, customizations);
    }

    @Test
    void getContactCustomizations_CustomizationsNotFound() {
        var predicate = new BooleanBuilder();
        when(predicates.contactCustomizations()).thenReturn(predicate);
        when(contactCustomizationsRepository.findOne(predicate)).thenReturn(empty());
        when(contactCustomizationsRepository.saveAndFlush(any(ContactCustomizations.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getContactCustomizations();
        assertThat(result).isNotNull();
        assertThat(result.getIndustries()).containsExactlyElementsOf(INITIAL_INDUSTRIES);
        assertThat(result.getSkills()).containsExactlyElementsOf(INITIAL_SKILLS);
        assertThat(result.getProducts()).containsExactlyElementsOf(INITIAL_PRODUCTS);
        assertThat(result.getTargetMarkets()).containsExactlyElementsOf(INITIAL_TARGET_MARKETS);
        verify(contactCustomizationsRepository).refresh(result);
    }

    @Test
    void getNoteCustomizations() {
        var customizations = noteCustomizations();
        var predicate = new BooleanBuilder();
        when(predicates.noteCustomizations()).thenReturn(predicate);
        when(noteCustomizaitonsRepository.findOne(predicate)).thenReturn(of(customizations));

        var result = settingsService.getNoteCustomizations();
        assertSettings(result, customizations);
    }

    @Test
    void getNoteCustomizations_CustomizationsNotFound() {
        var predicate = new BooleanBuilder();
        when(predicates.noteCustomizations()).thenReturn(predicate);
        when(noteCustomizaitonsRepository.findOne(predicate)).thenReturn(empty());
        when(noteCustomizaitonsRepository.saveAndFlush(any(NoteCustomizations.class))).thenAnswer((invocation -> invocation.getArgument(0)));

        var result = settingsService.getNoteCustomizations();
        assertThat(result).isNotNull();
        verify(noteCustomizaitonsRepository).refresh(result);
    }

    @Test
    void updateContactCustomizations() {
        var settingsInDb = contactCustomizations();
        var updatedSettings = contactCustomizations();
        var predicate = new BooleanBuilder();
        when(predicates.contactCustomizations()).thenReturn(predicate);
        when(contactCustomizationsRepository.findOne(predicate)).thenReturn(of(settingsInDb));

        settingsService.updateContactCustomizations(updatedSettings);
        verify(contactCustomizationsRepository).save(contactCustomizationsCapt.capture());
        var savedSettings = contactCustomizationsCapt.getValue();
        assertContactCustomizationFieldsUpdated(settingsInDb, updatedSettings, savedSettings);
    }

    @Test
    void updateNoteCustomizations() {
        var settingsInDb = noteCustomizations();
        var updatedSettings = noteCustomizations();
        var predicate = new BooleanBuilder();
        when(predicates.noteCustomizations()).thenReturn(predicate);
        when(noteCustomizaitonsRepository.findOne(predicate)).thenReturn(of(settingsInDb));

        settingsService.updateNoteCustomizations(updatedSettings);
        verify(noteCustomizaitonsRepository).save(noteCustomizationsCapt.capture());
        var savedSettings = noteCustomizationsCapt.getValue();
        assertNoteCustomizationFieldsUpdated(settingsInDb, updatedSettings, savedSettings);
    }

    private static void assertContactCustomizationFieldsUpdated(ContactCustomizations settingsInDb, ContactCustomizations updatedSettings,
        ContactCustomizations savedSettings) {
        assertThat(settingsInDb.getSettingsId()).isEqualTo(savedSettings.getSettingsId());
        assertThat(settingsInDb.getOwner()).isEqualTo(savedSettings.getOwner());
        assertThat(savedSettings.getLabels()).isEqualTo(updatedSettings.getLabels());
        assertThat(savedSettings.getIndustries()).isEqualTo(updatedSettings.getIndustries());
        assertThat(savedSettings.getSkills()).isEqualTo(updatedSettings.getSkills());
        assertThat(savedSettings.getProducts()).isEqualTo(updatedSettings.getProducts());
        assertThat(savedSettings.getTargetMarkets()).isEqualTo(updatedSettings.getTargetMarkets());
    }

    private static void assertNoteCustomizationFieldsUpdated(NoteCustomizations settingsInDb, NoteCustomizations updatedSettings,
        NoteCustomizations savedSettings) {
        assertThat(settingsInDb.getSettingsId()).isEqualTo(savedSettings.getSettingsId());
        assertThat(settingsInDb.getOwner()).isEqualTo(savedSettings.getOwner());
        assertThat(savedSettings.getCategories()).isEqualTo(updatedSettings.getCategories());
    }
}