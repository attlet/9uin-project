import React from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

export default function JoinSuccessView() {
  const navigate = useNavigate();

  return (
    <Container>
      <ContainerBox>
        <JoinText>회원가입이 완료되었습니다.</JoinText>
        <ProfileText>
          프로필을 작성하여 나를 소개하면서 프로젝트와 스터디 구인 매칭률을
          높여보세요!
        </ProfileText>
        <Button onClick={() => navigate('/mypage')}>프로필 작성하기</Button>
      </ContainerBox>
    </Container>
  );
}

const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ContainerBox = styled.div`
  margin-top: 5rem;
  border: 3px solid #d2e2ec;
  border-radius: 20px;
  padding: 3rem 8rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 230px;
  border-top-width: 40px;
  border-bottom-width: 40px;
`;

const JoinText = styled.p`
  margin-top: 3.2rem;
  margin-bottom: 1rem;
  font-family: 'SUITE Variable', sans-serif;
`;

const ProfileText = styled.p`
  margin-top: 0.3rem;
  margin-bottom: 2.5rem;
`;

const Button = styled.button`
  width: 42%;
  background-color: #1f7ceb;
  margin-top: 1.5rem;
  color: white;
  border-radius: 25px;
  padding: 0.8rem 0.6rem;
  border: none;
  cursor: pointer;

  &:hover {
    background-color: #08438c;
  }
`;
