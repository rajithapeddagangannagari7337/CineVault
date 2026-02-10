package org.MovieBookingApp.service.serviceImpl;

import org.MovieBookingApp.dto.requestDto.UserRequestDto;
import org.MovieBookingApp.dto.responseDto.UserResponseDto;
import org.MovieBookingApp.entities.Users;
import org.MovieBookingApp.repository.UserRepository;
import org.MovieBookingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        // Create Entity from DTO
        Users user = new Users();
        user.setUserName(userRequestDto.getName());
        user.setUserEmail(userRequestDto.getEmail());
        user.setUserPhone(userRequestDto.getPhone());
        user.setPassword(userRequestDto.getPassword());

        // Save to MongoDB
        Users savedUser = userRepository.save(user);

        // Convert to Response DTO
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        Users user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return mapToResponse(user);
    }

    private UserResponseDto mapToResponse(Users user) {
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getUserId()); // now userId is String
        response.setName(user.getUserName());
        response.setEmail(user.getUserEmail());
        return response;
    }
}
