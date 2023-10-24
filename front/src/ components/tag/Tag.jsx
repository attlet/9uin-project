import React from 'react';
import styled from 'styled-components';

const TagBox = styled.div`
  padding: 0.3rem;
  display: flex;
  border: 1px solid #7cb2f3;
  border-radius: 2rem;
  margin: 0.2rem;
`;

const TagIcon = styled.div`
  background-size: cover;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0.2rem;
  border-radius: 100%;
`;

const TagName = styled.div`
  font-size: 0.7rem;
  font-weight: 600;
  color: #1f7ceb;
  padding: 0.2rem;
`;

export default function Tag({ skill }) {
  const imageName = `${skill}.png`; // skill 값을 사용하여 이미지 파일 이름 생성
  return (
    <TagBox>
      <TagIcon
        style={{
          backgroundImage: `url('/tag/${skill}.png')`,
        }}
      />
      <TagName>{skill}</TagName>
    </TagBox>
  );
}
