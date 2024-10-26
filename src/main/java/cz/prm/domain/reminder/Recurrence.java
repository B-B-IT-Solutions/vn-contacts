package cz.prm.domain.reminder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dmfs.rfc5545.recur.RecurrenceRule;

@Slf4j
@Entity
@Table(name = "REMINDER", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recurrence {

    private static final String DATE_FORMAT_PATTERN = "yyyyMMdd";
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);
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

    @Transient
    private RecurrenceRule recurrenceRule;

    @Transient
    private Instant startDate;

    public Recurrence(String value) {
        this.value = value;
    }

    public boolean hasValidRecurrence() {
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

    public Instant getStartDate() {
        if (isNull(startDate)) {
            var optional = getRecurrencePart(START_DATE_KEY);
            if (optional.isPresent()) {
                try {
                    startDate = DATE_FORMATTER.parse(optional.get()).toInstant();
                } catch (Exception e) {
                    log.warn("StartDate is invalid!", e);
                }
            }
        }
        return startDate;
    }

    public Optional<String> getRecurrencePart(String partKey) {
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
