package com.example.user_service.service;

import com.example.user_service.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> allUsers();
    UserDTO getUser(long id);
}
