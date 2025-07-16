package com.example.testtask.service;

import com.example.testtask.dto.UserDTO;
import com.example.testtask.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User create(UserDTO dto);
    User update(Long id, UserDTO dto);
    User get(Long id);
    Page<User> getAll(int page, int size);
}
