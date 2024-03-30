package cz.prm.utils;

import cz.prm.controllers.mapppers.UserMapper;
import org.mapstruct.factory.Mappers;

public class MapperUtils {

   public static UserMapper getUserMapper() {
      return Mappers.getMapper(UserMapper.class);
   }
}
