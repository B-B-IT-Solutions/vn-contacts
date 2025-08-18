package cz.prm.controllers.dto.settings.notifications.dials;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralNotificationsDto {

    @JsonProperty("followupReminder")
    private boolean followupReminder;

    @JsonProperty("expiryReminder")
    private boolean expiryReminder;

    @JsonProperty("stalenessReminder")
    private boolean stalenessReminder;
}
