package com.parfenov.kindleVocabExport.mapper;

import com.parfenov.kindleVocabExport.dto.UserDTO;
import com.parfenov.kindleVocabExport.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseDBMapper<User, UserDTO>{
  @Override
  UserDTO toDto(User customer);

  @Override
  User toEntity(UserDTO customerDTO);
}
