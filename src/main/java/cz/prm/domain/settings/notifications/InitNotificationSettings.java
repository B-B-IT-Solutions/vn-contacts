package cz.prm.domain.settings.notifications;

import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.ALL;

import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;

public class InitNotificationSettings {

    public static NotificationSettings iniNotificationSettings() {
        var ns = new NotificationSettings();
        ns.setGlobal(ALL);
        ns.setContact(initContactNotifications());
        ns.setReferral(initReferralNotifications());
        ns.setTask(initTaskNotifications());
        return ns;
    }

    public static ContactNotifications initContactNotifications() {
        var cn = new ContactNotifications();
        cn.setStalenessReminder(true);
        return cn;
    }

    public static ReferralNotifications initReferralNotifications() {
        var rn = new ReferralNotifications();
        rn.setFollowupReminder(true);
        rn.setExpiryReminder(true);
        rn.setStalenessReminder(true);
        return rn;
    }

    public static TaskNotifications initTaskNotifications() {
        var rn = new TaskNotifications();
        rn.setReminders(true);
        rn.setAboutToExpire(true);
        return rn;
    }
}
