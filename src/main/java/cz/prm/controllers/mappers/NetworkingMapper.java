package cz.prm.controllers.mappers;

import static org.mapstruct.InjectionStrategy.FIELD;

import cz.prm.controllers.dto.networking.ReferralSuggestionDto;
import cz.prm.domain.networking.ReferralSuggestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = FIELD, uses = {ContactMapper.class})
public interface NetworkingMapper {

    ReferralSuggestionDto toReferralSuggestionDto(ReferralSuggestion rs);
}
