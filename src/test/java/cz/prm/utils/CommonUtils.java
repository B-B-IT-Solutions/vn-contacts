package cz.prm.utils;

import java.util.Random;
import java.util.UUID;

public class CommonUtils {

   private static Random random = new Random();

   public static Long randomLong() {
      return random.nextLong();
   }

   public static String uuid() {
      return UUID.randomUUID().toString();
   }

}
