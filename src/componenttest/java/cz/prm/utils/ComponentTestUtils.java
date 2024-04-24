package cz.prm.utils;

import static java.lang.Math.abs;

import java.util.Random;
import java.util.UUID;

public class ComponentTestUtils {

   private static Random random = new Random();

   public static Long randomLong() {
      return abs(random.nextLong());
   }

   public static String uuid() {
      return UUID.randomUUID().toString();
   }

}
