package com.uber.user.service;

import com.uber.user.dto.LoginDto;
import com.uber.user.dto.UserDTO;

public interface IAuthService {

    public String register(UserDTO dto);

    public String login(LoginDto dto);
}
