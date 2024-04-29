package cz.prm.domain.contact.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactsFilter {

   private String firstName;

   private String lastName;

   private String email;
}
