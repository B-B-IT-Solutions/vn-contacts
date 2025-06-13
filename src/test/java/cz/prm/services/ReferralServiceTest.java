package cz.prm.services;

import static cz.prm.utils.ReferralUtils.reminder;
import static cz.prm.utils.ReferralUtils.reminders;
import static cz.prm.utils.ReferralUtils.remindersQuery;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.assertions.ReferralAssertions.assertPage;
import static cz.prm.utils.assertions.ReferralAssertions.assertReferral;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.referral.Referral;
import cz.prm.repositories.referral.ReferralPredicates;
import cz.prm.repositories.referral.ReferralRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ReferralServiceTest {

    @Mock
    private ReferralRepository repository;
    @Mock
    private ReferralPredicates predicates;
    @Captor
    private ArgumentCaptor<Referral> reminderCapt;

    private ReferralService referralService;

    @BeforeEach
    void setUp() {
        referralService = new ReferralService(repository, predicates);
    }

    @Test
    void getReferrals() {
        var reminders = reminders();
        var page = new PageImpl(reminders);
        var query = remindersQuery();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();

        when(predicates.byContactId(contactId)).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = referralService.getReferrals(contactId, query);
        assertPage(result, page);
    }

    @Test
    void getReferral() {
        var reminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(reminder.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminder));
        var result = referralService.getReferral(reminder.getReferralId());
        assertReferral(result, reminder);
    }

    @Test
    void getReferral_EntityNotFound() {
        var reminder = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(reminder.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.getReferral(reminder.getReferralId()));
    }

    @Test
    void createReferral() {
        var reminder = reminder();
        referralService.createReferral(reminder);
        verify(repository).save(reminder);
    }

    @Test
    void updateReferral() {
        var reminderIdDb = reminder();
        var updatedReferral = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(reminderIdDb.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminderIdDb));

        referralService.updateReferral(reminderIdDb.getReferralId(), updatedReferral);
        verify(repository).save(reminderCapt.capture());
        var savedReferral = reminderCapt.getValue();
        assertFieldsUpdated(reminderIdDb, updatedReferral, savedReferral);
    }

    @Test
    void updateReferral_EntityNotFound() {
        var reminderIdDb = reminder();
        var updatedReferral = reminder();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(reminderIdDb.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.updateReferral(reminderIdDb.getReferralId(), updatedReferral));
    }

    @Test
    void deleteReferral() {
        var reminderIdDb = reminder();
        var reminderId = reminderIdDb.getReferralId();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(reminderId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(reminderIdDb));

        referralService.deleteReferral(reminderId);
        verify(repository).deleteById(reminderId);
    }

    @Test
    void deleteReferral_EntityNotFound() {
        var reminderIdDb = reminder();
        var reminderId = reminderIdDb.getReferralId();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(reminderId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.deleteReferral(reminderId));
    }

    @Test
    void deleteByContactId() {
        var reminders = reminders();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();
        when(predicates.byContactId(eq(contactId))).thenReturn(predicate);
        when(repository.findAll(predicate)).thenReturn(reminders);

        referralService.deleteByContactId(contactId);
        verify(repository).deleteAll(reminders);
    }

    private void assertFieldsUpdated(Referral referralIdDb, Referral updatedReferral, Referral savedReferral) {
        assertThat(referralIdDb.getReferralId()).isEqualTo(savedReferral.getReferralId());
        assertThat(referralIdDb.getContactId()).isEqualTo(savedReferral.getContactId());
        assertThat(referralIdDb.getOwner()).isEqualTo(savedReferral.getOwner());
        assertThat(referralIdDb.getCreationDate()).isEqualTo(savedReferral.getCreationDate());
        assertThat(savedReferral.getTitle()).isEqualTo(updatedReferral.getTitle());
        assertThat(savedReferral.getDescription()).isEqualTo(updatedReferral.getDescription());
        assertThat(savedReferral.getRecurrence()).isEqualTo(updatedReferral.getRecurrence());
    }
}