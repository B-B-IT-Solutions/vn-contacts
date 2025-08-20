package cz.prm.domain.contacts.task.query;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksFilter {

    private String globalFilter;

    private Long contactId;

    private Long referralId;

    private String name;

    private String status;

    private String endDate;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isContactId() {
        return nonNull(contactId);
    }

    public boolean isReferralId() {
        return nonNull(referralId);
    }

    public boolean isName() {
        return isNotBlank(name);
    }

    public boolean isStatus() {
        return isNotBlank(status);
    }

    public boolean isEndDate() {
        return isNotBlank(endDate);
    }
}
