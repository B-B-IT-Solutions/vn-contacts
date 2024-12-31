package cz.prm.domain.contact.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactsFilter {

    private String firstName;

    private String middleName;

    private String lastName;

    private String nickName;

    private String labels;

    private String industries;

    public boolean isFirstName() {
        return isNotBlank(firstName);
    }

    public boolean isMiddleName() {
        return isNotBlank(middleName);
    }

    public boolean isLastName() {
        return isNotBlank(lastName);
    }

    public boolean isNickName() {
        return isNotBlank(nickName);
    }

    public boolean isLabels() {
        return isNotBlank(labels);
    }

    public boolean isIndustries() {
        return isNotBlank(industries);
    }
}
