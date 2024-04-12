package com.bci.apiusuario.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.bci.apiusuario.model.dto.PhoneDto;
import com.bci.apiusuario.model.dto.UserDto;
import com.bci.apiusuario.model.dto.UserResponseDto;
import com.bci.apiusuario.model.entity.User;
import com.bci.apiusuario.repository.UserRepository;
import com.bci.apiusuario.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveUser_Success() {
        // Arrange
        UserDto userDto = this.createUserDto(this.createPhoneList());
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            return user;
        });
        // Act
        UserResponseDto result = userService.saveUser(userDto);
        // Assert
        assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertNotNull(result.getId());
        assertTrue(result.isIsactive());
        assertNotNull(result.getCreated());
        assertNotNull(result.getModified());
    }

    @Test
    public void saveUser_EmailAlreadyExists_ExceptionThrown() {
        // Arrange
        UserDto userDto = this.createUserDto(this.createPhoneList());
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userDto);
        });
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

    private PhoneDto createPhoneDto() {
        return PhoneDto.builder()
                .citycode("11")
                .countrycode("11")
                .number("13424456")
                .build();
    }
}
