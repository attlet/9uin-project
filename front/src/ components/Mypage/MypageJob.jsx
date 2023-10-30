import React, { useState } from 'react';
import styled from 'styled-components';
import MypageModal from './MypageModal';
import axios from 'axios';
import { getNewTokens } from '../../api/refreshToken';
import { createAxiosInstance } from '../../api/instance';

const MypageJob = ({ token }) => {
  const axiosInstance = createAxiosInstance(token);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [jobFields, setJobFields] = useState([
    {
      id: 1,
      jobname: '',
      startDate: '',
      endDate: '',
      stack: [],
      description: '',
    },
  ]);
  const [selectedStack, setSelectedStack] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handlePost = async () => {
    try {
      setIsLoading(true);

      const promises = jobFields.map(async (job) => {
        const data = {
          company_name: job.jobname,
          join_date: job.startDate,
          leave_date: job.endDate,
          job_explanation: job.description,
          skill_in_job: job.stack,
        };

        const response = await axiosInstance.post('/job_ex', data);
        // const response = await axios.post('http://1.246.104.170:8080/job_ex', data, {
        //   headers: {
        //     'X-AUTH-TOKEN': token
        //   }
        // });

        console.log(response.data);
      });

      await Promise.all(promises);
    } catch (error) {
      console.error(error);
      if (error.response && error.response.status === 401) {
        const { accessToken, refreshToken } = await getNewTokens();

        const promises = jobFields.map(async (field) => {
          const data = {
            certificate_name: field.name,
            acquisition_date: field.startDate,
            expire: field.endDate,
            explanation: field.description,
          };

          const refreshAxios = createAxiosInstance(refreshToken);
          const response = refreshAxios.post('/job_ex', data);
          // const response = await axios.post(
          //   'http://1.246.104.170:8080/job_ex',
          //   data,
          //   {
          //     headers: {
          //       'X-AUTH-TOKEN': refreshToken,
          //     },
          //   }
          // );

          console.log(response.data);
        });

        await Promise.all(promises);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const skillImage = {
    react: 'React.png',
    Spring: 'Spring.png',
    javascript: 'JS.png',
  };

  const handleFieldChange = (index, field, value) => {
    const updatedFields = [...jobFields];
    updatedFields[index][field] = value;
    setJobFields(updatedFields);
  };

  const addJobField = () => {
    const newField = {
      id: Date.now(),
      jobname: '',
      startDate: '',
      endDate: '',
      stack: [],
      description: '',
    };
    setJobFields([...jobFields, newField]);
  };

  const handleAddStack = (index, selectedStack) => {
    const updatedFields = [...jobFields];
    const stackToAdd = selectedStack.trim();

    if (stackToAdd && !updatedFields[index].stack.includes(stackToAdd)) {
      updatedFields[index].stack.push(stackToAdd);
      setJobFields(updatedFields);
    } else {
      alert('이미 추가한 스택입니다.');
    }
  };

  const handleRemoveSkill = (jobIndex, skillIndex) => {
    const shouldRemoveSkill =
      window.confirm(`사용한 기술 스택을 삭제하시겠습니까?`);

    if (shouldRemoveSkill) {
      const updatedFields = [...jobFields];
      updatedFields[jobIndex].stack.splice(skillIndex, 1);
      setJobFields(updatedFields);
    }
  };

  const titleContent = (
    <TitleContentStyled>
      <p></p>
      <h2>직무 경혐 등록</h2>
      <p></p>
    </TitleContentStyled>
  );

  const bodyContent = (
    <BodyContentStyled>
      {jobFields.map((field, index) => (
        <div key={field.id} className="job-field">
          <div className="section1">
            <div>
              <span>직무 이름</span>
              <input
                type="text"
                value={field.jobname}
                onChange={(e) =>
                  handleFieldChange(index, 'jobname', e.target.value)
                }
              />
            </div>
            <div>
              <span>재직 기간</span>
              <p className="date">
                <input
                  type="date"
                  value={field.startDate}
                  onChange={(e) =>
                    handleFieldChange(index, 'startDate', e.target.value)
                  }
                />
                <p className="date_p">~</p>
                <input
                  type="date"
                  value={field.endDate}
                  onChange={(e) =>
                    handleFieldChange(index, 'endDate', e.target.value)
                  }
                />
              </p>
            </div>
          </div>
          <div className="section2">
            <span>사용한 기술 스택</span>
            <div className="section2_select">
              <select
                name="stack"
                id=""
                value={selectedStack}
                onChange={(e) => setSelectedStack(e.target.value)}
              >
                <option value="">선택</option>
                <option value="react">react</option>
                <option value="Spring">spring</option>
                <option value="javascript">javascript</option>
              </select>
              <button>
                <img
                  src="/icons/plus.png"
                  alt=""
                  onClick={() => handleAddStack(index, selectedStack)}
                />
              </button>
            </div>
          </div>
          <div className="skill_stack">
            {field.stack.map((skill, index) => (
              <div key={skill} onClick={() => handleRemoveSkill(index, index)}>
                {skillImage[skill] && (
                  <img
                    className="skill_image"
                    src={`/stack/${skillImage[skill]}`}
                    alt={skill}
                  />
                )}
              </div>
            ))}
          </div>
          <div className="section3">
            <span>직무 설명</span>
            <textarea
              type="text"
              value={field.description}
              onChange={(e) =>
                handleFieldChange(index, 'description', e.target.value)
              }
            />
          </div>
        </div>
      ))}
      <div className="section4">
        <button onClick={addJobField}>
          <img src="/icons/plus.png" alt="버튼" />
        </button>
        <button onClick={handlePost} className="add_btn">
          추가
        </button>
      </div>
    </BodyContentStyled>
  );
  return (
    <Section3>
      <div className="section3_title">
        직무 경험 <p className="title_length">2</p>
      </div>
      <div className="section3_flex">
        <div className="section3_container">
          <div className="section3_container_left">
            <div className="section3_container_leftright_flex">
              <span>회사 이름</span>
              <p>모나미</p>
            </div>
            <div className="section3_container_leftright_flex">
              <span>사용한 기술 스택</span>
              <p>
                <img src="stack/JS.png" width="24" height="24" alt="js icon" />
              </p>
            </div>
            <div className="section3_container_leftright_flex">
              <span>직무 설명</span>
              <p></p>
            </div>
          </div>
          <div className="section3_container_right">
            <div className="section3_container_leftright_flex">
              <span>재직 기간</span>
              <p>
                <div>2015.06</div>
                <div>~</div>
                <div>2018.03</div>
              </p>
            </div>
          </div>
        </div>
        <div className="section3_next">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            viewBox="0 0 20 20"
            fill="none"
          >
            <path
              d="M13.477 9.16591H3.33366V10.8326H13.477L9.00699 15.3026L10.1853 16.4809L16.667 9.99924L10.1853 3.51758L9.00699 4.69591L13.477 9.16591Z"
              fill="#1F7CEB"
            />
          </svg>
        </div>
      </div>
      <div className="section3_btn">
        <button>수정하기</button>
        <button onClick={openModal}>추가하기</button>
      </div>
      <MypageModal
        isOpen={isModalOpen}
        onClose={closeModal}
        titleContent={titleContent}
        bodyContent={bodyContent}
      />
    </Section3>
  );
};

const Section3 = styled.div`
  margin-top: 50px;
  .section3_title {
    color: #000;
    font-size: 16px;
    font-style: normal;
    font-weight: 400;
    line-height: normal;
    display: flex;
    gap: 5px;
    align-items: center;
    margin-bottom: 50px;
  }
  .title_length {
    color: #000;
    font-size: 16px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
  }

  .section3_flex {
    display: flex;
    gap: 10px;
    align-items: center;
  }

  .section3_container {
    width: 1280px;
    height: 344px;
    flex-shrink: 0;
    border-radius: 20px;
    border: 2px solid #dae9fc;
    display: flex;
    align-items: start;
    .section3_container_left,
    .section3_container_right {
      padding-top: 90px;
      width: 640px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 40px;

      .section3_container_leftright_flex {
        display: flex;
        justify-content: center;
        align-items: center;
        span {
          display: inline-block;
          width: 150px;
          color: #5c5c5c;
          font-size: 16px;
          font-style: normal;
          font-weight: 400;
          line-height: normal;
        }
        p {
          width: 300px;
          color: #000;
          font-size: 20px;
          font-style: normal;
          font-weight: 400;
          line-height: normal;
          display: flex;
          gap: 50px;
        }
      }
    }
  }
  .section3_next {
    width: 50px;
    height: 50px;
    flex-shrink: 0;
    border: 2px solid #d2e2ec;
    border-radius: 100%;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .section3_btn {
    margin-top: 50px;
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 30px;
    button {
      cursor: pointer;
      display: flex;
      width: 100px;
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
      color: #1f7ceb;
      font-family: SUITE Variable;
      font-size: 16px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
    }
  }

  @media only screen and (min-width: 768px) and (max-width: 1325px) {
    width: 750px;
    justify-content: center;
    margin: 20px;

    .section3_container {
      width: 680px;

      .section3_container_left,
      .section3_container_right {
        width: 250px;

        .section3_container_leftright_flex {
          span {
            width: 100px;
          }

          p {
            width: 90px;
          }
        }
      }
    }
  }
`;

const TitleContentStyled = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  padding-bottom: 60px;
  p {
    width: 16px;
    height: 16px;
    flex-shrink: 0;
    background-color: #b9bcc0;
    border-radius: 100%;
  }
`;

const BodyContentStyled = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-left: 50px;
  padding-right: 50px;

  .job-field {
    margin-bottom: 100px;
  }
  span {
    color: #000;
    font-family: SUITE Variable;
    font-size: 20px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    padding-bottom: 10px;
  }

  .skill_stack {
    display: flex;
    gap: 10px;
    div {
      img {
        width: 25px;
        height: 25px;
        cursor: pointer;
      }
    }
  }

  .section1 {
    display: flex;
    align-items: center;
    justify-content: center;
    div:first-child {
      display: flex;
      gap: 10px;
      flex-direction: column;
      input {
        display: inline-flex;
        height: 21px;
        width: 377px;
        border-radius: 5px;
        border: 1.5px solid #1f7ceb;
        background: var(--bs-white, #fff);
        box-shadow: 0px 0px 0px 0px #cbdafc;
      }
    }
    div:last-child {
      display: flex;
      gap: 10px;
      flex-direction: column;

      .date {
        display: flex;
        justify-content: space-around;
        align-items: center;
        width: 500px;
      }

      .date_p {
        width: 25px;
      }

      input {
        display: inline-flex;
        padding: none;
        height: 21px;
        width: 163px;
        border-radius: 5px;
        border: 1.5px solid #1f7ceb;
        background: var(--bs-white, #fff);
        box-shadow: 0px 0px 0px 0px #cbdafc;
      }
    }
  }

  .section2 {
    margin-top: 30px;
    display: flex;
    height: 50px;
    align-items: center;
    gap: 10px;

    .section2_select {
      display: flex;
      gap: 10px;
      align-items: center;
    }
    select {
      display: flex;
      width: 163px;
      height: 42px;
      padding: 10px;
      justify-content: flex-end;
      align-items: center;
      gap: 10px;
      flex-shrink: 0;
      border-radius: 5px;
      border: 1.5px solid #1f7ceb;
      background: var(--bs-white, #fff);
      box-shadow: 0px 0px 0px 0px #cbdafc;
    }

    button {
      width: 25px;
      height: 25px;
      background: none;
      border: none;
      cursor: pointer;
    }
  }

  .section3 {
    display: flex;
    flex-direction: column;
    margin-top: 30px;
    textarea {
      border-radius: 5px;
      border: 1.5px solid #1f7ceb;
      background: var(--bs-white, #fff);
      box-shadow: 0px 0px 0px 0px #cbdafc;
      height: 126px;
      width: 880px;
      padding: 10px;
    }
  }

  .section4 {
    margin-top: 30px;
    display: flex;
    justify-content: center;
    button {
      border: none;
      background: none;
      img {
        width: 26.88px;
        height: 26.88px;
      }
    }

    .add_btn {
      cursor: pointer;
      background-color: #1f7ceb;
      width: 70px;
      height: 30px;
      border-radius: 20px;
      color: white;
    }
  }

  @media only screen and (min-width: 768px) and (max-width: 1325px) {
    overflow-x: hidden;
    .section1 {
      justify-content: start;
      div:first-child {
        input {
          width: 100px;
        }
      }
      div:last-child {
        .date {
          display: flex;
          justify-content: start;
          align-items: center;
          width: 500px;
        }
        input {
          width: 160px;
        }
      }
    }
    .section3 {
      textarea {
        width: 575px;
      }
    }
  }
`;
export default MypageJob;
