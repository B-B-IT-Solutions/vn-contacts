package cz.prm.controllers.mappers;

import static org.mapstruct.InjectionStrategy.FIELD;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.networking.ReferralSuggestionDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.networking.ReferralSuggestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = FIELD, uses = {ContactMapper.class})
public interface NetworkingMapper {

    PageDto<ReferralSuggestionDto> toPageDto(Page<ReferralSuggestion> rss);

    ReferralSuggestionDto toReferralSuggestionDto(ReferralSuggestion rs);
}
