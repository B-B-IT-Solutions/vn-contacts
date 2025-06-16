package cz.prm.services;

import static cz.prm.utils.ReferralUtils.referral;
import static cz.prm.utils.ReferralUtils.referrals;
import static cz.prm.utils.ReferralUtils.referralsQuery;
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
import cz.prm.domain.referral.query.ReferralsFilter;
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
    private ArgumentCaptor<Referral> referralCapt;

    private ReferralService referralService;

    @BeforeEach
    void setUp() {
        referralService = new ReferralService(repository, predicates);
    }

    @Test
    void getReferrals() {
        var referrals = referrals();
        var page = new PageImpl(referrals);
        var query = referralsQuery();
        var predicate = new BooleanBuilder();

        when(predicates.referrals(query.getFilter())).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = referralService.getReferrals(query);
        assertPage(result, page);
    }

    @Test
    void getContactReferrals() {
        var referrals = referrals();
        var page = new PageImpl(referrals);
        var query = referralsQuery();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();

        when(predicates.contactReferrals(contactId, query.getFilter())).thenReturn(predicate);
        when(repository.findAll(eq(predicate), any(PageRequest.class))).thenReturn(page);
        var result = referralService.getContactReferrals(contactId, query);
        assertPage(result, page);
    }

    @Test
    void getReferral() {
        var referral = referral();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(referral.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(referral));
        var result = referralService.getReferral(referral.getReferralId());
        assertReferral(result, referral);
    }

    @Test
    void getReferral_EntityNotFound() {
        var referral = referral();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(referral.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.getReferral(referral.getReferralId()));
    }

    @Test
    void createReferral() {
        var referral = referral();
        referralService.createReferral(referral);
        verify(repository).save(referral);
    }

    @Test
    void updateReferral() {
        var referralIdDb = referral();
        var updatedReferral = referral();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(referralIdDb.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(referralIdDb));

        referralService.updateReferral(referralIdDb.getReferralId(), updatedReferral);
        verify(repository).save(referralCapt.capture());
        var savedReferral = referralCapt.getValue();
        assertFieldsUpdated(referralIdDb, updatedReferral, savedReferral);
    }

    @Test
    void updateReferral_EntityNotFound() {
        var referralIdDb = referral();
        var updatedReferral = referral();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(referralIdDb.getReferralId())).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.updateReferral(referralIdDb.getReferralId(), updatedReferral));
    }

    @Test
    void deleteReferral() {
        var referralIdDb = referral();
        var referralId = referralIdDb.getReferralId();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(referralId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(of(referralIdDb));

        referralService.deleteReferral(referralId);
        verify(repository).deleteById(referralId);
    }

    @Test
    void deleteReferral_EntityNotFound() {
        var referralIdDb = referral();
        var referralId = referralIdDb.getReferralId();
        var predicate = new BooleanBuilder();
        when(predicates.byReferralId(referralId)).thenReturn(predicate);
        when(repository.findOne(predicate)).thenReturn(empty());
        assertThrows(EntityNotFoundException.class, () -> referralService.deleteReferral(referralId));
    }

    @Test
    void deleteByContactId() {
        var referrals = referrals();
        var contactId = randomLong();
        var predicate = new BooleanBuilder();
        when(predicates.contactReferrals(eq(contactId), any(ReferralsFilter.class))).thenReturn(predicate);
        when(repository.findAll(predicate)).thenReturn(referrals);

        referralService.deleteByContactId(contactId);
        verify(repository).deleteAll(referrals);
    }

    private void assertFieldsUpdated(Referral referralIdDb, Referral updatedReferral, Referral savedReferral) {
        assertThat(referralIdDb.getReferralId()).isEqualTo(savedReferral.getReferralId());
        assertThat(referralIdDb.getContactId()).isEqualTo(savedReferral.getContactId());
        assertThat(referralIdDb.getOwner()).isEqualTo(savedReferral.getOwner());
        assertThat(referralIdDb.getCreationDate()).isEqualTo(savedReferral.getCreationDate());
        assertThat(savedReferral.getName()).isEqualTo(updatedReferral.getName());
        assertThat(savedReferral.getDescription()).isEqualTo(updatedReferral.getDescription());
    }
}