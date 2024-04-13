package cz.prm.business;

import cz.prm.ComponentTestBase;
import cz.prm.controllers.dto.contact.ContactDto;
import io.restassured.common.mapper.TypeRef;
import java.util.List;

public class BusinessComponentTestBase extends ComponentTestBase {

   private static String CONTACTS_URL = "contacts";

   protected List<ContactDto> getContacts() {
      var typeRef = new TypeRef<List<ContactDto>>() {
      };
      return getMany(CONTACTS_URL, typeRef);
   }
}
