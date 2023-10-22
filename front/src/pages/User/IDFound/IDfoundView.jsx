import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';

export default function IDfoundView() {
  const navigate = useNavigate();

  const [mail, setMail] = useState('');
  const [isNotMail, setIsNotMail] = useState(false);
  const [isMail, setIsMail] = useState(false);

  const [validation, setValidation] = useState('');
  const [isNotValid, setIsNotValid] = useState(false);

  const [userId, setUserId] = useState('');

  const handlePW = () => {
    navigate('/user/pwFound');
  };

  const handleLogin = () => {
    navigate('/user/login');
  };

  const handleChangeMail = (e) => {
    setMail(e.target.value);
  };

  const sendValidMail = async () => {
    const mailInfo = {
      mail,
    };

    try {
      const response = await axios.post(
        'http://1.246.104.170:8080/find/validCodeSend',
        mailInfo
      );
      console.log(response);
      setIsNotMail(false);
      setIsMail(true);
      setTimeout(() => {
        setIsMail(false);
      }, 3000);
    } catch (error) {
      console.error('메일 인증 체크실패', error);
      console.log(error.response.data.message);
      if (
        error.response.data.message ===
        `Sign Exception. 등록되지 않은 메일입니다.`
      ) {
        console.log('들어옴');
        setIsNotMail(true);
        setIsMail(false);
        console.log(isNotMail);

        setTimeout(() => {
          setIsNotMail(false);
        }, 3000);
      }
    }
  };

  const handleChangeValidation = (e) => {
    setValidation(e.target.value);
  };

  const checkValidation = async () => {
    const validInfo = {
      mail,
      validCode: validation,
    };

    console.log(validInfo);

    try {
      const res = await axios.post(
        'http://1.246.104.170:8080/find/validMail',
        validInfo
      );

      console.log(res);
      if (res.data.success === true) {
        findId();
      } else {
        // 메일의 인증번호와 입력한 인증번호가 일치하지 않음
        setIsNotValid(true);

        setTimeout(() => {
          setIsNotValid(false);
        }, 3000);
      }
    } catch (error) {
      console.error('네트워크 에러 발생', error);
    }
  };

  const findId = async () => {
    const mailInfo = {
      mail,
    };

    try {
      const response = await axios.post(
        'http://1.246.104.170:8080/find/findId',
        mailInfo
      );
      console.log(response);
      setUserId(response.data.userId);
      setIsNotValid(false);
    } catch (error) {
      console.error('아이디 찾기 실패- 네트워크에러', error);
    }
  };

  return (
    <Container>
      <Title>아이디 찾기</Title>
      <ContainerBox>
        <Email>이메일 주소</Email>
        <InputBox>
          <Input
            type="text"
            value={mail}
            onChange={handleChangeMail}
            placeholder="내용을 입력해 주세요."
          />
          <CheckBtn onClick={sendValidMail}>인증 요청</CheckBtn>
        </InputBox>
        {isNotMail && <IsNotMail>등록되지 않은 메일입니다.</IsNotMail>}
        {isMail && <IsMail>메일로 인증번호를 전송했습니다.</IsMail>}
        <InputBox>
          <InputValid
            type="text"
            value={validation}
            onChange={handleChangeValidation}
            placeholder="내용을 입력해 주세요."
          />
          <CheckBtn onClick={checkValidation}>인증 확인</CheckBtn>
        </InputBox>
        {isNotValid && <IsNotMail>입력 내용이 올바르지 않습니다.</IsNotMail>}
        {userId && (
          <IdBox>
            <p>회원님의 아이디는</p>
            <IdText>{userId}</IdText>
            <p>입니다</p>
          </IdBox>
        )}
        <BtnContainer>
          <BtnLogin onClick={handleLogin}>로그인</BtnLogin>
          <BtnPW onClick={handlePW}>비밀번호 찾기</BtnPW>
        </BtnContainer>
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
  margin-bottom: 0.7rem;
`;

const Email = styled.p`
  font-weight: 800;
  margin: 0.5rem;
`;

const ContainerBox = styled.div`
  border: 2px solid #d2e2ec;
  border-radius: 20px;
  padding: 3rem 8rem;
`;

const InputBox = styled.div`
  display: flex;
  justify-content: center;
  margin-bottom: 1.5rem;
`;

const Input = styled.input`
  width: 100%;
  padding: 0.5rem 1rem;
  margin-right: 1.5rem;
  border: 1px solid #d2e2ec;
`;

const InputValid = styled.input`
  width: 100%;
  padding: 0.5rem 1rem;
  margin-right: 1.5rem;
  border: 1px solid #d2e2ec;
  background-color: #d2e2ec;
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

const IsNotMail = styled.p`
  color: #eb3e63;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 1.2rem;
`;

const IsMail = styled.p`
  color: #1f7ceb;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 1.2rem;
`;

const IdBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 4rem;
`;

const IdText = styled.p`
  color: #1f7ceb;
  font-weight: 800;
  font-size: 1.2rem;
  margin: 0.2rem;
`;

const BtnContainer = styled.div`
  margin-top: 3rem;
  display: flex;
  justify-content: center;
`;

const BtnLogin = styled.button`
  width: 45%;
  background-color: #dae9fc;
  margin-right: 0.5rem;
  border-radius: 25px;
  border: none;
  padding: 0.3rem 0.6rem;

  &:hover {
    cursor: pointer;
  }
`;

const BtnPW = styled.button`
  width: 45%;
  background-color: #1f7ceb;
  color: white;
  margin-left: 0.5rem;
  border-radius: 25px;
  padding: 0.3rem 0.6rem;
  border: none;

  &:hover {
    cursor: pointer;
  }
`;
