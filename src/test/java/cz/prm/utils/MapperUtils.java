package cz.prm.utils;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.controllers.mappers.NoteMapper;
import cz.prm.controllers.mappers.ReminderMapper;
import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.controllers.mappers.TaskMapper;
import org.mapstruct.factory.Mappers;

public class MapperUtils {

    public static ContactMapper getContactMapper() {
        return Mappers.getMapper(ContactMapper.class);
    }

    public static NoteMapper getNoteMapper() {
        return Mappers.getMapper(NoteMapper.class);
    }

    public static ReminderMapper getReminderMapper() {
        return Mappers.getMapper(ReminderMapper.class);
    }

    public static TaskMapper getTaskMapper() {
        return Mappers.getMapper(TaskMapper.class);
    }

    public static SettingsMapper getSettingsMapper() {
        return Mappers.getMapper(SettingsMapper.class);
    }
}
