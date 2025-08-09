package cz.prm.domain.contact.query;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactsFilter {

    private String globalFilter;

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String status;

    private String source;

    private String labels;

    private String industries;

    public boolean isGlobalFilter() {
        return isNotBlank(globalFilter);
    }

    public boolean isFirstName() {
        return isNotBlank(firstName);
    }

    public boolean isLastName() {
        return isNotBlank(lastName);
    }

    public boolean isCountry() {
        return isNotBlank(country);
    }

    public boolean isCity() {
        return isNotBlank(city);
    }

    public boolean isStatus() {
        return isNotBlank(status);
    }

    public boolean isSource() {
        return isNotBlank(source);
    }

    public boolean isLabels() {
        return isNotBlank(labels);
    }

    public boolean isIndustries() {
        return isNotBlank(industries);
    }
}
