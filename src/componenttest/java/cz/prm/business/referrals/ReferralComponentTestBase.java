package cz.prm.business.referrals;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contacts.referral.ReferralDto;
import cz.prm.controllers.dto.contacts.referral.query.ReferralQueryDto;
import cz.prm.controllers.dto.contacts.referral.query.ReferralsFilterDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class ReferralComponentTestBase extends BusinessComponentTestBase {

    protected static String REFERRALS_BASE_URL = "referrals";
    protected static String REFERRALS_URL = REFERRALS_BASE_URL;
    protected static String CONTACT_REFERRALS_URL = REFERRALS_URL + "/contact/%s";
    protected static String REFERRAL_URL = REFERRALS_URL + "/%s";

    protected void user1CreateReferral(ReferralDto dto) {
        createReferral(dto, USER_1);
    }

    protected void user2CreateReferral(ReferralDto dto) {
        createReferral(dto, USER_2);
    }

    protected void user3CreateReferral(ReferralDto dto) {
        createReferral(dto, USER_3);
    }

    protected void user1UpdateReferral(Long referralId, ReferralDto dto) {
        updateReferral(referralId, dto, USER_1);
    }

    protected void user2UpdateReferral(Long referralId, ReferralDto dto) {
        updateReferral(referralId, dto, USER_2);
    }

    protected void user3UpdateReferral(Long referralId, ReferralDto dto) {
        updateReferral(referralId, dto, USER_3);
    }

    protected void user1DeleteReferral(Long referralId) {
        deleteReferral(referralId, USER_1);
    }

    protected void user2DeleteReferral(Long referralId) {
        deleteReferral(referralId, USER_2);
    }

    protected void user3DeleteReferral(Long referralId) {
        deleteReferral(referralId, USER_3);
    }

    protected PageDto<ReferralDto> user1GetReferrals(ReferralQueryDto queryDto) {
        return getReferralsPage(queryDto, USER_1);
    }

    protected PageDto<ReferralDto> user2GetReferrals(ReferralQueryDto queryDto) {
        return getReferralsPage(queryDto, USER_2);
    }

    protected PageDto<ReferralDto> user3GetReferrals(ReferralQueryDto queryDto) {
        return getReferralsPage(queryDto, USER_3);
    }

    protected PageDto<ReferralDto> user1GetContactReferrals(Long contactId, ReferralQueryDto queryDto) {
        return getContactReferralsPage(contactId, queryDto, USER_1);
    }

    protected PageDto<ReferralDto> user2GetContactReferrals(Long contactId, ReferralQueryDto queryDto) {
        return getContactReferralsPage(contactId, queryDto, USER_2);
    }

    protected PageDto<ReferralDto> user3GetContactReferrals(Long contactId, ReferralQueryDto queryDto) {
        return getContactReferralsPage(contactId, queryDto, USER_3);
    }

    protected ReferralDto user1GetReferral(Long referralId) {
        return getReferral(referralId, USER_1);
    }

    protected ReferralDto user2GetReferral(Long referralId) {
        return getReferral(referralId, USER_2);
    }

    protected ReferralDto user3GetReferral(Long referralId) {
        return getReferral(referralId, USER_3);
    }

    protected void createReferral(ReferralDto dto, ComponentTestUser user) {
        post(REFERRALS_URL, user, dto);
    }

    protected void updateReferral(Long referralId, ReferralDto dto, ComponentTestUser user) {
        var url = format(REFERRAL_URL, referralId);
        put(url, user, dto);
    }

    protected void deleteReferral(Long referralId, ComponentTestUser user) {
        var url = format(REFERRAL_URL, referralId);
        delete(url, user);
    }

    protected PageDto<ReferralDto> getReferralsPage(ReferralQueryDto queryDto, ComponentTestUser user) {
        var baseURl = REFERRALS_BASE_URL;
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<ReferralDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected PageDto<ReferralDto> getContactReferralsPage(Long contactId, ReferralQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(CONTACT_REFERRALS_URL, contactId);
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<ReferralDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected ReferralDto getReferral(Long referralId, ComponentTestUser user) {
        var url = format(REFERRAL_URL, referralId);
        var typeRef = new TypeRef<ReferralDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void user1UpdateReferralExpectNotFound(Long referralId, ReferralDto dto) {
        updateReferralExpectNotFound(referralId, dto, USER_1);
    }

    protected void user2UpdateReferralExpectNotFound(Long referralId, ReferralDto dto) {
        updateReferralExpectNotFound(referralId, dto, USER_2);
    }

    protected void user3UpdateReferralExpectNotFound(Long referralId, ReferralDto dto) {
        updateReferralExpectNotFound(referralId, dto, USER_3);
    }

    protected void user1DeleteReferralExpectNotFound(Long referralId) {
        deleteReferralExpectNotFound(referralId, USER_1);
    }

    protected void user2DeleteReferralExpectNotFound(Long referralId) {
        deleteReferralExpectNotFound(referralId, USER_2);
    }

    protected void user3DeleteReferralExpectNotFound(Long referralId) {
        deleteReferralExpectNotFound(referralId, USER_3);
    }

    protected void user1GetReferralExpectNotFound(Long referralId) {
        getReferralExpectNotFound(referralId, USER_1);
    }

    protected void user2GetReferralExpectNotFound(Long referralId) {
        getReferralExpectNotFound(referralId, USER_2);
    }

    protected void user3GetReferralExpectNotFound(Long referralId) {
        getReferralExpectNotFound(referralId, USER_3);
    }

    protected void updateReferralExpectNotFound(Long referralId, ReferralDto dto, ComponentTestUser user) {
        var url = format(REFERRAL_URL, referralId);
        putExpectNotFound(url, user, dto);
    }

    protected void deleteReferralExpectNotFound(Long referralId, ComponentTestUser user) {
        var url = format(REFERRAL_URL, referralId);
        deleteExpectNotFound(url, user);
    }

    protected void getReferralExpectNotFound(Long referralId, ComponentTestUser user) {
        var url = format(REFERRAL_URL, referralId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, ReferralQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var filters = toUrlFilterParams(queryDto.getFilter());
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(filters) || isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(filters);
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }

    protected String toUrlFilterParams(ReferralsFilterDto filterDto) {
        var sb = new StringBuilder();
        if (nonNull(filterDto)) {
            if (isNotEmpty(filterDto.getGlobalFilter())) {
                sb.append("filter.globalFilter=");
                sb.append(filterDto.getGlobalFilter());
                sb.append("&");
            }
            if (isNotEmpty(filterDto.getName())) {
                sb.append("filter.name=");
                sb.append(filterDto.getName());
                sb.append("&");
            }
            if (nonNull(filterDto.getStatus())) {
                sb.append("filter.status=");
                sb.append(filterDto.getStatus());
                sb.append("&");
            }
        }
        return sb.toString();
    }
}
