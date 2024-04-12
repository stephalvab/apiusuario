package com.bci.apiusuario.service;

import com.bci.apiusuario.model.dto.UserDto;
import com.bci.apiusuario.model.dto.UserResponseDto;

public interface UserService {
    UserResponseDto saveUser(UserDto userDto);
}
