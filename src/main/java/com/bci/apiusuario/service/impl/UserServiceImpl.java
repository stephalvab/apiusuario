package com.bci.apiusuario.service.impl;


import com.bci.apiusuario.model.dto.PhoneDto;
import com.bci.apiusuario.model.dto.UserDto;
import com.bci.apiusuario.model.dto.UserResponseDto;
import com.bci.apiusuario.model.entity.Phone;
import com.bci.apiusuario.model.entity.User;
import com.bci.apiusuario.repository.UserRepository;
import com.bci.apiusuario.service.UserService;
import com.bci.apiusuario.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto saveUser(UserDto userDto) {
        log.info("saveUser");
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        return this.builderUserResponse(
                userRepository.save(this.builderUserBd(userDto)));
    }

    private User builderUserBd(UserDto userDto) {
        String uid = UUID.randomUUID().toString();
        return User.builder()
                .userId(uid)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .status(true)
                .phones(this.builderListPhones(userDto.getPhones(), uid))
                .lastLogin(new Date())
                .token(uid)
                .build();
    }

    private List<Phone> builderListPhones(List<PhoneDto> phoneDtoList, String uid) {
        List<Phone> phoneList = new ArrayList<>();
        phoneDtoList.stream().forEach(phone -> {
            phoneList.add(this.builderPhoneBd(phone, uid));
        });
        return phoneList;
    }

    private Phone builderPhoneBd(PhoneDto phoneDto, String uid) {
        return Phone.builder()
                .userId(uid)
                .cityCode(phoneDto.getCitycode())
                .countryCode(phoneDto.getCountrycode())
                .number(phoneDto.getNumber())
                .status(true)
                .build();
    }

    private UserResponseDto builderUserResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .token(user.getToken())
                .isactive(user.isStatus())
                .last_login(Util.convertirDateToString(user.getLastLogin()))
                .created(Util.convertirDateToString(user.getCreatedAt()))
                .modified(Util.convertirDateToString(user.getUpdatedAt()))

                .build();
    }
}
