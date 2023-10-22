import React, { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function PWfoundView() {
  const navigate = useNavigate();

  const [id, setId] = useState('');
  const [isId, setIsId] = useState(false);
  const [isNotId, setIsNotId] = useState(false);

  const [mail, setMail] = useState('');
  const [isMail, setIsMail] = useState(false);
  const [isNotMail, setIsNotMail] = useState(false);

  const handleLogin = () => {
    navigate('/user/login');
  };

  const handleChangeId = (e) => {
    setId(e.target.value);
  };

  const handleChangeMail = (e) => {
    setMail(e.target.value);
  };

  const checkId = async () => {
    const userInfo = {
      username: id,
    };

    try {
      const response = await axios.post(
        'http://1.246.104.170:8080/find/checkId',
        userInfo
      );
      console.log(response);

      if (response.data.message === '유저 존재') {
        setIsId(true);

        setTimeout(() => {
          setIsId(false);
        }, 3000);
      }
    } catch (error) {
      console.log('아이디 체크 실패', error);
      if (
        error.response.data.message ===
        'Find Exception. find - checkId에서 username이 존재하지 않음 확인'
      ) {
        console.log('들어옴');
        setIsNotId(true);

        setTimeout(() => {
          setIsNotId(false);
        }, 3000);
      }
    }
  };

  const findPw = async () => {
    const mailInfo = {
      mail,
    };

    try {
      const response = await axios.post(
        'http://1.246.104.170:8080/find/findPw',
        mailInfo
      );
      console.log(response);

      setIsMail(true);

      setTimeout(() => {
        setIsMail(false);
      }, 3000);
    } catch (error) {
      console.log('비밀번호 변경 실패', error);
      if (
        error.response.data.message ===
        `Sign Exception. ${mail}은 등록되지 않은 mail입니다.`
      ) {
        console.log('들어옴');
        setIsNotMail(true);
        console.log(isNotMail);

        setTimeout(() => {
          setIsNotMail(false);
        }, 3000);
      }
    }
  };

  return (
    <Container>
      <Title>비밀번호 찾기</Title>
      <ContainerBox>
        <SubTitle>아이디</SubTitle>
        <InputBox>
          <StyledInput
            type="text"
            value={id}
            onChange={handleChangeId}
            placeholder="내용을 입력해 주세요."
          />
          <CheckBtn onClick={checkId}>확인</CheckBtn>
        </InputBox>
        {isId && <IsId>아이디를 확인했습니다</IsId>}
        {isNotId && <IsNotId>아이디가 존재하지 않습니다.</IsNotId>}
        <MailContainer>
          <SubTitle>이메일 주소</SubTitle>
          <InputBox>
            <StyledInput
              type="text"
              value={mail}
              onChange={handleChangeMail}
              placeholder="내용을 입력해 주세요."
            />
            <CheckBtn onClick={findPw}>인증 요청</CheckBtn>
          </InputBox>
          {isMail && <IsMail>임시 비밀번호를 생성했습니다.</IsMail>}
          {isNotMail && <IsNotMail>위 메일은 없는 메일정보입니다.</IsNotMail>}
        </MailContainer>
        <StyledFoundBtn onClick={handleLogin}>로그인</StyledFoundBtn>
      </ContainerBox>
    </Container>
  );
}

const Container = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Title = styled.p`
  margin-top: 5rem;
  font-weight: 900;
  margin-bottom: 1.2rem;
`;

const SubTitle = styled.p`
  font-weight: 800;
  margin: 0.5rem 0;
`;

const ContainerBox = styled.div`
  border: 2px solid #d2e2ec;
  border-radius: 20px;
  padding: 3rem 8rem;
`;

const InputBox = styled.div`
  display: flex;
  justify-content: center;
  margin-bottom: 1.2rem;
`;

const CheckBtn = styled.button`
  color: #1f7ceb;
  border: solid 1px #d2e2ec;
  background-color: white;
  width: 45%;

  &:hover {
    cursor: pointer;
  }
`;

const MailContainer = styled.div`
  margin-top: 2rem;
  margin-bottom: 3rem;
`;

const StyledInput = styled.input`
  width: 100%;
  padding: 0.5rem 1rem;
  margin-right: 1.5rem;
  border: 1px solid #d2e2ec;
`;

const IsNotId = styled.p`
  color: red;
  font-weight: 600;
  text-align: center;
  margin-bottom: 1.2rem;
`;

const IsId = styled.p`
  color: #1f7ceb;
  font-weight: 600;
  text-align: center;
  margin-bottom: 1.2rem;
`;

const IsNotMail = styled.p`
  color: red;
  font-weight: 600;
  text-align: center;
  margin-bottom: 1.2rem;
`;

const IsMail = styled.p`
  color: #1f7ceb;
  font-weight: 600;
  text-align: center;
  margin-bottom: 1.2rem;
`;

const FoundBtn = styled.div`
  background-color: #dae9fc;
  text-align: center;
  margin: 1rem;
  padding: 0.3rem;
  width: 50%;
  margin: auto;
  border-radius: 25px;
`;

const PWBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 3.5rem;
  margin-bottom: 4rem;
`;

const PWContent = styled.p`
  color: #1f7ceb;
  margin: 0;
  font-weight: 700;
`;

const PWComment = styled.p`
  margin: 0.1rem;
`;

const LoginBtn = styled.div`
  background-color: #1f7ceb;
  color: white;
  border-radius: 25px;
  width: 50%;
  margin: auto;
  text-align: center;
  padding: 0.3rem;
`;

const StyledFoundBtn = styled(FoundBtn)`
  &:hover {
    cursor: pointer;
  }
`;

const StyledLoginBtn = styled(LoginBtn)`
  &:hover {
    cursor: pointer;
  }
`;
