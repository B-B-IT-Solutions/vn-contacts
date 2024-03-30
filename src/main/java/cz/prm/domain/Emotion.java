package cz.prm.domain;

public enum Emotion {

   POSITIVE("positive"),
   NEUTRAL("neutral"),
   NEGATIVE("negative");

   private final String code;

   Emotion(String code) {
      this.code = code;
   }

   public String getCode() {
      return this.code;
   }
}


