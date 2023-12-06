import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { refreshTokenAndRetry } from '../api/user';
import { createAxiosInstance } from '../api/instance';

export default function AddPost() {
  const axiosInstance = createAxiosInstance(localStorage.getItem('token'));

  const [formData, setFormData] = useState({
    title: '',
    skill: '',
    type: '',
    text: '',
    proceed_method: '',
    period: '',
    tagDtoList: [],
    pmCnt: 0,
    mobileCnt: 0,
    designerCnt: 0,
    frontEndCnt: 0,
    backEndCnt: 0,
    etcCnt: 0,

    projectBtn: false,
    studyBtn: false,
  });

  const navigate = useNavigate();

  const handleProejectBtn = () => {
    setFormData((prevData) => ({
      ...prevData,
      type: '프로젝트',
      projectBtn: true,
      studyBtn: false,
    }));
  };

  const handleStudyBtn = () => {
    setFormData((prevData) => ({
      ...prevData,
      type: '스터디',
      projectBtn: false,
      studyBtn: true,
    }));
  };

  const handleAddTags = (e) => {
    if (formData.skill) {
      setFormData((prevData) => ({
        ...prevData,
        tagDtoList: [...prevData.tagDtoList, formData.skill],
        skill: '',
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const roleNeededDtoList = [];

    if (formData.pmCnt > 0) {
      roleNeededDtoList.push({
        name: 'PM',
        pre_cnt: 0,
        want_cnt: formData.pmCnt,
      });
    }

    if (formData.mobileCnt > 0) {
      roleNeededDtoList.push({
        name: '모바일',
        pre_cnt: 0,
        want_cnt: formData.mobileCnt,
      });
    }

    if (formData.designerCnt > 0) {
      roleNeededDtoList.push({
        name: 'designer',
        pre_cnt: 0,
        want_cnt: formData.designerCnt,
      });
    }

    if (formData.frontEndCnt > 0) {
      roleNeededDtoList.push({
        name: 'frontend',
        pre_cnt: 0,
        want_cnt: formData.frontEndCnt,
      });
    }

    if (formData.backEndCnt > 0) {
      roleNeededDtoList.push({
        name: 'backend',
        pre_cnt: 0,
        want_cnt: formData.backEndCnt,
      });
    }

    if (formData.etcCnt > 0) {
      roleNeededDtoList.push({
        name: '기타',
        pre_cnt: 0,
        want_cnt: formData.etcCnt,
      });
    }

    // 유효성 검사
    if (!formData.type) {
      alert('프로젝트 또는 스터디를 선택해주세요.');
      return;
    }

    if (!formData.title) {
      alert('제목을 입력해주세요.');
      return;
    }

    if (!formData.period) {
      alert('마감 기한을 입력해주세요.');
      return;
    }

    const postData = {
      type: formData.type,
      title: formData.title,
      text: formData.text,
      proceed_method: formData.proceed_method,
      period: new Date(formData.period).toISOString(),
      tagDtoList: formData.tagDtoList,
      roleNeededDtoList,
    };

    console.log(postData);

    try {
      const response = await axiosInstance.post('/boards', postData);

      alert('글 작성 성공');
      navigate('/');
      console.log('글 작성 성공');
    } catch (error) {
      console.error('글 작성 실패', error);
      console.log(error.response.data);
      if (error.response.data.status === '401') {
        console.log(error.response.data.msg);

        try {
          // TODO refresh token 코드 리팩토링
          const retryResponse = await refreshTokenAndRetry(
            'post',
            '/boards',
            postData,
            {
              'X-AUTH-TOKEN': localStorage.getItem('token'),
            }
          );
          console.log(retryResponse);
        } catch (retryError) {
          console.log(retryError);
        }
      }
    }
  };

  return (
    <Section>
      <Recruitment>
        <span>모집구분</span>
        <div>
          <button
            style={{ borderRadius: ' 8px 0px 0px 8px' }}
            className={formData.projectBtn ? 'btnActive' : 'btn'}
            onClick={handleProejectBtn}
          >
            프로젝트
          </button>
          <button
            style={{ borderRadius: ' 0px 8px 8px 0px' }}
            className={formData.studyBtn ? 'btnActive' : 'btn'}
            onClick={handleStudyBtn}
          >
            스터디
          </button>
        </div>
      </Recruitment>
      <form onSubmit={handleSubmit}>
        <Title>
          <span>글 제목</span>
          <input
            placeholder="내용을 입력해 주세요."
            type="text"
            value={formData.title}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                title: e.target.value,
              }))
            }
          />
        </Title>
        <DateContainer>
          <span>마감 날짜</span>
          <input
            value={formData.period}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                period: e.target.value,
              }))
            }
            type="date"
          />
        </DateContainer>
        <Skill>
          <span>사용 기술</span>
          <select
            name="stack"
            id=""
            value={formData.skill}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                skill: e.target.value,
              }))
            }
          >
            <option value="">선택</option>
            <option value="react">react</option>
            <option value="Spring">spring</option>
            <option value="javascript">javascript</option>
            <option value="flutter">flutter</option>
          </select>
          <PlusBtn onClick={handleAddTags}></PlusBtn>
          <div className="tags">
            {formData.tagDtoList.map((tagName, index) => (
              <div key={index}>{tagName}</div>
            ))}
          </div>
        </Skill>
        <Playing>
          <span>진행 방식</span>
          <div>
            <input
              type="text"
              placeholder="내용을 입력해 주세요."
              name=""
              id=""
              value={formData.proceed_method}
              onChange={(e) =>
                setFormData((prevData) => ({
                  ...prevData,
                  proceed_method: e.target.value,
                }))
              }
            />
          </div>
        </Playing>
        <People>
          <span>인원 모집</span>
          <div>
            <div className="people_section">
              <div className="people_title">PM</div>
              <select
                name="pm"
                id=""
                value={formData.pmCnt}
                onChange={(e) =>
                  setFormData((prevData) => ({
                    ...prevData,
                    pmCnt: e.target.value,
                  }))
                }
              >
                <option value="">선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4명이상</option>
              </select>
            </div>
            <div className="people_section">
              <div className="people_title">디자이너</div>
              <select
                name="designer"
                value={formData.designerCnt}
                onChange={(e) =>
                  setFormData((prevData) => ({
                    ...prevData,
                    designerCnt: e.target.value,
                  }))
                }
              >
                <option value="">선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4명이상</option>
              </select>
            </div>
            <div className="people_section">
              <div className="people_title">프론트엔드</div>
              <select
                name="frontEnd"
                value={formData.frontEndCnt}
                onChange={(e) =>
                  setFormData((prevData) => ({
                    ...prevData,
                    frontEndCnt: e.target.value,
                  }))
                }
              >
                <option value="">선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4명이상</option>
              </select>
            </div>
            <div className="people_section">
              <div className="people_title">백엔드</div>
              <select
                name="backEnd"
                value={formData.backEndCnt}
                onChange={(e) =>
                  setFormData((prevData) => ({
                    ...prevData,
                    backEndCnt: e.target.value,
                  }))
                }
              >
                <option value="">선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4명이상</option>
              </select>
            </div>
            <div className="people_section">
              <div className="people_title">모바일</div>
              <select
                name="mobile"
                value={formData.mobileCnt}
                onChange={(e) =>
                  setFormData((prevData) => ({
                    ...prevData,
                    mobileCnt: e.target.value,
                  }))
                }
              >
                <option value="">선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4명이상</option>
              </select>
            </div>
            <div className="people_section">
              <div className="people_title">기타</div>
              <select
                name="etc"
                value={formData.etcCnt}
                onChange={(e) =>
                  setFormData((prevData) => ({
                    ...prevData,
                    etcCnt: e.target.value,
                  }))
                }
              >
                <option value="">선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4명이상</option>
              </select>
            </div>
          </div>
        </People>
        <TextArea>
          <span>글 내용</span>
          <textarea
            name="content"
            id=""
            cols="30"
            rows="10"
            placeholder="내용을 입력해 주세요."
            value={formData.text}
            onChange={(e) =>
              setFormData((prevData) => ({
                ...prevData,
                text: e.target.value,
              }))
            }
          ></textarea>
        </TextArea>
        <Submit>
          <button>글 작성하기</button>
        </Submit>
      </form>
    </Section>
  );
}

