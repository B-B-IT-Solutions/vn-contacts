package cz.prm.domain.reminder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dmfs.rfc5545.recur.RecurrenceRule;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recurrence {

    private static final String START_DATE_PATTERN = "yyyyMMdd";
    private static final String RECURRENCE_PARTS_DELIMITER = "\\n";
    private static final String START_DATE_KEY = "DTSTART";
    private static final String RRULE_KEY = "RRULE";

    private String recurrence;

    private RecurrenceRule recurrenceRule;

    private Instant startDate;

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
                    var formatter = new SimpleDateFormat(START_DATE_PATTERN);
                    startDate = formatter.parse(optional.get()).toInstant();
                } catch (Exception e) {
                    log.warn("StartDate is invalid!", e);
                }
            }
        }
        return startDate;
    }

    public Optional<String> getRecurrencePart(String partKey) {
        if (isNotBlank(recurrence)) {
            var parts = recurrence.split(RECURRENCE_PARTS_DELIMITER);
            var optional = of(parts).filter(p -> p.startsWith(partKey)).findFirst();
            if (optional.isPresent()) {
                var part = optional.get();
                var vk = part.split(":");
                if (vk.length == 2 && vk[0].equals(partKey)) {
                    return Optional.of(vk[1]);
                }
            }
        }
        return Optional.empty();
    }
}
