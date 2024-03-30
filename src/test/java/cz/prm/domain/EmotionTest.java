package cz.prm.domain;

import static cz.prm.domain.Emotion.NEGATIVE;
import static cz.prm.domain.Emotion.NEUTRAL;
import static cz.prm.domain.Emotion.POSITIVE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EmotionTest {

   @Test
   void codes() {
      assertThat(POSITIVE.getCode()).isEqualTo("positive");
      assertThat(NEUTRAL.getCode()).isEqualTo("neutral");
      assertThat(NEGATIVE.getCode()).isEqualTo("negative");
   }
}