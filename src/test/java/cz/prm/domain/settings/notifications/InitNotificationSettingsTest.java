package cz.prm.domain.settings.notifications;

import static cz.prm.domain.settings.notifications.InitNotificationSettings.iniNotificationSettings;
import static cz.prm.domain.settings.notifications.InitNotificationSettings.initContactNotifications;
import static cz.prm.domain.settings.notifications.InitNotificationSettings.initReferralNotifications;
import static cz.prm.domain.settings.notifications.InitNotificationSettings.initTaskNotifications;
import static cz.prm.domain.settings.notifications.dials.GlobalNotifications.ALL;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.domain.settings.notifications.dials.ContactNotifications;
import cz.prm.domain.settings.notifications.dials.ReferralNotifications;
import cz.prm.domain.settings.notifications.dials.TaskNotifications;
import org.junit.jupiter.api.Test;

class InitNotificationSettingsTest {

    @Test
    void iniNotificationSettingsTest() {
        var ns = iniNotificationSettings();
        assertThat(ns.getGlobal()).isEqualTo(ALL);
        assertInitNotifications(ns.getContact());
        assertInitNotifications(ns.getReferral());
        assertInitNotifications(ns.getTask());
    }

    @Test
    void initContactNotificationsTest() {
        var cn = initContactNotifications();
        assertInitNotifications(cn);
    }

    @Test
    void initReferralNotificationsTest() {
        var rn = initReferralNotifications();
        assertInitNotifications(rn);
    }

    @Test
    void initTaskNotificationsTest() {
        var tn = initTaskNotifications();
        assertInitNotifications(tn);
    }

    private void assertInitNotifications(ContactNotifications cn) {
        assertThat(cn.isStalenessReminder()).isTrue();
    }

    private void assertInitNotifications(ReferralNotifications rn) {
        assertThat(rn.isFollowupReminder()).isTrue();
        assertThat(rn.isExpiryReminder()).isTrue();
        assertThat(rn.isStalenessReminder()).isTrue();
    }

    private void assertInitNotifications(TaskNotifications tn) {
        assertThat(tn.isReminders()).isTrue();
        assertThat(tn.isAboutToExpire()).isTrue();
    }
}