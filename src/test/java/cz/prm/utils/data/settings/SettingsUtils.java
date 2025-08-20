package cz.prm.utils.data.settings;

import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.ALL;
import static cz.prm.utils.CommonUtils.user;
import static cz.prm.utils.TestUtils.randomLong;
import static cz.prm.utils.TestUtils.uuid;
import static java.time.Instant.now;

import cz.prm.controllers.dto.settings.notifications.NotificationSettingsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ContactNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ReferralNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.TaskNotificationsDto;
import cz.prm.domain.settings.AccountSettings;
import cz.prm.domain.settings.notifications.NotificationSettings;
import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;

public class SettingsUtils {

    public static AccountSettings accountSettings() {
        var settings = new AccountSettings();
        settings.setSettingsId(randomLong());
        settings.setAppLanguage(uuid());
        return settings;
    }

    public static NotificationSettings notificationSettings() {
        var settings = new NotificationSettings();
        settings.setSettingsId(randomLong());
        settings.setGlobal(ALL);
        settings.setContact(contactNotifications());
        settings.setReferral(referralNotifications());
        settings.setTask(taskNotifications());
        settings.setLastEditDate(now());
        settings.setOwner(user());
        return settings;
    }

    public static NotificationSettingsDto notificationSettingsDto() {
        var settings = new NotificationSettingsDto();
        settings.setSettingsId(randomLong());
        settings.setGlobal(ALL);
        settings.setContact(contactNotificationsDto());
        settings.setReferral(referralNotificationsDto());
        settings.setTask(taskNotificationsDto());
        settings.setLastEditDate(now());
        return settings;
    }

    public static ContactNotifications contactNotifications() {
        var settings = new ContactNotifications();
        settings.setStalenessReminder(true);
        return settings;
    }

    public static ContactNotificationsDto contactNotificationsDto() {
        var dto = new ContactNotificationsDto();
        dto.setStalenessReminder(true);
        return dto;
    }

    public static ReferralNotifications referralNotifications() {
        var settings = new ReferralNotifications();
        settings.setFollowupReminder(true);
        settings.setExpiryReminder(true);
        settings.setStalenessReminder(true);
        return settings;
    }

    public static ReferralNotificationsDto referralNotificationsDto() {
        var dto = new ReferralNotificationsDto();
        dto.setFollowupReminder(true);
        dto.setExpiryReminder(true);
        dto.setStalenessReminder(true);
        return dto;
    }

    public static TaskNotifications taskNotifications() {
        var settings = new TaskNotifications();
        settings.setReminders(true);
        settings.setAboutToExpire(true);
        return settings;
    }

    public static TaskNotificationsDto taskNotificationsDto() {
        var dto = new TaskNotificationsDto();
        dto.setReminders(true);
        dto.setAboutToExpire(true);
        return dto;
    }
}
