package cz.prm.domain.reminder;

import static jakarta.persistence.CascadeType.ALL;
import static java.util.Objects.nonNull;

import cz.prm.domain.recurrence.Recurrence;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REMINDER", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REMINDER_SEQ")
    @SequenceGenerator(name = "REMINDER_SEQ", sequenceName = "REMINDER_SEQ", allocationSize = 1)
    @Column(name = "REMINDER_ID")
    private Long reminderId;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "RECURRENCE_ID")
    private Recurrence recurrence;

    public boolean hasActiveRecurrence() {
        if (nonNull(recurrence)) {
            return recurrence.hasActiveRule();
        }
        return false;
    }
}
