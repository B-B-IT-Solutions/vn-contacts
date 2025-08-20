package cz.prm.utils.assertions.contacts;

import static cz.prm.utils.assertions.contacts.ContractComponentTestAssertions.assertContact;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.networking.ReferralSuggestionDto;
import cz.prm.domain.contacts.contact.Contact;
import java.util.List;
import java.util.Objects;

public class ComponentTestNetworkingAssertions {

    private static final int SUGGESTIONS_COUNT = 7;
    private static final int RELEVANT_SCORE_THRESHOLD = 20;

    public static void assertReferralSuggestions(List<Contact> contacts, PageDto<ReferralSuggestionDto> pageDto) {
        assertThat(pageDto.getContent()).hasSize(SUGGESTIONS_COUNT);
        assertThat(contacts).hasSizeGreaterThanOrEqualTo(SUGGESTIONS_COUNT);
        assertReferralSuggestionsDto(contacts, pageDto.getContent());
    }

    public static void assertReferralSuggestionsDto(List<Contact> contacts, List<ReferralSuggestionDto> dtos) {
        dtos.forEach(dto1 -> {
            var contact = contacts.stream().filter(c1 -> Objects.equals(c1.getContactId(), dto1.getContact().getContactId())).findFirst().get();
            assertReferralSuggestion(contact, dto1);
        });
    }

    public static void assertReferralSuggestion(Contact contact, ReferralSuggestionDto dto) {
        assertContact(contact, dto.getContact());
        assertThat(dto.getScore()).isGreaterThan(RELEVANT_SCORE_THRESHOLD);
        assertThat(dto.getReasons()).isNotEmpty();
    }
}
