package cz.prm.business.contacts;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.services.contact.ContactService;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactComponentTestBase extends ComponentTestBase {

   private static String CONTACTS_URL = "contacts";

   @Autowired
   protected ContactService contactService;

   protected List<ContactDto> user1GetContacts() {
      return getContacts(USER_1);
   }

   protected List<ContactDto> user2GetContacts() {
      return getContacts(USER_2);
   }

   protected List<ContactDto> user3GetContacts() {
      return getContacts(USER_3);
   }

   protected List<ContactDto> getContacts(ComponentTestUser user) {
      var typeRef = new TypeRef<List<ContactDto>>() {
      };
      return getMany(CONTACTS_URL, user, typeRef);
   }
}
