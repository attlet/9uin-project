import React, { useState } from 'react'
import styled from 'styled-components';
import axios from 'axios';
import { getNewTokens } from '../../api/refreshToken';
import { createAxiosInstance } from '../../api/instance';

const MypageSchool = ({token}) => {
  const [contact, setContact] = useState('');
  const [email, setEmail] = useState('');
  const [school, setSchool] = useState('');
  const [major, setMajor] = useState('');
  const [graduationStatus, setGraduationStatus] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const data = {
    phone_num: contact,
    mail: email,
    school, 
    major,
    isGraduated: graduationStatus,
  };

  const handlePost = async () => {
    if (!contact || !email || !school || !major || !graduationStatus) {
      return alert('빈칸을 채워주세요');
    }
    try {
      setIsLoading(true); 
      let currentToken = token;

      const axiosInstance = createAxiosInstance(currentToken);

      const response = await axiosInstance.post('/education', data);
      console.log(response.data);

    } catch (error) {
      console.error(error);
      if (error.response && error.response.status === 401) {
        const { accessToken, refreshToken } = await getNewTokens();

        const axiosInstance = createAxiosInstance(refreshToken);
        const response = await axiosInstance.post('/education', data);
        console.log(response.data);
      }
    } finally {
      setIsLoading(false); 
    }
  };


  const handleContactChange = (e) => {
    setContact(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleSchoolChange = (e) => {
    setSchool(e.target.value);
  };

  const handleMajorChange = (e) => {
    setMajor(e.target.value);
  };

  const handleGraduationStatusChange = (e) => {
    setGraduationStatus(e.target.value);
  };

  return (
    <MySchool>
      <div className="section1_school">
        <span>아이디</span>
        <p>abc1234</p>
      </div>
      <div className="section1_school">
        <span>연락처</span>
        <input
          type="text"
          placeholder="내용을 입력해 주세요."
          value={contact}
          onChange={handleContactChange}
        />
      </div>
      <div className="section1_school">
        <span>이메일</span>
        <input
          type="text"
          placeholder="내용을 입력해 주세요."
          value={email}
          onChange={handleEmailChange}
        />
      </div>
      <div className="section1_school">
        <span>학교</span>
        <input
          type="text"
          placeholder="내용을 입력해 주세요."
          value={school}
          onChange={handleSchoolChange}
        />
      </div>
      <div className="section1_school">
        <span>전공</span>
        <input
          type="text"
          placeholder="내용을 입력해 주세요."
          value={major}
          onChange={handleMajorChange}
        />
      </div>
      <div className="section1_school">
        <span>졸업여부</span>
        <input
          type="text"
          placeholder="내용을 입력해 주세요."
          value={graduationStatus}
          onChange={handleGraduationStatusChange}
        />
      </div>
      <div className="section1_school_btn_flex">
        <button onClick={handlePost} className="section1_school_btn">
          수정하기
        </button>
      </div>
    </MySchool>
  )
}
const MySchool = styled.div`
  width: 318px;
  height: 416px;
  flex-shrink: 0;
  border-radius: 50px;
  border: 2px solid #dae9fc;
  background: #fff;
  display: flex;
  flex-direction: column;
  align-items: start;
  padding-left: 20px;
  justify-content: center;
  gap: 20px;

  .section1_school {
    display: flex;
    gap: 5px;
    align-items: center;
    justify-content: center;

    span {
      width: 80px;
      display: inline-block;
    }


    input {
      width: 180px;
      border-radius: 5px;
      border: 1px solid #d2e2ec;
      background: var(--bs-white, #fff);
      box-shadow: 0px 0px 0px 0px #cbdafc;
    }
  }

  .section1_school_btn_flex {
    width: 100%;
    display: flex;
    justify-content: center;
    padding-top: 10px;
  }

  .section1_school_btn {
    display: flex;
    width: 100px;
    bottom: 20px;

    padding: 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    text-align: center;
    flex-shrink: 0;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: #fff;
    box-shadow: 0px 0px 0px 0px #cbdafc;
    cursor: pointer;
    color: #1f7ceb;
  }

  @media only screen and (min-width: 768px) and (max-width: 1325px) {
    width: 470px;
    .section1_school {
      padding-left: 70px;
    }
  }
`;
export default MypageSchool
