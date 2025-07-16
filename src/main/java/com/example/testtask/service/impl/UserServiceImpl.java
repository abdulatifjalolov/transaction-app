package com.example.testtask.service.impl;

import com.example.testtask.dto.UserDTO;
import com.example.testtask.exception.UserAlreadyExistsException;
import com.example.testtask.exception.UserNotFoundException;
import com.example.testtask.model.User;
import com.example.testtask.model.enums.Role;
import com.example.testtask.repository.UserRepository;
import com.example.testtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(UserDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Optional.ofNullable(dto.getRoles()).orElse(Set.of(Role.USER)))
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUsername(dto.getUsername());
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setRoles(dto.getRoles());

        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public Page<User> getAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }
}

