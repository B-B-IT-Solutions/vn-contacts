package cz.prm.domain.reminder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.dmfs.rfc5545.DateTime.now;
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
import org.dmfs.jems2.iterable.While;
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

    @Column(name = "NEXT_OCCURRENCE")
    private DateTime nextOccurrence;

    @Column(name = "LAST_OCCURRENCE")
    private DateTime lastOccurrence;

    @Transient
    private RecurrenceRule recurrenceRule;

    @Transient
    private DateTime startDate;

    public Recurrence(String value) {
        this.value = value;
    }

    public boolean hasActiveRule() {
        return nonNull(getRecurrenceRule());
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
                    startDate = now();
                }
            } else {
                startDate = now();
            }
        }
        return startDate;
    }

    public void resetParsedRule() {
        this.startDate = null;
        this.recurrenceRule = null;
        this.nextOccurrence = null;
    }

    public void resolveNextOccurrence() {
        if (hasActiveRule()) {
            var sd = getStartDate().after(now()) ? getStartDate() : now();
            var occurrences0 = new OfRule(getRecurrenceRule(), sd);
            if (occurrences0.isInfinite()) {
                var it0 = occurrences0.iterator();
                this.nextOccurrence = it0.next().startOfDay();
            } else {
                var occurrences1 = new First<>(2, new OfRule(getRecurrenceRule(), now()));
                var it1 = occurrences1.iterator();
                var next1 = DateTime.now();
                while (it1.hasNext()) {
                    next1 = it1.next().startOfDay();
                }
                var secondFromToday = next1;

                log.warn("secondFromToday - {}", secondFromToday);

                var occurrences2 = new While<>((dt) -> secondFromToday.after(dt), new OfRule(getRecurrenceRule(), getStartDate()));
                var it2 = occurrences2.iterator();

                while (it2.hasNext()) {
                    var next2 = it2.next().startOfDay();
                    this.nextOccurrence = next2.after(now().startOfDay()) ? next2 : null;
                }

                occurrences2.forEach(dt -> log.warn("{}", dt));
            }
        } else {
            this.nextOccurrence = null;
        }
    }

    public boolean isDue() {
        if (hasActiveRule()) {
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
                var kv = part.split(KEY_VALUE_DELIMITER);
                if (kv.length == 2 && kv[0].equals(partKey)) {
                    return Optional.of(kv[1]);
                }
            }
        }
        return Optional.empty();
    }
}
