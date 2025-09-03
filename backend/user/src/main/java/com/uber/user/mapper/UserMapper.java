package com.uber.user.mapper;

import com.uber.user.dto.UserDTO;
import com.uber.user.entity.User;

public class UserMapper {

    public User mapToUser(UserDTO userDTO, User user){
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }


    public UserDTO mapToUser(User user, UserDTO userDTO){
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
