package com.inProject.in.domain.CommonLogic.Application.service;

import com.inProject.in.domain.CommonLogic.Application.Dto.RequestApplicationDto;
import com.inProject.in.domain.CommonLogic.Application.Dto.ResponseApplicationDto;
import com.inProject.in.domain.MToNRelation.ApplicantBoardRelation.entity.ApplicantBoardRelation;
import org.springframework.http.ResponseEntity;

public interface ApplicationService {
    ResponseApplicationDto createApplication(RequestApplicationDto requestApplicationDto);
    ResponseApplicationDto deleteApplication(RequestApplicationDto requestApplicationDto);
    ApplicantBoardRelation rejectApplication(RequestApplicationDto requestApplicationDto);
    ApplicantBoardRelation acceptApplication(RequestApplicationDto requestApplicationDto);
}

