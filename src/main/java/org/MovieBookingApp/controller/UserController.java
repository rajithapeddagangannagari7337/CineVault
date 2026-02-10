package org.MovieBookingApp.controller;


import jakarta.validation.Valid;
import org.MovieBookingApp.dto.requestDto.UserRequestDto;
import org.MovieBookingApp.dto.responseDto.UserResponseDto;
import org.MovieBookingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userService.addUser(userRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email){
        UserResponseDto response = userService.getUserByEmail(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}