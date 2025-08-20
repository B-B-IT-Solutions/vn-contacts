package cz.prm.controllers.mappers.contacts;

import static cz.prm.domain.contacts.referral.query.ReferralsQuery.DEFAULT_REFERRALS_SORT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.referral.ReferralDto;
import cz.prm.controllers.dto.contacts.referral.query.ReferralQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import cz.prm.domain.contacts.referral.Referral;
import cz.prm.domain.contacts.referral.query.ReferralsFilter;
import cz.prm.domain.contacts.referral.query.ReferralsQuery;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReferralMapper {

    PageDto<ReferralDto> toPageDto(Page<Referral> referrals);

    ReferralDto toReferralDto(Referral referral);

    @Mapping(target = "owner", ignore = true)
    Referral toReferral(ReferralDto dto);

    ReferralsQuery toReferralsQuery(ReferralQueryDto dto);

    default ReferralsQuery toNullSafeReferralsQuery(ReferralQueryDto dto) {
        if (isNull(dto)) {
            return new ReferralsQuery();
        }
        return toReferralsQuery(dto);
    }

    @AfterMapping
    default void afterReferralsQuery(ReferralQueryDto source, @MappingTarget ReferralsQuery target) {
        if (isNull(target.getPagination())) {
            target.setPagination(new Pagination());
        }
        if (isNull(target.getFilter())) {
            target.setFilter(new ReferralsFilter());
        }
        if (isBlank(target.getSort())) {
            target.setSort(DEFAULT_REFERRALS_SORT);
        }
    }
}
