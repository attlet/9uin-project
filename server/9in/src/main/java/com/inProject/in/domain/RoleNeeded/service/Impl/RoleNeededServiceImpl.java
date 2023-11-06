package com.inProject.in.domain.RoleNeeded.service.Impl;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.Board.repository.BoardRepository;
import com.inProject.in.domain.RoleNeeded.Dto.ResponseRoleNeededDto;
import com.inProject.in.domain.RoleNeeded.Dto.RequestRoleNeededDto;
import com.inProject.in.domain.RoleNeeded.entity.RoleNeeded;
import com.inProject.in.domain.RoleNeeded.repository.RoleNeededRepository;
import com.inProject.in.domain.RoleNeeded.service.RoleNeededService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleNeededServiceImpl implements RoleNeededService {

    private BoardRepository boardRepository;
    private RoleNeededRepository roleNeededRepository;
    @Autowired
    public RoleNeededServiceImpl(BoardRepository boardRepository, RoleNeededRepository roleNeededRepository){
        this.boardRepository = boardRepository;
        this.roleNeededRepository = roleNeededRepository;
    }

    @Override
    public List<ResponseRoleNeededDto> getRoleNeeded() {
        List<RoleNeeded> roleNeededList = roleNeededRepository.findAll();
        List<ResponseRoleNeededDto> responseRoleNeededDtoList = new ArrayList<>();

        for(RoleNeeded roleNeeded : roleNeededList){
            ResponseRoleNeededDto responseRoleNeededDto = ResponseRoleNeededDto.builder()
                    .role_id(roleNeeded.getId())
                    .name(roleNeeded.getName())
                    .build();

            responseRoleNeededDtoList.add(responseRoleNeededDto);
        }

        return responseRoleNeededDtoList;
    }

    @Override
    public List<ResponseRoleNeededDto> createRoleNeededs(RequestRoleNeededDto requestRoleNeededDto) {
        List<ResponseRoleNeededDto> responseRoleNeededDtoList = new ArrayList<>();

        for(String roleName : requestRoleNeededDto.getName()){

            RoleNeeded roleNeeded = RoleNeeded.builder()
                    .name(roleName)
                    .build();

            RoleNeeded savedRoleNeeded = roleNeededRepository.save(roleNeeded);

            ResponseRoleNeededDto responseRoleNeededDto = ResponseRoleNeededDto.builder()
                    .role_id(savedRoleNeeded.getId())
                    .name(savedRoleNeeded.getName())
                    .build();

            responseRoleNeededDtoList.add(responseRoleNeededDto);
        }
        return responseRoleNeededDtoList;
    }

    @Override
    public ResponseRoleNeededDto updateRoleNeeded(Long role_id, String name) {
        RoleNeeded roleNeeded = roleNeededRepository.findById(role_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.ROLE, HttpStatus.NOT_FOUND, "updateRole에서 없는 role입니다."));

        roleNeeded.setName(name);
        roleNeeded.updateRoleNeeded(roleNeeded);
        RoleNeeded savedRoleNeeded = roleNeededRepository.save(roleNeeded);

        ResponseRoleNeededDto responseRoleNeededDto = ResponseRoleNeededDto.builder()
                .role_id(savedRoleNeeded.getId())
                .name(savedRoleNeeded.getName())
                .build();

        return responseRoleNeededDto;
    }

    @Override
    public void deleteRoleNeeded(Long role_id) {
        RoleNeeded roleNeeded = roleNeededRepository.findById(role_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.ROLE, HttpStatus.NOT_FOUND, "deleteRole에서 없는 role입니다."));

        roleNeededRepository.deleteById(role_id);
    }
}
