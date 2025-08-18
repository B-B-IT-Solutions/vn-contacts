package cz.prm.controllers.dto.settings.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.prm.controllers.dto.settings.notifications.dials.ContactNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.ReferralNotificationsDto;
import cz.prm.controllers.dto.settings.notifications.dials.TaskNotificationsDto;
import cz.prm.domain.settings.notifications.dials.GlobalNotifications;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSettingsDto {

    @JsonProperty("settingsId")
    private Long settingsId;

    @JsonProperty("global")
    private GlobalNotifications global;

    @JsonProperty("contact")
    private ContactNotificationsDto contact;

    @JsonProperty("referral")
    private ReferralNotificationsDto referral;

    @JsonProperty("task")
    private TaskNotificationsDto task;

    @JsonProperty("lastEditDate")
    private Instant lastEditDate;
}
