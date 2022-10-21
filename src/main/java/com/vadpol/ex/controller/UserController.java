package com.vadpol.ex.controller;

import com.vadpol.ex.dto.UserDto;
import com.vadpol.ex.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public Long createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }
    @GetMapping("user/{id}")
    public UserDto getUserById(long id){
        return userService.getUserById(id);
    }

    @GetMapping("user")
    public List<UserDto> getUsers(){
        return userService.getUsers();
    }
}
