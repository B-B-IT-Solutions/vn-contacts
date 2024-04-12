package cz.prm.controllers.mappers;

import cz.prm.controllers.dto.user.UserDto;
import cz.prm.domain.user.User;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

   List<UserDto> toUsersDto(List<User> users);

   UserDto toUserDto(User user);
}
