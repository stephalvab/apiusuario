package com.bci.apiusuario.controller;

import com.bci.apiusuario.exception.MessageException;
import com.bci.apiusuario.model.dto.UserDto;
import com.bci.apiusuario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name="Usuarios")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Creacion de un usuario")
    @Validated
    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageException.Message(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage()));
        }
    }
}
