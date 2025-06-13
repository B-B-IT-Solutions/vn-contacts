package cz.prm.business.referrals;

import static com.google.common.collect.Lists.newArrayList;
import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;
import static cz.prm.utils.ComponentTestUtils.emptyFilter;
import static cz.prm.utils.ComponentTestUtils.endsWithFilter;
import static cz.prm.utils.ComponentTestUtils.equalsFilter;
import static cz.prm.utils.ComponentTestUtils.notEmptyFilter;
import static cz.prm.utils.ComponentTestUtils.notEqualsFilter;
import static cz.prm.utils.ComponentTestUtils.randomLong;
import static cz.prm.utils.ComponentTestUtils.startsWithFilter;
import static cz.prm.utils.ComponentTestUtils.uuid;
import static cz.prm.utils.ReferralComponentTestUtils.referralDto;
import static cz.prm.utils.ReferralComponentTestUtils.referralsQueryDto;
import static cz.prm.utils.assertions.ReferralComponentTestAssertions.assertReferral;
import static cz.prm.utils.assertions.ReferralComponentTestAssertions.assertReferrals;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.controllers.dto.referral.ReferralDto;
import org.junit.jupiter.api.Test;

public class ReferralComponentTest extends ReferralComponentTestBase {

    @Test
    void getReferralsDataAccess() {
        var queryDto = referralsQueryDto();
        var pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Referrals = createReferrals(USER_1);
        pageDto = user1GetReferrals(queryDto);
        assertReferrals(user1Referrals, pageDto);

        pageDto = user2GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Referrals = createReferrals(USER_2);
        pageDto = user2GetReferrals(queryDto);
        assertReferrals(user2Referrals, pageDto);

        pageDto = user1GetReferrals(queryDto);
        assertReferrals(user1Referrals, pageDto);

        pageDto = user3GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Referrals = createReferrals(USER_3);
        pageDto = user3GetReferrals(queryDto);
        assertReferrals(user3Referrals, pageDto);

        pageDto = user1GetReferrals(queryDto);
        assertReferrals(user1Referrals, pageDto);

        pageDto = user2GetReferrals(queryDto);
        assertReferrals(user2Referrals, pageDto);
    }

    @Test
    void getContactReferralsDataAccess() {
        var queryDto = referralsQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var reminders = createReferrals(USER_1);
        var reminder = reminders.get(0);
        contactId = reminder.getContactId();

        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertReferrals(reminders, pageDto);

        pageDto = user2GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user2Referrals = createReferrals(USER_2);
        reminder = user2Referrals.get(0);
        contactId = reminder.getContactId();

        pageDto = user2GetContactReferrals(contactId, queryDto);
        assertReferrals(user2Referrals, pageDto);

        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user3GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user3Referrals = createReferrals(USER_3);
        reminder = user3Referrals.get(0);
        contactId = reminder.getContactId();

        pageDto = user3GetContactReferrals(contactId, queryDto);
        assertReferrals(user3Referrals, pageDto);

        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        pageDto = user2GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();
    }

    @Test
    void getReferralsPagination() {
        var queryDto = referralsQueryDto();
        var pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        createReferrals(USER_1, 21);
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getContactReferralsPagination() {
        var queryDto = referralsQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isZero();
        assertThat(pageDto.getTotalElements()).isZero();
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).isEmpty();

        var reminders = createReferrals(USER_1, 21);
        var reminder = reminders.get(0);
        contactId = reminder.getContactId();

        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(50);
        assertThat(pageDto.getContent()).hasSize(21);

        var pagination = queryDto.getPagination();
        pagination.setPageSize(5);
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(5);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(5);
        assertThat(pageDto.getContent()).hasSize(5);

        pagination.setPageNumber(1);
        pagination.setPageSize(10);
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(10);

        pagination.setPageNumber(2);
        pagination.setPageSize(10);
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(21);
        assertThat(pageDto.getPageSize()).isEqualTo(10);
        assertThat(pageDto.getContent()).hasSize(1);
    }

