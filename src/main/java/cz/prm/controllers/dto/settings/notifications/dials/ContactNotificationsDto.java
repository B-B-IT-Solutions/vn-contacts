package cz.prm.controllers.dto.settings.notifications.dials;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactNotificationsDto {

    @JsonProperty("stalenessReminder")
    private boolean stalenessReminder;
}
