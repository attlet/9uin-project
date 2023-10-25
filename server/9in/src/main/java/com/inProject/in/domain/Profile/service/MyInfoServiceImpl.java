package com.inProject.in.domain.Profile.service;

import com.inProject.in.Global.exception.ConstantsClass;
import com.inProject.in.Global.exception.CustomException;
import com.inProject.in.config.security.JwtTokenProvider;
import com.inProject.in.domain.Board.entity.Board;
import com.inProject.in.domain.MToNRelation.TagMyInfoRelation.entity.TagMyInfoRelation;
import com.inProject.in.domain.MToNRelation.TagMyInfoRelation.repository.TagMyInfoRelationRepository;
import com.inProject.in.domain.Profile.Dto.request.RequestMyInfoDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseMyInfoDto;
import com.inProject.in.domain.Profile.entity.MyInfo;
import com.inProject.in.domain.Profile.repository.MyInfoRepository;
import com.inProject.in.domain.SkillTag.Dto.RequestSkillTagDto;
import com.inProject.in.domain.SkillTag.Dto.ResponseSkillTagDto;
import com.inProject.in.domain.SkillTag.entity.SkillTag;
import com.inProject.in.domain.SkillTag.repository.SkillTagRepository;
import com.inProject.in.domain.User.entity.User;
import com.inProject.in.domain.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.relational.core.conversion.DbAction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyInfoServiceImpl {

    private final MyInfoRepository myInfoRepository;
    private final UserRepository userRepository;
    private final TagMyInfoRelationRepository tagMyInfoRelationRepository;
    private final SkillTagRepository skillTagRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Logger log = LoggerFactory.getLogger(MyInfoServiceImpl.class);


    public List<TagMyInfoRelation> InsertTagMyInfoRelation(MyInfo myInfo, List<RequestSkillTagDto> requestSkillTagDtoList){

        List<TagMyInfoRelation> tagMyInfoRelationList = new ArrayList<>();

        if(myInfo.getTagMyInfoRelationList() != null){
            for(TagMyInfoRelation tagBoardRelation : myInfo.getTagMyInfoRelationList()){
                tagMyInfoRelationRepository.delete(tagBoardRelation);
            }
        }

        for(RequestSkillTagDto requestSkillTagDto : requestSkillTagDtoList){   //받아온 태그 당 관계 데이터를 생성

            SkillTag skillTag = skillTagRepository.findTagByName(requestSkillTagDto.toEntity().getName())
                    .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.BOARD, HttpStatus.NOT_FOUND, requestSkillTagDto.getName() + "는 없는 태그명입니다."));    //태그 잘못 입력했으면 오류 반환

            TagMyInfoRelation tagMyInfoRelation = TagMyInfoRelation.builder()
                    .skillTag(skillTag)
                    .myInfo(myInfo)
                    .build();

            tagMyInfoRelationList.add(tagMyInfoRelationRepository.save(tagMyInfoRelation));
        }

        return tagMyInfoRelationList;
    }

    public ResponseMyInfoDto getMyInfo(Long user_id){
        MyInfo myInfo = myInfoRepository.findMyInfoByUserId(user_id)
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user_id + "는 myinfo를 생성하지 않았음."));

        return new ResponseMyInfoDto(myInfo);
    }
    @Transactional
    public ResponseMyInfoDto createMyInfo(RequestMyInfoDto requestMyInfoDto, HttpServletRequest request){
        User user = getUserFromRequest(request);
        MyInfo myInfo = requestMyInfoDto.toEntity(user);


        MyInfo find = myInfoRepository.findMyInfoByUserId(user.getId()).get();

        if(find != null){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.CONFLICT, "이미 내 정보 작성함");
        }

        List<TagMyInfoRelation> tagMyInfoRelationList = InsertTagMyInfoRelation(myInfo, requestMyInfoDto.getRequestSkillTagDtoList());
        myInfo.setTagMyInfoRelationList(tagMyInfoRelationList);

        MyInfo savedMyInfo = myInfoRepository.save(myInfo);

        ResponseMyInfoDto responseMyInfoDto = new ResponseMyInfoDto(savedMyInfo);

        for(TagMyInfoRelation tagMyInfoRelation : tagMyInfoRelationList){
            ResponseSkillTagDto responseSkillTagDto = new ResponseSkillTagDto(tagMyInfoRelation.getSkillTag());
            responseMyInfoDto.getResponseSkillTagDtoList().add(responseSkillTagDto);
        }

        return responseMyInfoDto;
    }
    @Transactional
    public ResponseMyInfoDto updateMyInfo(RequestMyInfoDto requestMyInfoDto, HttpServletRequest request){
        User user = getUserFromRequest(request);

        MyInfo myInfo = myInfoRepository.findMyInfoByUserId(user.getId())
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 myinfo를 생성하지 않았음"));

        if(!myInfo.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        List<TagMyInfoRelation> tagMyInfoRelationList = InsertTagMyInfoRelation(myInfo, requestMyInfoDto.getRequestSkillTagDtoList());
        myInfo.setTagMyInfoRelationList(tagMyInfoRelationList);
        myInfo.updateMyInfo(requestMyInfoDto);

        ResponseMyInfoDto responseMyInfoDto = new ResponseMyInfoDto(myInfo);

        for(TagMyInfoRelation tagMyInfoRelation : tagMyInfoRelationList){
            ResponseSkillTagDto responseSkillTagDto = new ResponseSkillTagDto(tagMyInfoRelation.getSkillTag());
            responseMyInfoDto.getResponseSkillTagDtoList().add(responseSkillTagDto);
        }


        return responseMyInfoDto;

    }
    @Transactional
    public void deleteMyInfo(HttpServletRequest request){
        User user = getUserFromRequest(request);

        MyInfo myInfo = myInfoRepository.findMyInfoByUserId(user.getId())
                .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.BAD_REQUEST, user.getId() + "는 myinfo를 생성하지 않았음"));

        if(!myInfo.getUser().getId().equals(user.getId())){
            throw new CustomException(ConstantsClass.ExceptionClass.PROFILE, HttpStatus.UNAUTHORIZED, user.getId() + "은 프로필 작성자가 아닙니다.");
        }

        myInfoRepository.deleteById(myInfo.getId());

    }

    private User getUserFromRequest(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        User user;

        if(token != null && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);

            return user = userRepository.getByUsername(username)
                    .orElseThrow(() -> new CustomException(ConstantsClass.ExceptionClass.BOARD, HttpStatus.BAD_REQUEST, "BoardService ==> request로부터 user를 찾지 못함"));
        }
        else{
            throw new CustomException(ConstantsClass.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "token이 없거나, 권한이 유효하지 않습니다.");
        }


    }
}