const Section = styled.section`
  margin: auto;
  width: 100%;
  max-width: 800px;
  margin-top: 70px;
  display: flex;
  flex-direction: column;
  gap: 40px;
  span {
    display: inline-block;
    width: 90px;
    margin-right: 10px;
  }

  @media only screen and (max-width: 1325px) {
    width: 600px;
  }
`;

const Recruitment = styled.div`
  display: flex;
  align-items: center;

  div {
    width: 700px;
  }

  .btn {
    background-color: #1f7ceb;
    height: 42px;
    color: white;
    padding: 0.5rem 1rem;
    border: 1px solid #d2e2ec;
    width: 126px;
    cursor: pointer;
  }

  .btnActive {
    width: 6rem;
    background-color: #08438c;
    height: 42px;
    color: white;
    padding: 0.5rem 1rem;
    width: 126px;
    cursor: pointer;
    border: 1px solid #d2e2ec;
  }
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  padding-bottom: 30px;
  input {
    width: 130px;
    height: 22px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
    padding: 0.5rem;
  }
`;

const Title = styled.div`
  display: flex;
  align-items: center;
  padding-bottom: 30px;
  input {
    width: 700px;
    height: 21px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
    padding: 1rem;
  }
`;
const Playing = styled.div`
  display: flex;
  align-items: center;
  padding-bottom: 30px;

  div {
    width: 700px;
    height: 42px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
    display: flex;
    justify-content: center;
    align-items: center;
    input {
      margin: 0;
      padding: 5px;
      outline: none;
      background: none;
      flex: 0.95;
      border: none;
    }
  }
  button {
    margin-left: 12px;
    width: 88px;
    height: 42px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: #fff;
    box-shadow: 0px 0px 0px 0px #cbdafc;
    color: #1f7ceb;
  }
`;

