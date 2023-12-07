package com.example.kameleon_task.mapper;

import com.example.kameleon_task.dto.RegUserDto;
import com.example.kameleon_task.entity.User;

public class RegUserDtoMapper {
    public static User regUserToUser(RegUserDto regUserDto){
        User user = new User();
        user.setName(regUserDto.getName());
        user.setEmail(regUserDto.getEmail());
        user.setPassword(regUserDto.getPassword());
        return user;
    }
}
