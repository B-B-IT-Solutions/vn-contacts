package cz.prm.utils;

import cz.prm.controllers.mappers.ContactMapper;
import org.mapstruct.factory.Mappers;

public class MapperUtils {

    public static ContactMapper getContactMapper() {
        return Mappers.getMapper(ContactMapper.class);
    }
}
