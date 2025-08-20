package cz.prm.domain.contacts.referral.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReferralsFilter {

    private String globalFilter;

    private String name;

    private String status;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isName() {
        return isNotBlank(name);
    }

    public boolean isStatus() {
        return isNotBlank(status);
    }
}
