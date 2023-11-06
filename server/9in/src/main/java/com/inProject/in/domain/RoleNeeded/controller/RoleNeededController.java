package com.inProject.in.domain.RoleNeeded.controller;

import com.inProject.in.domain.RoleNeeded.Dto.RequestRoleNeededDto;
import com.inProject.in.domain.RoleNeeded.Dto.ResponseRoleNeededDto;
import com.inProject.in.domain.RoleNeeded.service.RoleNeededService;
import com.inProject.in.domain.SkillTag.Dto.ResponseSkillTagDto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roleNeeded")
public class RoleNeededController {
    private RoleNeededService roleNeededService;

    @Autowired
    public RoleNeededController(RoleNeededService roleNeededService){
        this.roleNeededService = roleNeededService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseRoleNeededDto>> getRoleNeeded(){
        List<ResponseRoleNeededDto> responseRoleNeededDtoList = roleNeededService.getRoleNeeded();

        return ResponseEntity.status(HttpStatus.OK).body(responseRoleNeededDtoList);
    }
    @PostMapping()
    public ResponseEntity<List<ResponseRoleNeededDto>> createRoleNeeded(@RequestBody RequestRoleNeededDto requestRoleNeededDto){
        List<ResponseRoleNeededDto> responseRoleNeededDto = roleNeededService.createRoleNeededs(requestRoleNeededDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseRoleNeededDto);
    }

    @PutMapping("/{role_id}")
    public ResponseEntity<ResponseRoleNeededDto> updateRoleNeeded(@PathVariable(name = "role_id") Long role_id,
                                                                  @RequestParam(name = "name") String name){
        ResponseRoleNeededDto responseRoleNeededDto = roleNeededService.updateRoleNeeded(role_id, name);

        return ResponseEntity.status(HttpStatus.OK).body(responseRoleNeededDto);
    }

    @DeleteMapping("/{role_id}")
    public ResponseEntity<String> deleteRoleNeeded(@PathVariable(name = "role_id") Long role_id){
        roleNeededService.deleteRoleNeeded(role_id);

        return ResponseEntity.status(HttpStatus.OK).body("role 삭제 성공");
    }
}
