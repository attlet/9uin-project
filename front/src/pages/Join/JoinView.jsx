import React, { useState } from 'react';
import styles from './JoinView.module.css';
import { useNavigate } from 'react-router-dom';
import { createAxiosInstance } from '../../api/instance';

export default function JoinView() {
  const axiosInstance = createAxiosInstance();

  const [mail, setMail] = useState('');
  const [validCode, setValidCode] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rePassword, setRePassword] = useState('');

  const [isMailCheck, setIsMailCheck] = useState(false);
  const [isIdCheck, setIsIdCheck] = useState(false);

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  const navigate = useNavigate();

  const handleMailChange = (e) => {
    setMail(e.target.value);
  };

  const handleValidCodeChange = (e) => {
    setValidCode(e.target.value);
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleRePasswordChange = (e) => {
    setRePassword(e.target.value);
  };

  const handleValidCodeSend = async () => {
    const mailInfo = {
      mail,
    };

    if (emailRegex.test(mail)) {
      try {
        const response = await axiosInstance.post(
          '/sign/sign-up/validCodeSend',
          mailInfo
        );
        console.log(response);
        alert('인증번호를 전송했습니다.');
      } catch (error) {
        console.error('메일 인증코드 전송실패', error);
      }
    } else {
      alert('유효한 이메일을 입력해주세요.');
    }
  };

  const handleValidCodeCheck = async () => {
    const validInfo = {
      mail,
      validCode,
    };

    try {
      const response = await axiosInstance.post(
        '/sign/sign-up/validMail',
        validInfo
      );
      console.log(response);
      setIsMailCheck(true);
      alert('인증이 완료되었습니다.');
    } catch (error) {
      console.error('메일 인증코드 검증실패', error);
      setIsMailCheck(false);
      alert('입력하신 인증번호가 올바르지 않습니다.');
    }
  };

  const handleIdCheck = async () => {
    const userIdInfo = {
      username,
    };

    try {
      const response = await axiosInstance.post(
        '/sign/sign-up/checkId',
        userIdInfo
      );
      console.log(response);
      setIsIdCheck(true);
      alert('사용가능한 아이디입니다.');
    } catch (error) {
      console.error('아이디 체크 실패', error);
      setIsIdCheck(false);
      alert('다른 아이디를 입력해주세요.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isMailCheck) {
      alert('메일 인증을 해주세요.');
      return;
    }

    if (!isIdCheck) {
      alert('아이디 중복확인을 해주세요');
      return;
    }

    if (!username) {
      alert('아이디를 입력해주세요.');
      return;
    }

    if (!password) {
      alert('비밀번호를 입력해주세요.');
      return;
    }

    if (password !== rePassword) {
      alert('입력하신 비밀번호가 일치하지 않습니다.');
      return;
    }

    console.log('회원가입 정보:', {
      username,
      password,
      mail,
      role: 'user',
    });
    const userData = { username, password, mail, role: 'user' };

    try {
      const response = await axiosInstance.post('/sign/sign-up', userData);
      console.log('회원가입 성공', response);
      alert('회원가입을 성공했습니다!');
      navigate('/joinSuccess');
    } catch (error) {
      console.error('회원가입 실패', error);
    }
  };

  return (
    <section className={styles.container}>
      <p className={styles.title}>회원가입</p>
      <form className={styles.container__box}>
        <div>
          <p className={styles.title__sub}>이메일 주소</p>
          <div className={styles.input__box}>
            <input
              className={styles.input__content}
              type="email"
              placeholder="내용을 입력해 주세요."
              onChange={handleMailChange}
            />
            <button
              type="button"
              onClick={handleValidCodeSend}
              className={styles.check__btn}
            >
              인증요청
            </button>
          </div>
        </div>
        <div>
          <div className={styles.input__box}>
            <input
              className={styles.input__valid}
              type="text"
              placeholder="내용을 입력해 주세요."
              onChange={handleValidCodeChange}
            />
            <button
              onClick={handleValidCodeCheck}
              className={styles.check__btn}
              type="button"
            >
              인증하기
            </button>
          </div>
        </div>
        <div>
          <p className={styles.title__sub}>아이디 설정</p>
          <div className={styles.input__box}>
            <input
              className={styles.input__content}
              type="text"
              placeholder="내용을 입력해 주세요."
              onChange={handleUsernameChange}
            />
            <button
              onClick={handleIdCheck}
              className={styles.check__btn}
              type="button"
            >
              중복확인
            </button>
          </div>
        </div>
        <div className={styles.pw__containter}>
          <p className={styles.title__sub}>비밀번호 설정</p>
          <input
            className={styles.input__pw}
            type="password"
            placeholder="내용을 입력해 주세요."
            onChange={handlePasswordChange}
            autoComplete="new-password"
          />
        </div>
        <div className={styles.pw__containter}>
          <p className={styles.title__sub}>비밀번호 확인</p>
          <input
            className={styles.input__pw}
            type="password"
            placeholder="내용을 입력해 주세요."
            onChange={handleRePasswordChange}
            autoComplete="new-password"
          />
        </div>
        <div className={styles.btn__container}>
          <button onClick={handleSubmit} className={styles.btn__join}>
            가입하기
          </button>
        </div>
      </form>
    </section>
  );
}
