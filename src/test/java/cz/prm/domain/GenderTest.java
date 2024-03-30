package cz.prm.domain;

import static cz.prm.domain.Gender.FEMALE;
import static cz.prm.domain.Gender.MALE;
import static cz.prm.domain.Gender.NONE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GenderTest {

   @Test
   void codes() {
      assertThat(MALE.getCode()).isEqualTo("M");
      assertThat(FEMALE.getCode()).isEqualTo("F");
      assertThat(NONE.getCode()).isEqualTo("N");
   }

}