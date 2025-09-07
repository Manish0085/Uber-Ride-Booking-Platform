package com.uber.user.service;

import com.uber.user.dto.LoginDto;
import com.uber.user.dto.UserDTO;

public interface IAuthService {

    String register(UserDTO dto);

    String login(LoginDto dto);
}
