package com.inProject.in.domain.SkillTag.controller;

import com.inProject.in.domain.SkillTag.Dto.RequestCreateSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.ResponseSkillTagDto;
import com.inProject.in.domain.SkillTag.service.SkillTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skillTag")
public class SkillTagController {
    private SkillTagService skillTagService;

    @Autowired
    public SkillTagController(SkillTagService skillTagService){
        this.skillTagService = skillTagService;
    }

    @GetMapping()
    public ResponseEntity<List<ResponseSkillTagDto>> getSkillTagList(){
        List<ResponseSkillTagDto> responseSkillTagDtos = skillTagService.getSkillTagList();

        return ResponseEntity.status(HttpStatus.OK).body(responseSkillTagDtos);
    }
    @PostMapping()
    public ResponseEntity<List<ResponseSkillTagDto>> createSkillTag(@RequestBody RequestCreateSkillTagDto requestCreateSkillTagDto){

        List<ResponseSkillTagDto> responseSkillTagDto = skillTagService.createSkillTag(requestCreateSkillTagDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseSkillTagDto);
    }

    @PutMapping("/{skillTag_id}")
    public ResponseEntity<ResponseSkillTagDto> updateSkillTag(@PathVariable(name = "skillTag_id") Long skillTag_id,
                                                              @RequestBody RequestSkillTagDto requestSkillTagDto){

        ResponseSkillTagDto responseSkillTagDto = skillTagService.updateSkillTag(skillTag_id, requestSkillTagDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseSkillTagDto);
    }

    @DeleteMapping("/{skillTag_id}")
    public ResponseEntity<String> deleteSkillTag(@PathVariable(name = "skillTag_id") Long skillTag_id){
        skillTagService.deleteSkillTag(skillTag_id);

        return ResponseEntity.status(HttpStatus.OK).body("태그 삭제 완료");
    }
}
