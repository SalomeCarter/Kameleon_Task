package com.example.kameleon_task.service;

import com.example.kameleon_task.dto.LoginUserDto;
import com.example.kameleon_task.dto.RegUserDto;
import com.example.kameleon_task.entity.SessionUser;
import com.example.kameleon_task.entity.User;
import com.example.kameleon_task.mapper.RegUserDtoMapper;
import com.example.kameleon_task.mapper.UserMapper;
import com.example.kameleon_task.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(RegUserDto regUserDto) {
        User user = RegUserDtoMapper.regUserToUser(regUserDto);
        userRepository.save(user);
    }

    public Optional<SessionUser> login(LoginUserDto loginUserDto) {
        Optional<User> userOptional = userRepository.findByEmail(loginUserDto.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (loginUserDto.getPassword().equals(user.getPassword())) {
                SessionUser sessionUser = UserMapper.userToSessionUser(user);
                return Optional.of(sessionUser);
            }
        }

        return Optional.empty();
    }
}
