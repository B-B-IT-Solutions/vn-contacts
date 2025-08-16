package cz.prm.utils.assertions;

import static cz.prm.utils.assertions.ContactAssertions.assertContact;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.networking.ReferralSuggestionDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.networking.ReferralRequirement;
import cz.prm.domain.networking.ReferralSuggestion;
import java.util.List;
import java.util.Objects;

public class NetworkingAssertions {

    public static void assertPage(Page<ReferralSuggestion> page, PageDto<ReferralSuggestionDto> pageDto) {
        assertThat(page.getTotalPages()).isEqualTo(pageDto.getTotalPages());
        assertThat(page.getNumberOfElements()).isEqualTo(pageDto.getNumberOfElements());
        assertThat(page.getTotalElements()).isEqualTo(pageDto.getTotalElements());
        assertThat(page.getPageSize()).isEqualTo(pageDto.getPageSize());
        assertThat(page.getPageNumber()).isEqualTo(pageDto.getPageNumber());
        assertReferralSuggestionsDto(page.getContent(), pageDto.getContent());
    }

    public static void assertPage(Page<ReferralSuggestion> page1, Page<ReferralSuggestion> page2) {
        assertThat(page1.getTotalPages()).isEqualTo(page2.getTotalPages());
        assertThat(page1.getNumberOfElements()).isEqualTo(page2.getNumberOfElements());
        assertThat(page1.getTotalElements()).isEqualTo(page2.getTotalElements());
        assertThat(page1.getPageSize()).isEqualTo(page2.getPageSize());
        assertThat(page1.getPageNumber()).isEqualTo(page2.getPageNumber());
        assertReferralSuggestions(page1.getContent(), page2.getContent());
    }

    public static void assertReferralRequirement(ReferralRequirement rr, Contact contact) {
        assertContact(rr.getContact(), contact);
        assertThat(rr.getIndustries()).containsExactlyElementsOf(contact.getIndustries());
        assertThat(rr.getSkills()).containsExactlyElementsOf(contact.getSkills());
        assertThat(rr.getProducts()).containsExactlyElementsOf(contact.getProducts());
        assertThat(rr.getTargetMarkets()).containsExactlyElementsOf(contact.getTargetMarkets());
    }

    public static void assertContactReferralSuggestions(List<ReferralSuggestion> rss, List<Contact> contactReferrals) {
        assertThat(rss).isNotEmpty().hasSizeLessThanOrEqualTo(contactReferrals.size());
        rss.forEach(rs1 -> {
            var contact = contactReferrals.stream().filter(c -> Objects.equals(rs1.getContact(), c)).findFirst().get();
            assertContactReferralSuggestion(rs1, contact);
        });
    }

    public static void assertContactReferralSuggestion(ReferralSuggestion rs, Contact contact) {
        assertContact(rs.getContact(), contact);
    }

    public static void assertReferralSuggestions(List<ReferralSuggestion> rss1, List<ReferralSuggestion> rss2) {
        assertThat(rss1).isNotEmpty().hasSameSizeAs(rss2);
        rss1.forEach(rs1 -> {
            var rs2 = rss2.stream().filter(u -> Objects.equals(rs1.getContact(), u.getContact())).findFirst().get();
            assertReferralSuggestion(rs1, rs2);
        });
    }

    public static void assertReferralSuggestionsDto(List<ReferralSuggestion> rss1, List<ReferralSuggestionDto> dtos) {
        assertThat(rss1).isNotEmpty().hasSameSizeAs(dtos);
        rss1.forEach(rs1 -> {
            var rs2 = dtos.stream().filter(u -> Objects.equals(rs1.getContact().getContactId(), u.getContact().getContactId())).findFirst().get();
            assertReferralSuggestion(rs1, rs2);
        });
    }

    public static void assertReferralSuggestion(ReferralSuggestion rs1, ReferralSuggestion rs2) {
        assertContact(rs1.getContact(), rs2.getContact());
        assertThat(rs1.getScore()).isEqualTo(rs2.getScore());
        assertThat(rs1.getJustifications()).isNotEmpty().containsExactlyEntriesOf(rs2.getJustifications());
        assertThat(rs1.getCheckedCriterias()).isNotEmpty().containsExactlyElementsOf(rs2.getCheckedCriterias());
    }

    public static void assertReferralSuggestion(ReferralSuggestion rs, ReferralSuggestionDto dto) {
        assertContact(rs.getContact(), dto.getContact());
        assertThat(rs.getScore()).isEqualTo(dto.getScore());
        assertThat(rs.getReasons()).isNotEmpty().containsExactlyElementsOf(dto.getReasons());
    }
}
