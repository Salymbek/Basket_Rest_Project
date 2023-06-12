package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.TokenResponse;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AllUserResponse;
import peaksoft.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    SimpleResponse saveUser(UserRequest userRequest);
    List<AllUserResponse> getAllUser();
    UserResponse getUserById(Long id);
    SimpleResponse updateUserById(Long id,UserRequest userRequest);
    SimpleResponse deleteById(Long id);
    TokenResponse authenticate(UserRequest userRequest);
}
