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

   private String email;

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

   public boolean isEmail() {
      return isNotBlank(email);
   }
}
