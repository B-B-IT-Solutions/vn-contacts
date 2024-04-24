package cz.prm.repositories.contact;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import cz.prm.security.SecurityContextUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class ContactPredicatesTest {

   private ContactPredicates predicates;

   @BeforeEach
   void setUp() {
      predicates = new ContactPredicates();
   }

   @Test
   void contacts() {
      try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
         var username = uuid();
         context.when(() -> SecurityContextUtils.getUsername()).thenReturn(username);
         var query = predicates.contacts();
         var expectedString = format("contact.owner = %s", username);
         assertThat(query).hasToString(expectedString);
      }
   }

   @Test
   void byContactId() {
      try (MockedStatic<SecurityContextUtils> context = Mockito.mockStatic(SecurityContextUtils.class)) {
         var username = uuid();
         context.when(() -> SecurityContextUtils.getUsername()).thenReturn(username);
         var query = predicates.byContactId(11L);
         var expectedString = format("contact.owner = %s && contact.contactId = 11", username);
         assertThat(query).hasToString(expectedString);
      }
   }

}