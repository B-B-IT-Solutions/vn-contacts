package cz.prm.domain.contact.query;

import static cz.prm.utils.TestUtils.uuid;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ContactsFilterTest {

   @Test
   void isFirstName() {
      var filter = new ContactsFilter();
      assertThat(filter.isFirstName()).isFalse();
      filter.setFirstName(null);
      assertThat(filter.isFirstName()).isFalse();
      filter.setFirstName("");
      assertThat(filter.isFirstName()).isFalse();
      filter.setFirstName(" ");
      assertThat(filter.isFirstName()).isFalse();
      filter.setFirstName(uuid());
      assertThat(filter.isFirstName()).isTrue();
   }

   @Test
   void isLastName() {
      var filter = new ContactsFilter();
      assertThat(filter.isLastName()).isFalse();
      filter.setLastName(null);
      assertThat(filter.isLastName()).isFalse();
      filter.setLastName("");
      assertThat(filter.isLastName()).isFalse();
      filter.setLastName(" ");
      assertThat(filter.isLastName()).isFalse();
      filter.setLastName(uuid());
      assertThat(filter.isLastName()).isTrue();
   }

   @Test
   void isEmail() {
      var filter = new ContactsFilter();
      assertThat(filter.isEmail()).isFalse();
      filter.setEmail(null);
      assertThat(filter.isEmail()).isFalse();
      filter.setEmail("");
      assertThat(filter.isEmail()).isFalse();
      filter.setEmail(" ");
      assertThat(filter.isEmail()).isFalse();
      filter.setEmail(uuid());
      assertThat(filter.isEmail()).isTrue();
   }
}