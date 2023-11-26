import React from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const MypageEtc = ({ clipCnt, projectCnt, studyCnt }) => {
  const navigate = useNavigate();

  return (
    <MyPageEtc>
      <div
        onClick={() => navigate('/mypage/clip')}
        className="section1_etc_clip"
      >
        <div>
          <img src="icons/clip.png" width="24" height="24" alt="clip icon" />
          <p>클립 해놓은 게시물 {'>'}</p>
        </div>
        <div>{clipCnt}</div>
      </div>
      <div className="section1_etc_recruitment">
        <div
          onClick={() => navigate('/mypage/recruit')}
          className="section1_etc_recruitment_title"
        >
          <img src="icons/clip.png" width="24" height="24" alt="clip icon" />
          <p>모집 현황 {'>'}</p>
        </div>
        <div
          onClick={() => navigate('/mypage/recruit')}
          className="section1_etc_recruitment_content"
        >
          <div className="section1_etc_recruitment_content_project">
            <div>프로젝트</div>
            <div>{projectCnt}</div>
          </div>
          <div className="section1_etc_recruitment_content_study">
            <div>스터디</div>
            <div>{studyCnt}</div>
          </div>
        </div>
      </div>
    </MyPageEtc>
  );
};

const MyPageEtc = styled.div`
  display: flex;
  flex-direction: column;
  .section1_etc_clip,
  .section1_etc_recruitment {
    width: 240px;
    height: 200px;
    border-radius: 30px;
    border: 2px solid #dae9fc;
    background: #dae9fc;
    margin-bottom: 10px;
  }

  .section1_etc_clip {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 20px;

    div:first-child {
      display: flex;
      gap: 3px;
      color: #000;
      font-family: SUITE Variable;
      font-size: 18px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
      cursor: pointer;
    }
    div:last-child {
      color: #1f7ceb;
      font-family: SUITE Variable;
      font-size: 50px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
    }
  }

  .section1_etc_recruitment {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 20px;
    .section1_etc_recruitment_title {
      display: flex;
      gap: 3px;
      color: #000;
      font-family: SUITE Variable;
      font-size: 18px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
      cursor: pointer;
      padding-bottom: 15px;
      border-bottom: 1px solid #c2c2c2;
    }
    .section1_etc_recruitment_content {
      display: flex;
      .section1_etc_recruitment_content_project {
        width: 100px;
        text-align: center;
        div:last-child {
          color: #1f7ceb;
          font-family: SUITE Variable;
          font-size: 50px;
          font-style: normal;
          font-weight: 400;
          line-height: normal;
          text-align: center;
          border-right: 1px solid #c2c2c2;
        }
      }

      .section1_etc_recruitment_content_study {
        width: 100px;
        text-align: center;
        div:last-child {
          color: #1f7ceb;
          font-family: SUITE Variable;
          font-size: 50px;
          font-style: normal;
          font-weight: 400;
          line-height: normal;
          text-align: center;
        }
      }
    }
  }
`;
export default MypageEtc;
