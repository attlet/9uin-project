import React, { useState } from 'react'
import styled from 'styled-components';
import MypageModal from './MypageModal'
import axios from 'axios';
import { getNewTokens } from '../../api/refreshToken';
import { createAxiosInstance } from '../../api/instance';

const MypageLicese = ({token}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [licenseFields, setLicenseFields] = useState([
    { id: 1, name: '', startDate: '', endDate: '', description: '' },
  ]);
  const [isLoading, setIsLoading] = useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handleFieldChange = (index, field, value) => {
    const updatedFields = [...licenseFields];
    updatedFields[index][field] = value;
    setLicenseFields(updatedFields);
  };

  const addLicenseField = () => {
    const newField = { id: Date.now(), name: '', startDate: '', endDate: '', description: '' };
    setLicenseFields([...licenseFields, newField]);
  };
  
  const handlePost = async () => {
    try {
      setIsLoading(true);
  
      const promises = licenseFields.map(async (field) => {
        const data = {
          name: field.name,
          startDate: field.startDate,
          endDate: field.endDate,
          description: field.description,
        };
  
        const response = await axios.post('http://1.246.104.170:8080/certificate', data, {
          headers: {
            'X-AUTH-TOKEN': token
          }
        });
  
        console.log(response.data);
      });
  
      await Promise.all(promises);
    } catch (error) {
      console.error(error);
      if (error.response && error.response.status === 401) {
        const { accessToken, refreshToken } = await getNewTokens();

        const promises = licenseFields.map(async (field) => {
          const data = {
            name: field.name,
            startDate: field.startDate,
            endDate: field.endDate,
            description: field.description,
          };
    
          const response = await axios.post('http://1.246.104.170:8080/certificate', data, {
            headers: {
              'X-AUTH-TOKEN': refreshToken
            }
          });
    
          console.log(response.data);
        });

        await Promise.all(promises);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const titleContent = (
    <TitleContentStyled>
      <p></p>
      <h2>자격증 등록</h2>
      <p></p>
    </TitleContentStyled>
  );

  const bodyContent = (
    <BodyContentStyled>
      {licenseFields.map((field, index) => (
        <div key={field.id} className='license-field'>
          <div className='section1'>
            <div>
              <span>자격증 이름</span>
              <input
                type='text'
                value={field.name}
                onChange={(e) => handleFieldChange(index, 'name', e.target.value)}
              />
            </div>
            <div>
              <span>취득 일자</span>
              <input
                type='date'
                value={field.acquiredDate}
                onChange={(e) => handleFieldChange(index, 'acquiredDate', e.target.value)}
              />
            </div>
            <div>
              <span>(유효 기간)</span>
              <input
                type='date'
                value={field.expiryDate}
                onChange={(e) => handleFieldChange(index, 'expiryDate', e.target.value)}
              />
            </div>
          </div>
          <div className='section2'>
            <span>직무 설명</span>
            <textarea
              type='text'
              value={field.description}
              onChange={(e) => handleFieldChange(index, 'description', e.target.value)}
            />
          </div>
        </div>
      ))}
      <div className='section3'>
        <button onClick={addLicenseField}>
          <img src="/icons/plus.png" alt="버튼" />
        </button>
        <button onClick={handlePost} className='add_btn'>
          추가
        </button>
      </div>
    </BodyContentStyled>
  );
  return (
    <Licese>
      <div className="section2_title">
        자격증 현 <p className="title_length">1</p>
      </div>
      <div className="section2_container">
        <div className="section2_content">
          <div>토익</div>
          <div>880</div>
          <div>2022.02.22 ~ 2024.02.21</div>
        </div>
        <div onClick={openModal} className="section2_plus">+</div>
      </div>
      <MypageModal
        isOpen={isModalOpen}
        onClose={closeModal}
        titleContent={titleContent}
        bodyContent={bodyContent}
      />
    </Licese>
  )
}

const Licese = styled.div`
  margin-top: 100px;
  .section2_title {
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

  .section2_container {
    display: flex;
    align-items: center;
    gap: 50px;
  }

  .section2_content {
    width: 204px;
    height: 204px;
    flex-shrink: 0;
    border-radius: 15px;
    border: 2px solid #dae9fc;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 5px;

    div:first-child {
      padding-top: 5px;
      padding-bottom: 10px;
      width: 150px;
      border-bottom: 1.5px solid #dae9fc;
      display: flex;
      justify-content: center;
      gap: 3px;
      color: #000;
      font-family: SUITE Variable;
      font-size: 20px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
      cursor: pointer;
    }
    div:nth-child(2) {
      color: #1f7ceb;
      font-family: SUITE Variable;
      font-size: 50px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
      padding-bottom: 20px;
    }
    div:lasth-child {
      color: #5c5c5c;
      font-size: 12px;
      font-style: normal;
      font-weight: 400;
      line-height: normal;
    }
  }

  .section2_plus {
    cursor: pointer;
    width: 50px;
    height: 50px;
    flex-shrink: 0;
    display: flex;
    justify-content: center;
    border: 2px solid #d2e2ec;
    border-radius: 100%;
    font-size: 30px;
    color: #1f7ceb;
  }

  @media only screen and (min-width: 768px) and (max-width: 1325px) {
    width: 750px;
    justify-content: center;
    margin: 20px;
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
    background-color: #B9BCC0;
    border-radius: 100%;
  }
`;

const BodyContentStyled = styled.div`
  display: flex;
  flex-direction: column;
  padding-left: 50px;
  padding-right: 50px;
  span {
    color: #000;
    font-family: SUITE Variable;
    font-size: 20px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    padding-bottom: 10px;
  }
  .section1 {
    display: flex;
    gap: 10px;
    div:first-child {
      display:flex;
      flex-direction: column;
      gap: 20px;
      input {
        border-radius: 5px;
        border: 1.5px solid #1F7CEB;
        display: inline-flex;
        height: 21px;
        align-items: center;
        gap: 10px;
        flex-shrink: 0;
        width: 377px;
      }
    }
    div:nth-child(2) {
      display:flex;
      flex-direction: column;
      gap: 20px;
      input {
        width: 163px;
        border-radius: 5px;
        border: 1.5px solid #1F7CEB;
        display: inline-flex;
        height: 21px;
        align-items: center;
        gap: 10px;
        flex-shrink: 0;
      }
    }
    div:last-child {
      display:flex;
      flex-direction: column;
      gap: 20px;
      input {
        width: 163px;
        border-radius: 5px;
        border: 1.5px solid #1F7CEB;
        display: inline-flex;
        height: 21px;
        align-items: center;
        gap: 10px;
        flex-shrink: 0;
      }
    }
  }

  .section2 {
    padding-top: 30px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 100px;
    textarea {
      padding: 10px;
      width: 850px;
      border-radius: 5px;
      border: 1.5px solid #1F7CEB;
      background: var(--bs-white, #FFF);
      box-shadow: 0px 0px 0px 0px #CBDAFC;
      height: 126px;
    }
  }
  
  .section3{
    display: flex;
    justify-content: center;
    gap: 10px;
    align-items: center;

    button {
      cursor: pointer;
      border: none;
      background: none;
      padding-top: 5px;
      img {
        width: 26.88px;
        height: 26.88px;
      }
    }

    .add_btn {
      cursor: pointer;
      background-color: #1F7CEB;
      width: 70px;
      height: 30px;
      border-radius: 20px;
      color:white;
    }
  }

  @media only screen and (min-width: 768px) and (max-width: 1325px) {
  
    .section1{
      div:first-child {
        input {
          width: 150px;
        }
      }
    }

    .section2{
      textarea {
        width: 620px;
      }
    }
  }
`
export default MypageLicese
