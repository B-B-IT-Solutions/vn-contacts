package cz.prm.business.customizations;

import static cz.prm.utils.ComponentTestUser.USER_1;
import static cz.prm.utils.ComponentTestUser.USER_2;
import static cz.prm.utils.ComponentTestUser.USER_3;

import cz.prm.business.BusinessComponentTestBase;
import cz.prm.controllers.dto.customizations.contact.ContactCustomizationsDto;
import cz.prm.controllers.dto.customizations.note.NoteCustomizationsDto;
import cz.prm.utils.ComponentTestUser;
import io.restassured.common.mapper.TypeRef;

public class CustomizationsComponentTestBase extends BusinessComponentTestBase {

    protected static String CUSTOMIZATIONS_BASE_URL = "settings";
    protected static String CONTACT_CUSTOMIZATIONS_URL = CUSTOMIZATIONS_BASE_URL + "/contact";
    protected static String NOTE_CUSTOMIZATIONS_URL = CUSTOMIZATIONS_BASE_URL + "/note";

    protected ContactCustomizationsDto user1GetContactCustomizations() {
        return getContactCustomizations(USER_1);
    }

    protected ContactCustomizationsDto user2GetContactCustomizations() {
        return getContactCustomizations(USER_2);
    }

    protected ContactCustomizationsDto user3GetContactCustomizations() {
        return getContactCustomizations(USER_3);
    }

    protected NoteCustomizationsDto user1GetNoteCustomizations() {
        return getNoteCustomizations(USER_1);
    }

    protected NoteCustomizationsDto user2GetNoteCustomizations() {
        return getNoteCustomizations(USER_2);
    }

    protected NoteCustomizationsDto user3GetNoteCustomizations() {
        return getNoteCustomizations(USER_3);
    }

    protected void user1UpdateContactCustomizations(ContactCustomizationsDto dto) {
        updateContactCustomizations(dto, USER_1);
    }

    protected void user2UpdateContactCustomizations(ContactCustomizationsDto dto) {
        updateContactCustomizations(dto, USER_2);
    }

    protected void user3UpdateContactCustomizations(ContactCustomizationsDto dto) {
        updateContactCustomizations(dto, USER_3);
    }

    protected void user1UpdateNoteCustomizations(NoteCustomizationsDto dto) {
        updateNoteCustomizations(dto, USER_1);
    }

    protected void user2UpdateNoteCustomizations(NoteCustomizationsDto dto) {
        updateNoteCustomizations(dto, USER_2);
    }

    protected void user3UpdateNoteCustomizations(NoteCustomizationsDto dto) {
        updateNoteCustomizations(dto, USER_3);
    }

    protected ContactCustomizationsDto getContactCustomizations(ComponentTestUser user) {
        var typeRef = new TypeRef<ContactCustomizationsDto>() {
        };
        return getOne(CONTACT_CUSTOMIZATIONS_URL, user, typeRef);
    }

    protected NoteCustomizationsDto getNoteCustomizations(ComponentTestUser user) {
        var typeRef = new TypeRef<NoteCustomizationsDto>() {
        };
        return getOne(NOTE_CUSTOMIZATIONS_URL, user, typeRef);
    }

    protected void updateContactCustomizations(ContactCustomizationsDto dto, ComponentTestUser user) {
        put(CONTACT_CUSTOMIZATIONS_URL, user, dto);
    }

    protected void updateNoteCustomizations(NoteCustomizationsDto dto, ComponentTestUser user) {
        put(NOTE_CUSTOMIZATIONS_URL, user, dto);
    }
}
