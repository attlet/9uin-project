import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import MypageUser from '../../../ components/Mypage/MypageUser';
import MypageSchool from '../../../ components/Mypage/MypageSchool';
import MypageEtc from '../../../ components/Mypage/MypageEtc';
import MypageLicese from '../../../ components/Mypage/MypageLicese';
import MypageJob from '../../../ components/Mypage/MypageJob';
import MypageProject from '../../../ components/Mypage/MypageProject';
import { getNewTokens } from '../../../api/refreshToken';
import { createAxiosInstance } from '../../../api/instance';

export default function MyPage() {
  const [token, setToken] = useState('');
  const [refreshToken, setRefreshToken] = useState('');
  const [data, setData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const user_id = 1234; // user_id

  useEffect(() => {
    setToken(localStorage.getItem("token"));
    setRefreshToken(localStorage.getItem("refresh_token"));

  },[token, refreshToken])

  console.log(data)

  const getProfile = async() => {
    try {
      setIsLoading(true);
      const axiosInstance = createAxiosInstance(token);
      const response = await axiosInstance.get(`/profile/${user_id}`);
      setData(response.data)
    } catch (error) {
      console.error(error);
      if (error.response && error.response.status === 401) {
        const { accessToken, refreshToken } = await getNewTokens();

        const axiosInstance = createAxiosInstance(refreshToken);
        const response = await axiosInstance.get(`/profile/${user_id}`);
        setData(response.data)
      }
    } finally { 
      setIsLoading(false);
    }
  }

  useEffect(() => {
    getProfile()
  },[])

  return (
    <Mypage>
      <Section1>
        <MypageUser 
          token={refreshToken}
          myinfoData={data.myInfoDto}
        />
        <div className='section1_flex'>
          <MypageSchool 
            token={refreshToken}
          />
          <MypageEtc />
        </div>
      </Section1>
      <Section2>
        <MypageLicese 
          token={refreshToken}
        />
        <MypageJob 
          token={refreshToken}
        />
        <MypageProject 
          token={refreshToken}
        />
      </Section2>
    </Mypage>
  );
}

const Mypage = styled.div`
  width: 1300px;
  padding-top: 50px;
  margin: auto;
`

const Section1 = styled.div`
  display: flex;
  justify-content: center;
  gap: 20px;

  .section1_profile {
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
  }

  .section1_flex {
    display: flex;
    gap: 20px;
  }

  .section1_etc {
    width: 200px;
    height: 318px;
    flex-shrink: 0;
    border-radius: 50px;
    border: 2px solid #dae9fc;
    background: #fff;
  }

  .section1_fix_btn {
    display: flex;
    width: 100px;
    bottom: 20px;
    position: absolute;
    height: 34px;
    padding: 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    flex-shrink: 0;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: #fff;
    box-shadow: 0px 0px 0px 0px #cbdafc;
    cursor: pointer;
    color: #1f7ceb;
  }

  .section_profile_flex {
    div {
      display: flex;
      gap: 5px;
      margin-bottom: 5px;
      align-items: center;
      span {
        display: inline-block;
        width: 120px;
        text-align: center;
      }

      input {
        display: flex;
        width: 187px;
        height: 22px;
        padding: 10px;
        align-items: center;
        gap: 5px;
        flex-shrink: 0;
        border-radius: 5px;
        border: 1px solid #d2e2ec;
        background: var(--bs-white, #fff);
        box-shadow: 0px 0px 0px 0px #cbdafc;
      }

      select {
        width: 208px;
        height: 42px;
        border-radius: 5px;
        border: 1px solid #d2e2ec;
        background: var(--bs-white, #fff);
        box-shadow: 0px 0px 0px 0px #cbdafc;
      }

      .skill_stack {
      }

      .add_btn {
        display: flex;
        width: 74px;
        height: 34px;
        padding: 10px;
        justify-content: center;
        align-items: center;
        gap: 10px;
        flex-shrink: 0;
        color: #1f7ceb;
        font-family: SUITE Variable;
        font-size: 14px;
        font-style: normal;
        font-weight: 400;
        line-height: normal;
        border-radius: 5px;
        border: 1px solid #d2e2ec;
        background: #fff;
        box-shadow: 0px 0px 0px 0px #cbdafc;
        cursor: pointer;
      }
    }
  }

  @media only screen and (min-width: 768px) and (max-width: 1325px) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    gap:30px;
    .section1_profile {
      width: 750px;
    }
  }
`;

const Section2 = styled.div`
  @media only screen and (min-width: 768px) and (max-width: 1325px) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
`
