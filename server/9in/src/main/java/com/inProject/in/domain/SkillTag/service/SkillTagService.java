package com.inProject.in.domain.SkillTag.service;

import com.inProject.in.domain.SkillTag.Dto.RequestCreateSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.ResponseSkillTagDto;

import java.util.List;

public interface SkillTagService {

    List<ResponseSkillTagDto> getSkillTagList();
    List<ResponseSkillTagDto> createSkillTag(RequestCreateSkillTagDto requestCreateSkillTagDto);
    ResponseSkillTagDto updateSkillTag(Long skillTag_id, RequestSkillTagDto requestSkillTagDto);
    void deleteSkillTag(Long skillTag_id);
}
