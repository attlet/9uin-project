import React, { useEffect, useState } from 'react';
import Logo from '../ components/header/Logo';
import Post from '../ components/Post';
import styles from './Home.module.css';
import axios from 'axios';
import useFetchData from '../ components/hooks/getPostList';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchClipList } from '../modules/user';
import { createAxiosInstance } from '../api/instance';
import { styled } from 'styled-components';

export default function HomeView() {
  const [postList, setPostList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [page, setPage] = useState(0);

  // 로그인 후 처음 home => clipList 받아옴
  useEffect(() => {
    dispatch(fetchClipList());
  }, [dispatch]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const axiosInstance = createAxiosInstance(null, page);
        const response = await axiosInstance.get('/boards');
        setPostList(response.data);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    fetchData();
  }, [page]);

  const handleClick = (board_id) => {
    navigate(`/postDetail/${board_id}`);
  };

  if (loading) return <p>Loading...</p>;

  if (error) return <p>{error}</p>;

  return (
    <>
      <Logo />
      <div className={styles.projectGrid}>
        {postList.length > 0 &&
          postList.map((post) => (
            <div onClick={() => handleClick(post.board_id)}>
              <Post
                key={post.board_id}
                title={post.title}
                type={post.type}
                roles={post.roles}
                period={post.period}
                proceed_method={post.proceed_method}
                username={post.username}
                tags={post.tags}
                board_id={post.board_id}
                view_cnt={post.view_cnt}
                createAt={post.createAt}
              />
            </div>
          ))}
      </div>
      <PageBox>
        <PageIndex onClick={() => setPage(0)}>1</PageIndex>
        <PageIndex onClick={() => setPage(1)}>2</PageIndex>
      </PageBox>
    </>
  );
}

const PageBox = styled.ul`
  list-style: none;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const PageIndex = styled.li`
  list-style: none;
  border: 1px solid #7cb2f3;
  border-radius: 20px;
  margin: 0.2em;
  padding: 0.2rem 0.4rem;
  font-size: 0.9rem;

  &:hover {
    background-color: tomato;
    color: white;
    border: none;
    transform: scale(1.2);
    transition: all 100ms ease-in-out;
  }
`;
