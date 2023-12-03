package com.inProject.in.domain.User.controller;

import com.inProject.in.domain.User.Dto.ResponseUserDto;
import com.inProject.in.domain.User.Dto.UpdateUserDto;
import com.inProject.in.domain.User.Dto.RequestUserDto;
import com.inProject.in.domain.User.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "user", description = "유저 관련 api로, 자주 사용되지는 않을 것입니다.")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<ResponseUserDto>> getUserList(){
        List<ResponseUserDto> users = userService.getUserList();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
//    @PostMapping()
//    public ResponseEntity<ResponseUserDto> createUser(@Valid @RequestBody RequestUserDto requestUserDto){
//
//        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);
//
//        return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
//    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long user_id,
                                                      @RequestBody UpdateUserDto updateUserDto) {

        ResponseUserDto responseUserDto = userService.updateUser(user_id, updateUserDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
    }

    @DeleteMapping("/{user_id}")
    @Parameter(name = "user_id", description = "유저 ID", in = ParameterIn.PATH, schema = @Schema(type = "integer", format = "int64"))
    public ResponseEntity<String> deleteUser(@PathVariable(name = "user_id") Long user_id){

        userService.deleteUser(user_id);

        return ResponseEntity.status(HttpStatus.OK).body("유저 삭제 완료");
    }
}
