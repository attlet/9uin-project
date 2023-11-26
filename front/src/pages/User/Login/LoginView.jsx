import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
// import { useAuth } from '../../../ components/context/AuthContext';
import { useDispatch } from 'react-redux';
import { login } from '../../../modules/auth';
import { createAxiosInstance } from '../../../api/instance';

export default function LoginView() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // const { login } = useAuth();
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleIdFound = () => {
    navigate('/user/idFound');
  };

  const handlePwFound = () => {
    navigate('/user/pwFound');
  };

  const handlePwChange = () => {
    navigate('/user/pwChange');
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    console.log('로그인 정보:', {
      username,
      password,
    });
    const userData = { username, password };

    try {
      const axiosInstance = createAxiosInstance();
      const response = await axiosInstance.post('/sign/sign-in', userData);
      console.log('로그인 성공');
      console.log(response.data);
      const token = response.data.token;
      const refreshToken = response.data.refreshToken;
      const user_id = response.data.user_id;
      // login(token, refreshToken, user_id);
      dispatch(login(token, refreshToken, user_id));

      localStorage.setItem('token', token);
      localStorage.setItem('refreshToken', refreshToken);

      // connect 서버 소켓통신
      // const loginAxios = createAxiosInstance(token);
      // const connect = await loginAxios.get('/connect');
      // console.log(connect);

      navigate('/');
    } catch (error) {
      alert('아이디 또는 비밀번호를 확인해주세요.');
      console.error('로그인 실패', error);
    }
  };

  return (
    <Container>
      <Title>로그인</Title>
      <ContainerBox>
        <form onSubmit={handleLogin}>
          <InputContainer>
            <p className="input__text">아이디</p>
            <input
              type="text"
              onChange={handleUsernameChange}
              placeholder="내용을 입력해 주세요."
              value={username}
            />
            <p className="input__text">비밀번호</p>
            <input
              className="password__input"
              type="password"
              onChange={handlePasswordChange}
              placeholder="내용을 입력해 주세요."
              value={password}
            />
            <LoginBtnContainer>
              <LoginBtn>로그인</LoginBtn>
            </LoginBtnContainer>
          </InputContainer>
        </form>
        <ButtonContainer>
          <MenuText onClick={handleIdFound}>아이디 찾기</MenuText>
          <MenuText onClick={handlePwFound}>비밀번호 찾기</MenuText>
          <MenuText onClick={handlePwChange}>비밀번호 변경</MenuText>
        </ButtonContainer>
      </ContainerBox>
      <LoginSNS>SNS로 로그인 하기</LoginSNS>
      <SNSContainer>
        <SNSIconContainer>
          <div className="kakao"></div>
          <SNSText>Kakao로 로그인 하기</SNSText>
        </SNSIconContainer>
        <SNSIconContainer>
          <div className="naver"></div>
          <SNSText>Naver로 로그인 하기</SNSText>
        </SNSIconContainer>
        <SNSIconContainer>
          <div className="github"></div>
          <SNSText>Github로 로그인 하기</SNSText>
        </SNSIconContainer>
      </SNSContainer>
      <JoinContainer>
        <JoinQA>아직 회원이 아니세요?</JoinQA>
        <JoinText onClick={() => navigate('/join')}>회원가입하기</JoinText>
      </JoinContainer>
    </Container>
  );
}

const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 2rem;
`;

const Title = styled.p`
  font-weight: 800;
  margin: 1rem;
`;

const ContainerBox = styled.div`
  border: 2px solid #d2e2ec;
  border-radius: 20px;
  padding: 3rem 8rem;
`;

const InputContainer = styled.div`
  .input__text {
    font-weight: 700;
    margin-top: 1.3rem;
    margin-bottom: 0.4rem;
  }

  input {
    width: 100%;
    border: 1px solid #d2e2ec;
    padding: 0.5rem;
    border-radius: 0.2rem;
  }

  .password__input {
    background-color: #d2e2ec;
    margin-bottom: 0.7rem;
  }
`;

const LoginBtnContainer = styled.div`
  margin-top: 2rem;
  text-align: center;
`;

const ButtonContainer = styled.div`
  display: flex;
  margin: auto;
  width: 100%;
`;

const MenuText = styled.p`
  font-size: 0.6rem;
  margin: 0.2rem;
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }
`;

const LoginBtn = styled.button`
  margin: auto;
  background-color: #1f7ceb;
  width: 70%;
  border-radius: 25px;
  color: #ffffff;
  padding: 0.5rem 1rem;
  border: none;
  margin: 1rem;
`;

const LoginSNS = styled.p`
  font-weight: 800;
  margin: 2rem;
`;

const SNSContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const SNSIconContainer = styled.div`
  margin: 0 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;

  .kakao {
    background-image: url('/sns/kakaoImage.png');
    background-size: cover;
    width: 3rem;
    height: 3rem;
    margin: 1rem 0;
  }

  .naver {
    background-image: url('/sns/naverImage.png');
    background-size: cover;
    width: 3rem;
    height: 3rem;
    margin: 1rem 0;
  }

  .github {
    background-image: url('/sns/githubImage.png');
    background-size: cover;
    width: 3rem;
    height: 3rem;
    margin: 1rem 0;
  }
`;

const SNSText = styled.span`
  font-size: 0.8rem;
`;

const JoinContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const JoinQA = styled.p`
  font-size: 0.8rem;
  margin-top: 2.4rem;
  margin-bottom: 1rem;
  font-weight: 400;
`;

const JoinText = styled.p`
  font-weight: 900;
  text-decoration: underline;
  cursor: pointer;

  &:hover {
    text-decoration: none;
  }
`;
