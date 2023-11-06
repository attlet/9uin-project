package com.inProject.in.domain.RoleNeeded.service;

import com.inProject.in.domain.RoleNeeded.Dto.ResponseRoleNeededDto;
import com.inProject.in.domain.RoleNeeded.Dto.RequestRoleNeededDto;

import java.util.List;

public interface RoleNeededService {
    List<ResponseRoleNeededDto> getRoleNeeded();
    List<ResponseRoleNeededDto> createRoleNeededs(RequestRoleNeededDto requestRoleNeededDto);
    ResponseRoleNeededDto updateRoleNeeded(Long role_id, String name);
    void deleteRoleNeeded(Long role_id);
}
