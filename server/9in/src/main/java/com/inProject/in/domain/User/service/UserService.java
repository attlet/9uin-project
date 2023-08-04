package com.inProject.in.domain.User.service;

import com.inProject.in.domain.User.Dto.ResponseUserDto;
import com.inProject.in.domain.User.Dto.UpdateUserDto;
import com.inProject.in.domain.User.Dto.UserDto;
import com.inProject.in.domain.User.entity.User;

public interface UserService {
    ResponseUserDto getUser(Long id);
    ResponseUserDto createUser(UserDto userdto);
    ResponseUserDto updateUser(Long id, UpdateUserDto updateUserdto);
    void deleteUser(Long id);
}
