package cz.prm.domain;

public enum Gender {

   MALE("M"),
   FEMALE("F"),
   NONE("N");

   private final String code;

   Gender(String code) {
      this.code = code;
   }

   public String getCode() {
      return this.code;
   }

}



