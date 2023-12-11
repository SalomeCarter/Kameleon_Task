package com.example.kameleon_task.service;

import com.example.kameleon_task.dto.LoginUserDto;
import com.example.kameleon_task.dto.RegUserDto;
import com.example.kameleon_task.entity.SessionUser;
import com.example.kameleon_task.entity.User;
import com.example.kameleon_task.mapper.RegUserDtoMapper;
import com.example.kameleon_task.mapper.UserMapper;
import com.example.kameleon_task.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(RegUserDto regUserDto) {
        User user = RegUserDtoMapper.regUserToUser(regUserDto);
        user.setPassword(regUserDto.getPassword());
        userRepository.save(user);
    }

    public Optional<SessionUser> login(LoginUserDto loginUserDto) {
        Optional<User> user = userRepository.findByEmail(loginUserDto.getEmail());
        if (user.isPresent()) {
            User currentUser = user.get();
            if (currentUser.getPassword().equals(loginUserDto.getPassword())) {
                SessionUser sessionUser = UserMapper.userToSessionUser(currentUser);
                return Optional.of(sessionUser);
            }
        }
        return Optional.empty();
    }
}

