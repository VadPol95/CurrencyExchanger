package com.vadpol.ex.service;

import com.vadpol.ex.dto.UserDto;

import java.util.List;

public interface UserService {
    Long createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getUsers();
}
