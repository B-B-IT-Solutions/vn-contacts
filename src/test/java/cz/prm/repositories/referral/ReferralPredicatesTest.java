package cz.prm.repositories.referral;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.contacts.referral.query.ReferralsFilter;
import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class ReferralPredicatesTest {

    private ReferralPredicates predicates;

    @BeforeEach
    void setUp() {
        predicates = new ReferralPredicates();
    }

    @Test
    void referralsNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new ReferralsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.referrals(filter);
            var expectedString = format("referral.owner.username = %s", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void referralsWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new ReferralsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.referrals(filter);
            var expectedString = format("referral.owner.username = %s", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_01");
            predicate = predicates.referrals(filter);
            expectedString = format("referral.owner.username = %s && (containsIc(referral.name,globalFilter_01) || containsIc"
                + "(referral.note,globalFilter_01))", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_02");
            predicate = predicates.referrals(filter);
            expectedString = format("referral.owner.username = %s && (containsIc(referral.name,globalFilter_02) || containsIc"
                + "(referral.note,globalFilter_02))", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setName("title_01");
            predicate = predicates.referrals(filter);
            expectedString = format("referral.owner.username = %s && containsIc(referral.name,title_01)", user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void contactReferralsNoFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new ReferralsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.contactReferrals(11L, filter);
            var expectedString = format("referral.owner.username = %s && referral.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }

    @Test
    void contactReferralsWithFilters() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            var filter = new ReferralsFilter();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var predicate = predicates.contactReferrals(15L, filter);
            var expectedString = format("referral.owner.username = %s && referral.contactId = 15", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_01");
            predicate = predicates.contactReferrals(16L, filter);
            expectedString = format("referral.owner.username = %s && (containsIc(referral.name,globalFilter_01) || containsIc"
                + "(referral.note,globalFilter_01)) && referral.contactId = 16", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_02");
            predicate = predicates.contactReferrals(17L, filter);
            expectedString = format("referral.owner.username = %s && (containsIc(referral.name,globalFilter_02) || containsIc"
                + "(referral.note,globalFilter_02)) && referral.contactId = 17", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setName("title_01");
            predicate = predicates.contactReferrals(17L, filter);
            expectedString = format("referral.owner.username = %s && containsIc(referral.name,title_01) && referral.contactId = 17",
                user.getUsername());
            assertThat(predicate).hasToString(expectedString);
        }
    }

    @Test
    void byReferralId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.byReferralId(10L);
            var expectedString = format("referral.owner.username = %s && referral.referralId = 10", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}