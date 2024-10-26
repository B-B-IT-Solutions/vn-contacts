package cz.prm.domain.reminder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.dmfs.rfc5545.DateTime.parse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dmfs.jems2.iterable.First;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recurrenceset.OfRule;

@Slf4j
@Entity
@Table(name = "RECURRENCE", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recurrence {

    private static final String PARTS_DELIMITER = "\n";
    private static final String KEY_VALUE_DELIMITER = ":";
    private static final String START_DATE_KEY = "DTSTART";
    private static final String RRULE_KEY = "RRULE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECURRENCE_SEQ")
    @SequenceGenerator(name = "RECURRENCE_SEQ", sequenceName = "RECURRENCE_SEQ", allocationSize = 1)
    @Column(name = "RECURRENCE_ID")
    private Long recurrenceId;

    @Column(name = "VALUE")
    private String value;

//    @Column(name = "VALUE")
//    private DateTime nextOccurrence;

    @Transient
    private RecurrenceRule recurrenceRule;

    @Transient
    private DateTime startDate;

    public Recurrence(String value) {
        this.value = value;
    }

    public boolean hasActiveRecurrence() {
        return nonNull(getRecurrenceRule()) && nonNull(getStartDate());
    }

    public RecurrenceRule getRecurrenceRule() {
        if (isNull(recurrenceRule)) {
            var optional = getRecurrencePart(RRULE_KEY);
            if (optional.isPresent()) {
                try {
                    recurrenceRule = new RecurrenceRule(optional.get());
                } catch (Exception e) {
                    log.warn("RecurrenceRule is invalid!", e);
                }
            }
        }
        return recurrenceRule;
    }

    public DateTime getStartDate() {
        if (isNull(startDate)) {
            var optional = getRecurrencePart(START_DATE_KEY);
            if (optional.isPresent()) {
                try {
                    startDate = parse(optional.get());
                } catch (Exception e) {
                    log.warn("StartDate is invalid!", e);
                }
            }
        }
        return startDate;
    }

    public boolean isDue() {
        if (hasActiveRecurrence()) {
            var occurrences = new First<>(100, new OfRule(getRecurrenceRule(), getStartDate()));
            occurrences.forEach(dt -> {
                log.info("occurrence: {}", dt);
            });
        }
        return false;
    }

    private Optional<String> getRecurrencePart(String partKey) {
        if (isNotBlank(value)) {
            var parts = value.split(PARTS_DELIMITER);
            var optional = of(parts).filter(p -> p.startsWith(partKey)).findFirst();
            if (optional.isPresent()) {
                var part = optional.get();
                var vk = part.split(KEY_VALUE_DELIMITER);
                if (vk.length == 2 && vk[0].equals(partKey)) {
                    return Optional.of(vk[1]);
                }
            }
        }
        return Optional.empty();
    }
}
