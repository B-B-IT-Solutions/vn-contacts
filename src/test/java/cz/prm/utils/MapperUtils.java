package cz.prm.utils;

import cz.prm.controllers.mappers.ContactMapper;
import cz.prm.controllers.mappers.NetworkingMapper;
import cz.prm.controllers.mappers.NoteMapper;
import cz.prm.controllers.mappers.ReferralMapper;
import cz.prm.controllers.mappers.SettingsMapper;
import cz.prm.controllers.mappers.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

public class MapperUtils {

    private static ContactMapper contactMapper;
    private static NetworkingMapper networkingMapper;
    private static NoteMapper noteMapper;
    private static ReferralMapper referralMapper;
    private static TaskMapper taskMapper;
    private static SettingsMapper settingsMapper;

    static {
        contactMapper = Mappers.getMapper(ContactMapper.class);
        networkingMapper = Mappers.getMapper(NetworkingMapper.class);
        noteMapper = Mappers.getMapper(NoteMapper.class);
        referralMapper = Mappers.getMapper(ReferralMapper.class);
        taskMapper = Mappers.getMapper(TaskMapper.class);
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

    public static SettingsMapper getSettingsMapper() {
        return settingsMapper;
    }
}
