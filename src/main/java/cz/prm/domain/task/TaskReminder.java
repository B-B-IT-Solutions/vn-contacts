package cz.prm.domain.task;

import static com.google.common.collect.Lists.newArrayList;
import static jakarta.persistence.CascadeType.ALL;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import cz.prm.domain.recurrence.Recurrence;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK_REMINDER", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_REMINDER_SEQ")
    @SequenceGenerator(name = "TASK_REMINDER_SEQ", sequenceName = "TASK_REMINDER_SEQ", allocationSize = 1)
    @Column(name = "TASK_REMINDER_ID")
    private Long taskReminderId;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "RECURRENCE_ID")
    private List<Recurrence> reminderRules;

    public List<String> getReminderRuleValues() {
        if (isNotEmpty(reminderRules)) {
            return reminderRules.stream().map(r -> r.getValue()).collect(toList());
        }
        return newArrayList();
    }
}
