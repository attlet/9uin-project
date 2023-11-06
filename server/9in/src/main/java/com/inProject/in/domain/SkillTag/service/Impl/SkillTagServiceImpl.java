package com.inProject.in.domain.SkillTag.service.Impl;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.domain.SkillTag.Dto.RequestCreateSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.ResponseSkillTagDto;
import com.inProject.in.domain.SkillTag.entity.SkillTag;
import com.inProject.in.domain.SkillTag.repository.SkillTagRepository;
import com.inProject.in.domain.SkillTag.service.SkillTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillTagServiceImpl implements SkillTagService {

    private SkillTagRepository skillTagRepository;
    @Autowired
    public SkillTagServiceImpl(SkillTagRepository skillTagRepository){
        this.skillTagRepository = skillTagRepository;
    }

    @Override
    public List<ResponseSkillTagDto> getSkillTagList() {

        List<SkillTag> skillTagList = skillTagRepository.findAll();
        List<ResponseSkillTagDto> responseSkillTagDtoList = new ArrayList<>();

        for(SkillTag skillTag : skillTagList){
            ResponseSkillTagDto responseSkillTagDto = ResponseSkillTagDto.builder()
                    .skillTag_id(skillTag.getId())
                    .name(skillTag.getName())
                    .build();

            responseSkillTagDtoList.add(responseSkillTagDto);
        }

        return responseSkillTagDtoList;
    }

    @Override
    public List<ResponseSkillTagDto> createSkillTag(RequestCreateSkillTagDto requestCreateSkillTagDto) {

        List<ResponseSkillTagDto> responseSkillTagDtoList = new ArrayList<>();

        for(String tagName : requestCreateSkillTagDto.getName()){
            SkillTag skillTag = SkillTag.builder()
                    .name(tagName)
                    .build();

            SkillTag savedTag = skillTagRepository.save(skillTag);

            ResponseSkillTagDto responseSkillTagDto = ResponseSkillTagDto.builder()
                    .skillTag_id(savedTag.getId())
                    .name(savedTag.getName())
                    .build();

            responseSkillTagDtoList.add(responseSkillTagDto);
        }

        return responseSkillTagDtoList;
    }

    @Override
    public ResponseSkillTagDto updateSkillTag(Long skillTag_id, RequestSkillTagDto requestSkillTagDto) {
        SkillTag skillTag = skillTagRepository.findById(skillTag_id).
                orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.SKILLTAG, HttpStatus.NOT_FOUND, "updateSkill에서 없는 태그입니다."));

        skillTag.updateSkillTag(requestSkillTagDto);
        SkillTag updateTag = skillTagRepository.save(skillTag);

        ResponseSkillTagDto responseSkillTagDto = new ResponseSkillTagDto(updateTag);

        return responseSkillTagDto;
    }

    @Override
    public void deleteSkillTag(Long skillTag_id) {
        skillTagRepository.deleteById(skillTag_id);
    }
}
