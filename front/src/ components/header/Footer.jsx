import React from 'react';
import styled from 'styled-components';

const Footer = () => {
  return (
    <>
      <Bottom>
        <BottomImg>
          <img src="/logo/logoFooter.png" alt="dd" />
        </BottomImg>
        <BottomWriting>
          전체서비스｜이용약관｜개인정보처리방침｜통합검색｜고객센터
        </BottomWriting>
        <BottomLast>Copyright ⓒ 2023 9in. All rights reserved.</BottomLast>
      </Bottom>
    </>
  );
};

export default Footer;

const Bottom = styled.div`
  position: relative;
  transform: translateY(-%100);

  max-width: 1300px;
  width: 100%;
  margin: auto;
  height: 7rem;
`;

const BottomImg = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 2rem;
`;

const BottomWriting = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1rem;
`;

const BottomLast = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1rem;
`;
