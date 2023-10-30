import React, { useState } from 'react';
import styles from './JoinView.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { createAxiosInstance } from '../../api/instance';

export default function JoinView() {
  const [mail, setMail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  const handleMailChange = (e) => {
    setMail(e.target.value);
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('회원가입 정보:', {
      username,
      password,
      mail,
      role: 'user',
    });
    const userData = { username, password, mail, role: 'user' };

    try {
      const axiosInstance = createAxiosInstance();
      const response = await axiosInstance.post('/sign/sign-up', userData);
      console.log('회원가입 성공', response);
      navigate('/joinSuccess');
    } catch (error) {
      console.error('회원가입 실패', error);
    }
  };

  return (
    <section className={styles.container}>
      <p className={styles.title}>회원가입</p>
      <form className={styles.container__box} onSubmit={handleSubmit}>
        <div>
          <p className={styles.title__sub}>이메일 주소</p>
          <div className={styles.input__box}>
            <input
              className={styles.input__content}
              type="email"
              placeholder="내용을 입력해 주세요."
              name=""
              id="mail"
              onChange={handleMailChange}
            />
            <button className={styles.check__btn}>인증하기</button>
          </div>
        </div>
        <div>
          <p className={styles.title__sub}>아이디 설정</p>
          <div className={styles.input__box}>
            <input
              className={styles.input__content}
              type="text"
              name=""
              id=""
              placeholder="내용을 입력해 주세요."
              onChange={handleUsernameChange}
            />
            <button className={styles.check__btn}>중복확인</button>
          </div>
        </div>
        <div className={styles.pw__containter}>
          <p className={styles.title__sub}>비밀번호 설정</p>
          <input
            className={styles.input__pw}
            type="password"
            name=""
            id=""
            placeholder="내용을 입력해 주세요."
            onChange={handlePasswordChange}
          />
        </div>
        <div className={styles.pw__containter}>
          <p className={styles.title__sub}>비밀번호 확인</p>
          <input
            className={styles.input__pw}
            type="password"
            name=""
            id=""
            placeholder="내용을 입력해 주세요."
          />
        </div>
        <div className={styles.btn__container}>
          <button className={styles.btn__join}>가입하기</button>
        </div>
      </form>
    </section>
  );
}
