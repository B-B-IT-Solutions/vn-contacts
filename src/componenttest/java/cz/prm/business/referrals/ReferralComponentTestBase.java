package cz.prm.business.referrals;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.referral.ReferralDto;
import cz.prm.controllers.dto.referral.query.ReferralQueryDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class ReferralComponentTestBase extends BusinessComponentTestBase {

    protected static String REMINDERS_BASE_URL = "reminders";
    protected static String CONTACT_REMINDERS_URL = REMINDERS_BASE_URL + "/contact/%s";
    protected static String REMINDER_URL = REMINDERS_BASE_URL + "/reminder";
    protected static String REMINDER_BY_ID_URL = REMINDER_URL + "/%s";

    protected void user1CreateReferral(ReferralDto dto) {
        createReferral(dto, USER_1);
    }

    protected void user2CreateReferral(ReferralDto dto) {
        createReferral(dto, USER_2);
    }

    protected void user3CreateReferral(ReferralDto dto) {
        createReferral(dto, USER_3);
    }

    protected void user1UpdateReferral(Long reminderId, ReferralDto dto) {
        updateReferral(reminderId, dto, USER_1);
    }

    protected void user2UpdateReferral(Long reminderId, ReferralDto dto) {
        updateReferral(reminderId, dto, USER_2);
    }

    protected void user3UpdateReferral(Long reminderId, ReferralDto dto) {
        updateReferral(reminderId, dto, USER_3);
    }

    protected void user1DeleteReferral(Long reminderId) {
        deleteReferral(reminderId, USER_1);
    }

    protected void user2DeleteReferral(Long reminderId) {
        deleteReferral(reminderId, USER_2);
    }

    protected void user3DeleteReferral(Long reminderId) {
        deleteReferral(reminderId, USER_3);
    }

    protected PageDto<ReferralDto> user1GetReferrals(Long contactId, ReferralQueryDto queryDto) {
        return getReferralsPage(contactId, queryDto, USER_1);
    }

    protected PageDto<ReferralDto> user2GetReferrals(Long contactId, ReferralQueryDto queryDto) {
        return getReferralsPage(contactId, queryDto, USER_2);
    }

    protected PageDto<ReferralDto> user3GetReferrals(Long contactId, ReferralQueryDto queryDto) {
        return getReferralsPage(contactId, queryDto, USER_3);
    }

    protected ReferralDto user1GetReferral(Long reminderId) {
        return getReferral(reminderId, USER_1);
    }

    protected ReferralDto user2GetReferral(Long reminderId) {
        return getReferral(reminderId, USER_2);
    }

    protected ReferralDto user3GetReferral(Long reminderId) {
        return getReferral(reminderId, USER_3);
    }

    protected void createReferral(ReferralDto dto, ComponentTestUser user) {
        post(REMINDER_URL, user, dto);
    }

    protected void updateReferral(Long reminderId, ReferralDto dto, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        put(url, user, dto);
    }

    protected void deleteReferral(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        delete(url, user);
    }

    protected PageDto<ReferralDto> getReferralsPage(Long contactId, ReferralQueryDto queryDto, ComponentTestUser user) {
        var baseURl = format(CONTACT_REMINDERS_URL, contactId);
        var url = appendQueryToUrl(baseURl, queryDto);
        var typeRef = new TypeRef<PageDto<ReferralDto>>() {
        };
        return getPage(url, user, typeRef);
    }

    protected ReferralDto getReferral(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        var typeRef = new TypeRef<ReferralDto>() {
        };
        return getOne(url, user, typeRef);
    }

    protected void user1UpdateReferralExpectNotFound(Long reminderId, ReferralDto dto) {
        updateReferralExpectNotFound(reminderId, dto, USER_1);
    }

    protected void user2UpdateReferralExpectNotFound(Long reminderId, ReferralDto dto) {
        updateReferralExpectNotFound(reminderId, dto, USER_2);
    }

    protected void user3UpdateReferralExpectNotFound(Long reminderId, ReferralDto dto) {
        updateReferralExpectNotFound(reminderId, dto, USER_3);
    }

    protected void user1DeleteReferralExpectNotFound(Long reminderId) {
        deleteReferralExpectNotFound(reminderId, USER_1);
    }

    protected void user2DeleteReferralExpectNotFound(Long reminderId) {
        deleteReferralExpectNotFound(reminderId, USER_2);
    }

    protected void user3DeleteReferralExpectNotFound(Long reminderId) {
        deleteReferralExpectNotFound(reminderId, USER_3);
    }

    protected void user1GetReferralExpectNotFound(Long reminderId) {
        getReferralExpectNotFound(reminderId, USER_1);
    }

    protected void user2GetReferralExpectNotFound(Long reminderId) {
        getReferralExpectNotFound(reminderId, USER_2);
    }

    protected void user3GetReferralExpectNotFound(Long reminderId) {
        getReferralExpectNotFound(reminderId, USER_3);
    }

    protected void updateReferralExpectNotFound(Long reminderId, ReferralDto dto, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        putExpectNotFound(url, user, dto);
    }

    protected void deleteReferralExpectNotFound(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        deleteExpectNotFound(url, user);
    }

    protected void getReferralExpectNotFound(Long reminderId, ComponentTestUser user) {
        var url = format(REMINDER_BY_ID_URL, reminderId);
        getExpectNotFound(url, user);
    }

    protected String appendQueryToUrl(String url, ReferralQueryDto queryDto) {
        var sb = new StringBuilder(url);
        var pagination = toUrlPaginationParams(queryDto.getPagination());
        var sort = toUrlSortParams(queryDto.getSort());

        if (isNotBlank(pagination) || isNotBlank(sort)) {
            sb.append("?");
            sb.append(pagination);
            sb.append(sort);
        }
        return sb.toString();
    }
}
