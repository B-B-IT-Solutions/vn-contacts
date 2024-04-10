package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.UserDto;
import cz.prm.domain.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

   List<UserDto> toUsersDto(List<User> users);

   UserDto toUserDto(User user);
}
