package ra.projectintern.service.mapper;

import ra.projectintern.exception.CustomException;
import ra.projectintern.model.domain.Users;
import ra.projectintern.model.dto.request.UserRequest;
import ra.projectintern.model.dto.response.UserResponse;
import ra.projectintern.service.IGenericMapper;

public class UserMapper implements IGenericMapper<Users, UserRequest, UserResponse> {
    @Override
    public Users toEntity(UserRequest userRequest) {
        return Users.builder()
                .fullName(userRequest.getFullName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .roles(userRequest.getRoles())
                .password(userRequest.getPassword())
                .phoneNumber(userRequest.getPhoneNumber())
                .status(userRequest.isStatus())
                .build();
    }

    @Override
    public UserResponse toResponse(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                .fullName(users.getFullName())
                .phoneNumber(users.getPhoneNumber())
                .roles(users.getRoles())
                .status(users.isStatus())
                .refresh_token(users.getRefresh_token())
                .build();
    }
}
