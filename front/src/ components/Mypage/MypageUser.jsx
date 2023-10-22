import React, { useEffect, useState } from 'react'
import styled from 'styled-components';
import styles from '../../pages/User/MyPage/MyPage.module.css'
import { getNewTokens } from '../../api/refreshToken';
import { createAxiosInstance } from '../../api/instance';

const MypageUser = ({token, myinfoData}) => {
  const [nickname, setNickname] = useState("");
  const [selectedStack, setSelectedStack] = useState('');
  const [skills, setSkills] = useState([]);
  const [selectedRole, setSelectedRole] = useState("");
  const [experience, setExperience] = useState(myinfoData?.career || "");
  const [isLoading, setIsLoading] = useState(false);
   
  useEffect(() => {
    if (myinfoData) {
      setNickname(myinfoData.nickname);
      setSelectedRole(myinfoData.role);
      setExperience(myinfoData.career);
    }
  }, [myinfoData]);

  const data = {
    nickname,
    role: selectedRole,
    career: experience,
    // stack 배열
  };


  const handlePost = async () => {
    if (!nickname || !selectedRole || !experience || skills.length < 1) {
      return alert('빈칸을 채워주세요');
    }
  
    try {
      setIsLoading(true);
      let currentToken = token;
  
      const axiosInstance = createAxiosInstance(currentToken);

      if (myinfoData) {
        const response = await axiosInstance.put('/myinfo', data);
      } else {
        const response = await axiosInstance.post('/myinfo', data);
      }
  
    } catch (error) {
      console.error(error);
  
      if (error.response && error.response.status === 401) {
        const { accessToken, refreshToken } = await getNewTokens();

        const axiosInstance = createAxiosInstance(refreshToken);
        if (myinfoData) {
          const response = await axiosInstance.put('/myinfo', data);
        } else {
          const response = await axiosInstance.post('/myinfo', data);
        }
        
      }
    } finally {
      setIsLoading(false);
      alert(myinfoData ? "수정하기 완료" : "등록하기 완료")
    }
  };
  

  const skillImage = {
    react: 'React.png',
    Spring: 'Spring.png',
    javascript: 'JS.png',
  };

  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

  const handleStackChange = (e) => {
    setSelectedStack(e.target.value);
  };

  const handleAddStack = () => {
    if (selectedStack) {
      if (skills.includes(selectedStack)) {
        alert("이미 존재하는 스킬입니다.");
      } else {
        setSkills([...skills, selectedStack]);
        setSelectedStack('');
      }
    }
  };

  const handleSkillClick = (clickedSkill) => {
    const shouldDelete = window.confirm(`삭제하시겠습니까?`);
    if (shouldDelete) {
      const updatedSkills = skills.filter((skill) => skill !== clickedSkill);
      setSkills(updatedSkills);
    }
  };

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
  };

  const handleExperienceChange = (e) => {
    setExperience(e.target.value);
  };

  return (
    <UserEdit className="section1_profile">
      <div className={styles.profile__img}></div>
      <div className="section_profile_flex">
        <div>
          <span>닉네임</span>
          <input
            type="text"
            placeholder="닉네임을 입력해주세요"
            value={nickname}
            onChange={handleNicknameChange}
          />
        </div>
        <div>
          <span>기술스택</span>
          <select name="stack" value={selectedStack} onChange={handleStackChange}>
            <option value="">선택</option>
            <option value="react">react</option>
            <option value="Spring">spring</option>
            <option value="javascript">javascript</option>
          </select>
          <button className="add_btn" onClick={handleAddStack}>
            추가
          </button>
        </div>
        <div>
          <span></span>
          <div className="skill_stack">
            {skills.map((skill) => (
              <div key={skill} onClick={() => handleSkillClick(skill)}>
                {skillImage[skill] && (
                  <img className='skill_image' src={`/stack/${skillImage[skill]}`} alt={skill} />
                )}
              </div>
            ))}
          </div>
        </div>
        <div>
          <span>역할</span>
          <select name="stack" value={selectedRole} onChange={handleRoleChange}>
            <option value="">선택</option>
            <option value="Designer">Designer</option>
            <option value="Frontend">Frontend</option>
            <option value="Backend">Backend</option>
          </select>
        </div>
        <div>
          <span>경력</span>
          <input
            type="text"
            placeholder="경력을 입력해주세요"
            value={experience}
            onChange={handleExperienceChange}
          />
        </div>
      </div>
      <button onClick={handlePost} className="section1_fix_btn" >
        {myinfoData ? "수정하기" : "등록하기"}
      </button>
    </UserEdit>
  );
};

const UserEdit = styled.div`
  position: relative;
  width: 660px;
  height: 416px;
  flex-shrink: 0;
  border-radius: 50px;
  border: 2px solid #dae9fc;
  background: #fff;
  display: flex;
  justify-content: center;
  align-items: center;

  .skill_image {
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`


export default MypageUser
