package com.example.kameleon_task.controller;

import com.example.kameleon_task.config.JwtTokenGenerator;
import com.example.kameleon_task.dto.LoginUserDto;
import com.example.kameleon_task.dto.RegUserDto;
import com.example.kameleon_task.entity.SessionUser;
import com.example.kameleon_task.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegUserDto regUserDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid registration data");
        }
        try {
            userService.save(regUserDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body("The user with this email already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDto loginUserDto,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid login data");
        }

        Optional<SessionUser> sessionUser = userService.login(loginUserDto);
        if (sessionUser.isPresent()) {
            SessionUser user = sessionUser.get();
            String token = jwtTokenGenerator.generateToken(user.getId(), user.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email or password is invalid, please try again");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
