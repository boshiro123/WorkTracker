package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/v2/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public boolean registerNewUser(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
       return userService.save(userDto);
    }

    @GetMapping("/logIn")
    @ResponseStatus(HttpStatus.OK)
    public UserDto logIn(@RequestParam("email") String email,
                         @RequestParam("password") String password) throws NoSuchAlgorithmException {
        System.out.println(email);
        System.out.println(password);;
        return userService.logIn(email, password);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getInfo(@PathVariable Long id) {
        return userService.fingById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody @Valid UserDto user) throws NoSuchAlgorithmException {
        userService.updateUser(user);
    }
    @PutMapping("/updateMessage")
    @ResponseStatus(HttpStatus.OK)
    public void updateMessage(@RequestBody @Valid UserDto user) throws NoSuchAlgorithmException {
        userService.updateMessage(user);
    }
    @PutMapping("/block")
    @ResponseStatus(HttpStatus.OK)
    public void Block(@RequestBody @Valid UserDto user) {
        userService.Block(user);
    }


}