    @Test
    void getReferralsSorting() {
        var queryDto = referralsQueryDto();
        var pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        createReferrals(USER_1, 21);
        queryDto = referralsQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetReferrals(queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = referralsQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetReferrals(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetReferrals(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetReferrals(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetReferrals(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(contactId)");
        pageDto = user1GetReferrals(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getContactId));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(contactId)");
        pageDto = user1GetReferrals(queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getContactId).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getContactReferralsSorting() {
        var queryDto = referralsQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var reminders = createReferrals(USER_1, 21);
        var reminder = reminders.get(0);
        contactId = reminder.getContactId();

        queryDto = referralsQueryDto();
        queryDto.setSort(null);
        pageDto = user1GetContactReferrals(contactId, queryDto);
        var actual = pageDto.getContent();
        var expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto = referralsQueryDto();
        queryDto.setSort("asc(lastEditDate)");
        pageDto = user1GetContactReferrals(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getLastEditDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(lastEditDate)");
        pageDto = user1GetContactReferrals(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getLastEditDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(creationDate)");
        pageDto = user1GetContactReferrals(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getCreationDate));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(creationDate)");
        pageDto = user1GetContactReferrals(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getCreationDate).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("asc(contactId)");
        pageDto = user1GetContactReferrals(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getContactId));
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);

        queryDto.setSort("desc(contactId)");
        pageDto = user1GetContactReferrals(contactId, queryDto);
        actual = pageDto.getContent();
        expected = newArrayList(actual);
        sort(expected, comparing(ReferralDto::getContactId).reversed());
        assertThat(actual).hasSize(21).containsExactlyElementsOf(expected);
    }

    @Test
    void getReferralsFilters() {
        var queryDto = referralsQueryDto();
        var pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Referrals = createReferrals(USER_1, 21);
        var userTask1 = user1Referrals.get(0);

        queryDto = referralsQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = referralsQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(userTask1.getName());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(userTask1.getDescription());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(userTask1.getDescription());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = referralsQueryDto();
        filter = queryDto.getFilter();

        filter.setName(userTask1.getName());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(uuid());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter("Title"));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(startsWithFilter(userTask1.getName()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(startsWithFilter("Q"));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter(uuid()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter("End"));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(endsWithFilter(userTask1.getName()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(endsWithFilter("Q"));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter(uuid()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(userTask1.getName()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(equalsFilter("Q"));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(" "));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(uuid()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEqualsFilter(userTask1.getName()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setName(notEqualsFilter("Q"));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(" "));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(emptyFilter());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEmptyFilter());
        pageDto = user1GetReferrals(queryDto);
        assertThat(pageDto.getContent()).hasSize(21);
    }

    @Test
    void getReferral() {
        var reminder = createReferral(USER_1);
        var reminderId = reminder.getReferralId();

        var reminderDto = user1GetReferral(reminderId);
        assertReferral(reminder, reminderDto);
        user2GetReferralExpectNotFound(reminderId);
        user3GetReferralExpectNotFound(reminderId);

        reminder = createReferral(USER_2);
        reminderId = reminder.getReferralId();
        reminderDto = user2GetReferral(reminderId);
        assertReferral(reminder, reminderDto);
        user1GetReferralExpectNotFound(reminderId);
        user3GetReferralExpectNotFound(reminderId);

        reminder = createReferral(USER_3);
        reminderId = reminder.getReferralId();
        reminderDto = user3GetReferral(reminderId);
        assertReferral(reminder, reminderDto);
        user1GetReferralExpectNotFound(reminderId);
        user2GetReferralExpectNotFound(reminderId);
    }

    @Test
    void getContactReferralsFilters() {
        var queryDto = referralsQueryDto();
        var contactId = randomLong();
        var pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        var user1Referrals = createReferrals(USER_1, 21);
        var user1Task = user1Referrals.get(0);
        contactId = user1Task.getContactId();

        queryDto = referralsQueryDto();
        queryDto.setFilter(null);
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        queryDto = referralsQueryDto();
        var filter = queryDto.getFilter();

        filter.setGlobalFilter(user1Task.getName());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(user1Task.getDescription());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(user1Task.getDescription());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setGlobalFilter(uuid());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        queryDto = referralsQueryDto();
        filter = queryDto.getFilter();

        filter.setName(user1Task.getName());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(uuid());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter("Title"));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(startsWithFilter(user1Task.getName()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(startsWithFilter("Q"));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(startsWithFilter(uuid()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter("End"));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(endsWithFilter(user1Task.getName()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(endsWithFilter("Q"));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(endsWithFilter(uuid()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(user1Task.getName()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(1);

        filter.setName(equalsFilter("Q"));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(" "));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(equalsFilter(uuid()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEqualsFilter(user1Task.getName()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(20);

        filter.setName(notEqualsFilter("Q"));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(" "));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(notEqualsFilter(uuid()));
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);

        filter.setName(emptyFilter());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).isEmpty();

        filter.setName(notEmptyFilter());
        pageDto = user1GetContactReferrals(contactId, queryDto);
        assertThat(pageDto.getContent()).hasSize(21);
    }

    @Test
    void createReferral() {
        var contact = createContact(USER_1);
        var toCreateDto = referralDto(contact.getContactId());
        user1CreateReferral(toCreateDto);
        var reminder = getReferralFromDb(toCreateDto);
        var reminderId = reminder.getReferralId();

        var createdDto = user1GetReferral(reminderId);
        assertReferral(reminder, createdDto);
        user2GetReferralExpectNotFound(reminderId);
        user3GetReferralExpectNotFound(reminderId);

        contact = createContact(USER_2);
        toCreateDto = referralDto(contact.getContactId());
        user2CreateReferral(toCreateDto);
        reminder = getReferralFromDb(toCreateDto);
        reminderId = reminder.getReferralId();

        createdDto = user2GetReferral(reminderId);
        assertReferral(reminder, createdDto);
        user1GetReferralExpectNotFound(reminderId);
        user3GetReferralExpectNotFound(reminderId);

        contact = createContact(USER_3);
        toCreateDto = referralDto(contact.getContactId());
        user3CreateReferral(toCreateDto);
        reminder = getReferralFromDb(toCreateDto);
        reminderId = reminder.getReferralId();

        createdDto = user3GetReferral(reminderId);
        assertReferral(reminder, createdDto);
        user1GetReferralExpectNotFound(reminderId);
        user2GetReferralExpectNotFound(reminderId);
    }

    @Test
    void updateReferral() {
        var reminder = createReferral(USER_1);
        var reminderId = reminder.getReferralId();
        var updateDto = user1GetReferral(reminderId);

        updateDto.setDescription(uuid());
        user1UpdateReferral(reminderId, updateDto);
        reminder = getReferralFromDb(updateDto);
        assertReferral(reminder, updateDto);

        user2UpdateReferralExpectNotFound(reminderId, updateDto);
        user3UpdateReferralExpectNotFound(reminderId, updateDto);

        reminder = createReferral(USER_2);
        reminderId = reminder.getReferralId();
        updateDto = user2GetReferral(reminderId);

        updateDto.setDescription(uuid());
        user2UpdateReferral(reminderId, updateDto);
        reminder = getReferralFromDb(updateDto);
        assertReferral(reminder, updateDto);

        user1UpdateReferralExpectNotFound(reminderId, updateDto);
        user3UpdateReferralExpectNotFound(reminderId, updateDto);

        reminder = createReferral(USER_3);
        reminderId = reminder.getReferralId();
        updateDto = user3GetReferral(reminderId);

        updateDto.setDescription(uuid());
        user3UpdateReferral(reminderId, updateDto);
        reminder = getReferralFromDb(updateDto);
        assertReferral(reminder, updateDto);

        user1UpdateReferralExpectNotFound(reminderId, updateDto);
        user2UpdateReferralExpectNotFound(reminderId, updateDto);
    }

    @Test
    void deleteReferral() {
        var reminder = createReferral(USER_1);
        var reminderId = reminder.getReferralId();
        var reminderDto = user1GetReferral(reminderId);
        assertReferral(reminder, reminderDto);

        user2DeleteReferralExpectNotFound(reminderId);
        user3DeleteReferralExpectNotFound(reminderId);
        user1DeleteReferral(reminderId);
        user1GetReferralExpectNotFound(reminderId);

        reminder = createReferral(USER_2);
        reminderId = reminder.getReferralId();
        reminderDto = user2GetReferral(reminderId);
        assertReferral(reminder, reminderDto);

        user1DeleteReferralExpectNotFound(reminderId);
        user3DeleteReferralExpectNotFound(reminderId);
        user2DeleteReferral(reminderId);
        user2GetReferralExpectNotFound(reminderId);

        reminder = createReferral(USER_3);
        reminderId = reminder.getReferralId();
        reminderDto = user3GetReferral(reminderId);
        assertReferral(reminder, reminderDto);

        user1DeleteReferralExpectNotFound(reminderId);
        user2DeleteReferralExpectNotFound(reminderId);
        user3DeleteReferral(reminderId);
        user3GetReferralExpectNotFound(reminderId);
    }
}
