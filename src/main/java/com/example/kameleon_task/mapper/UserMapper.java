package com.example.kameleon_task.mapper;

import com.example.kameleon_task.entity.SessionUser;
import com.example.kameleon_task.entity.User;

public class UserMapper {
    public static SessionUser userToSessionUser(User user){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(user.getId());
        sessionUser.setName(user.getName());
        sessionUser.setEmail(user.getEmail());
        return sessionUser;
    }
}
