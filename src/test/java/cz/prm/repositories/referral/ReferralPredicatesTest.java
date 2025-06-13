package cz.prm.repositories.referral;

import static cz.prm.utils.CommonUtils.user;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.referral.query.ReferralsFilter;
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
                + "(referral.description,globalFilter_01))", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter("globalFilter_02");
            predicate = predicates.referrals(filter);
            expectedString = format("referral.owner.username = %s && (containsIc(referral.name,globalFilter_02) || containsIc"
                + "(referral.description,globalFilter_02))", user.getUsername());
            assertThat(predicate).hasToString(expectedString);

            filter.setGlobalFilter(null);
            filter.setName("title_01");
            predicate = predicates.referrals(filter);
            expectedString = format("referral.owner.username = %s && containsIc(referral.name,title_01)", user.getUsername());
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

    @Test
    void byContactId() {
        try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
            var user = user();
            context.when(() -> SecurityContextUtils.getUser()).thenReturn(user);
            var query = predicates.contactReferrals(11L);
            var expectedString = format("referral.owner.username = %s && referral.contactId = 11", user.getUsername());
            assertThat(query).hasToString(expectedString);
        }
    }
}