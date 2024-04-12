package com.bci.apiusuario.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bci.apiusuario.model.dto.PhoneDto;
import com.bci.apiusuario.model.dto.UserDto;
import com.bci.apiusuario.model.dto.UserResponseDto;
import com.bci.apiusuario.model.entity.User;
import com.bci.apiusuario.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void createUser_Success() throws Exception {
        // Arrange

        UserDto userDto = this.createUserDto(this.createPhoneList());

        UserResponseDto savedUser = this.createUserResponseDto();

        when(userService.saveUser(any(UserDto.class))).thenReturn(savedUser);

        // Act & Assert
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void createUser_Exception() throws Exception {
        // Arrange
        UserDto userDto = this.createUserDto(this.createPhoneList());
        String errorMessage = "Error message from service";

        when(userService.saveUser(any(UserDto.class))).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    public void createUser_ValidationException() throws Exception {
        // Arrange
        UserDto userDto = this.createUserDto(null);
        String errorMessage = "Error message from service";

        when(userService.saveUser(any(UserDto.class))).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    private UserDto createUserDto(List<PhoneDto> phoneDtoList) {
        return UserDto.builder()
                .name("Stephanie Alva")
                .email("steph.alva@example.com")
                .password("aaaa")
                .phones(phoneDtoList)
                .build();
    }

    private List<PhoneDto> createPhoneList () {
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        phoneDtoList.add(this.createPhoneDto());
        return phoneDtoList;
    }

    private UserResponseDto createUserResponseDto() {
        return UserResponseDto.builder()
                .id("1")
                .name("Stephanie Alva")
                .email("steph.alva@example.com")
                .build();
    }

    private PhoneDto createPhoneDto() {
        return PhoneDto.builder()
                .citycode("11")
                .countrycode("11")
                .number("13424456")
                .build();
    }
}
