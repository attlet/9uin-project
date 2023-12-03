package com.inProject.in.domain.User.service;

import com.inProject.in.domain.User.Dto.ResponseUserDto;
import com.inProject.in.domain.User.Dto.UpdateUserDto;
import com.inProject.in.domain.User.Dto.RequestUserDto;
import com.inProject.in.domain.User.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface UserService {
    List<ResponseUserDto> getUserList();
    ResponseUserDto createUser(RequestUserDto userdto);
    ResponseUserDto updateUser(Long id, UpdateUserDto updateUserdto);
    void deleteUser(Long id);
}