const Skill = styled.div`
  display: flex;
  align-items: center;
  padding-bottom: 30px;
  select {
    width: 160px;
    height: 42px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
  }

  .tags {
    display: flex;
    gap: 0.8rem;
    margin-left: 1rem;
  }
`;

const People = styled.div`
  display: flex;
  padding-bottom: 30px;
  .people_section {
    display: flex;
    margin-bottom: 10px;
    gap: 10px;
  }

  .people_title {
    width: 150px;
    height: 42px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
    line-height: 42px;
    padding-left: 10px;
  }

  select {
    width: 160px;
    height: 42px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
  }
`;

const PlusBtn = styled.div`
  margin-left: 10px;
  cursor: pointer;
  background-image: url('/icons/plus.png');
  background-size: cover;
  width: 1.5rem;
  height: 1.5rem;
}
`;

const TextArea = styled.div`
  display: flex;
  padding-bottom: 30px;

  textarea {
    width: 710px;
    border-radius: 5px;
    border: 1px solid #d2e2ec;
    background: var(--bs-white, #fff);
    box-shadow: 0px 0px 0px 0px #cbdafc;
    padding: 0.5rem;
  }
`;

const Submit = styled.div`
  width: 100%;
  display: flex;
  margin: 30px auto;
  text-align: center;
  align-items: center;
  justify-content: center;
  gap: 20px;

  button:first-child {
    border-radius: 5px;
    border: 2px solid #d2e2ec;
    background: #fff;
    box-shadow: 0px 0px 0px 0px #cbdafc;
    display: flex;
    width: 100px;
    height: 42px;
    padding: 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    flex-shrink: 0;
    color: #1f7ceb;
    font-size: 16px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    cursor: pointer;
  }
  button:nth-child(2) {
    border-radius: 5px;
    border: 2px solid #d2e2ec;
    background: #1f7ceb;
    box-shadow: 0px 0px 0px 0px #cbdafc;
    display: flex;
    width: 100px;
    height: 42px;
    padding: 10px;
    justify-content: center;
    align-items: center;
    gap: 10px;
    flex-shrink: 0;
    color: white;
    font-size: 16px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    cursor: pointer;
  }
`;
