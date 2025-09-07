package com.ride.captain.service.impl;

import com.ride.captain.dto.CaptainRegisterDTO;
import com.ride.captain.dto.LoginDto;

public interface IAuthService {

    String register(CaptainRegisterDTO dto);

    String login(LoginDto dto);
}
