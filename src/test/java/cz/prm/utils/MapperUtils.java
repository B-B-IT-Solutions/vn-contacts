package cz.prm.utils;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.controllers.mappers.NoteMapper;
import org.mapstruct.factory.Mappers;

public class MapperUtils {

    public static ContactMapper getContactMapper() {
        return Mappers.getMapper(ContactMapper.class);
    }

    public static NoteMapper getNoteMapper() {
        return Mappers.getMapper(NoteMapper.class);
    }
}
