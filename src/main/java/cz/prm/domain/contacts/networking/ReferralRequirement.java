package cz.prm.domain.contacts.networking;

import cz.prm.domain.contacts.contact.Contact;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralRequirement {

    private Contact contact;

    public Long getContactId() {
        return contact.getContactId();
    }

    public List<String> getIndustries() {
        return contact.getIndustries();
    }

    public List<String> getSkills() {
        return contact.getSkills();
    }

    public List<String> getProducts() {
        return contact.getProducts();
    }

    public List<String> getTargetMarkets() {
        return contact.getTargetMarkets();
    }
}
