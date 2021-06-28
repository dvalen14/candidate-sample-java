package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.UserDto;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

  private final MapperFacade mapperFacade;

  public ResourceMapper(MapperFacade mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  public <T extends Collection<User>> List<UserDto> convertUsers(final T users){
    return users.stream().map(this::convertUser).collect(Collectors.toList());
  }

  public UserDto convertUser(final User user){
    final UserDto dto = mapperFacade.map(user, UserDto.class);

    String name;
    if(user.getMiddleName() != null && !user.getMiddleName().trim().isEmpty()){
      name = String.format("%s %s %s", user.getFirstName(), user.getMiddleName(), user.getLastName());
    } else {
      name = String.format("%s %s", user.getFirstName(), user.getLastName());
    }
    dto.setName(name);
    return dto;
  }
}
