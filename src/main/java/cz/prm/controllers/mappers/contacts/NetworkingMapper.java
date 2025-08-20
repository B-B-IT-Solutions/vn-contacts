package cz.prm.controllers.mappers.contacts;

import static org.mapstruct.InjectionStrategy.FIELD;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.networking.ReferralSuggestionDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.contacts.networking.ReferralSuggestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = FIELD, uses = {ContactMapper.class})
public interface NetworkingMapper {

    PageDto<ReferralSuggestionDto> toPageDto(Page<ReferralSuggestion> rss);

    ReferralSuggestionDto toReferralSuggestionDto(ReferralSuggestion rs);
}
