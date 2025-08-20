package cz.prm.utils;

import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.controllers.mappers.contacts.ContactMapper;
import cz.prm.controllers.mappers.contacts.NetworkingMapper;
import cz.prm.controllers.mappers.contacts.NoteMapper;
import cz.prm.controllers.mappers.contacts.ReferralMapper;
import cz.prm.controllers.mappers.contacts.TaskMapper;
import cz.prm.controllers.mappers.customizations.CustomizationsMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

public class MapperUtils {

    private static ContactMapper contactMapper;
    private static NetworkingMapper networkingMapper;
    private static NoteMapper noteMapper;
    private static ReferralMapper referralMapper;
    private static TaskMapper taskMapper;
    private static CustomizationsMapper customizationsMapper;
    private static SettingsMapper settingsMapper;

    static {
        contactMapper = Mappers.getMapper(ContactMapper.class);
        networkingMapper = Mappers.getMapper(NetworkingMapper.class);
        noteMapper = Mappers.getMapper(NoteMapper.class);
        referralMapper = Mappers.getMapper(ReferralMapper.class);
        taskMapper = Mappers.getMapper(TaskMapper.class);
        customizationsMapper = Mappers.getMapper(CustomizationsMapper.class);
        settingsMapper = Mappers.getMapper(SettingsMapper.class);

        ReflectionTestUtils.setField(networkingMapper, "contactMapper", contactMapper);
    }

    public static ContactMapper getContactMapper() {
        return contactMapper;
    }

    public static NetworkingMapper getNetworkingMapper() {
        return networkingMapper;
    }

    public static NoteMapper getNoteMapper() {
        return noteMapper;
    }

    public static ReferralMapper getReferralMapper() {
        return referralMapper;
    }

    public static TaskMapper getTaskMapper() {
        return taskMapper;
    }

    public static CustomizationsMapper getCustomizationsMapper() {
        return customizationsMapper;
    }

    public static SettingsMapper getSettingsMapper() {
        return settingsMapper;
    }
}
