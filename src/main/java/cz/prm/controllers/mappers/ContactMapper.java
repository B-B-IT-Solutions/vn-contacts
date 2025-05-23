package cz.prm.controllers.mappers;

import static cz.prm.domain.contact.query.ContactsQuery.DEFAULT_CONTACTS_SORT;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import cz.prm.controllers.dto.common.PageDto;
import cz.prm.controllers.dto.contact.AboutDto;
import cz.prm.controllers.dto.contact.ContactDto;
import cz.prm.controllers.dto.contact.FirstInteractionDto;
import cz.prm.controllers.dto.contact.query.ContactsFilterDto;
import cz.prm.controllers.dto.contact.query.ContactsQueryDto;
import cz.prm.domain.common.query.Page;
import cz.prm.domain.common.query.Pagination;
import cz.prm.domain.contact.About;
import cz.prm.domain.contact.Contact;
import cz.prm.domain.contact.query.ContactsFilter;
import cz.prm.domain.contact.query.ContactsQuery;
import java.util.ArrayList;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    PageDto<ContactDto> toPageDto(Page<Contact> contacts);

    ContactDto toContactDto(Contact contact);

    @Mapping(target = "owner", ignore = true)
    Contact toContact(ContactDto dto);

    AboutDto toAboutDto(About about);

    @Mapping(target = "owner", ignore = true)
    About toAbout(AboutDto dto);

    ContactsQuery toContactsQuery(ContactsQueryDto dto);

    ContactsFilter toContactsFilter(ContactsFilterDto dto);

    default ContactsQuery toNullSafeContactsQuery(ContactsQueryDto dto) {
        if (isNull(dto)) {
            return new ContactsQuery();
        }
        return toContactsQuery(dto);
    }

    @AfterMapping
    default void afterAboutDto(About source, @MappingTarget AboutDto target) {
        if (isNull(target.getIdealClients())) {
            target.setIdealClients(new ArrayList<>());
        }
        if (isNull(target.getFirstInteraction())) {
            target.setFirstInteraction(new FirstInteractionDto());
        }
    }

    @AfterMapping
    default void afterContactsQuery(ContactsQueryDto source, @MappingTarget ContactsQuery target) {
        if (isNull(target.getPagination())) {
            target.setPagination(new Pagination());
        }
        if (isNull(target.getFilter())) {
            target.setFilter(new ContactsFilter());
        }
        if (isBlank(target.getSort())) {
            target.setSort(DEFAULT_CONTACTS_SORT);
        }
    }
}
