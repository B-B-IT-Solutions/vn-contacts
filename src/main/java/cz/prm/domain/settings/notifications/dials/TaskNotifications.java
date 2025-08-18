package cz.prm.domain.settings.notifications.dials;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskNotifications {

    @Column(name = "TASK_REMINDERS")
    private boolean reminders;

    @Column(name = "TASK_ABOUT_TO_EXPIRE")
    private boolean aboutToExpire;
}
