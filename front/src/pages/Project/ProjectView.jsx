import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Tag from '../../ components/tag/Tag';
import axios from 'axios';
import Post from '../../ components/Post';
import useFetchData from '../../ components/hooks/getPostList';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { createAxiosInstance } from '../../api/instance';

export default function ProjectView() {
  const [projectList, setProjectList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [page, setPage] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const axiosInstance = createAxiosInstance(null, page);
        const response = await axiosInstance.get('/boards');
        setProjectList(response.data);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    fetchData();
  }, [page]);

  console.log(projectList.filter((project) => project.type === '프로젝트'));

  if (error) return <p>{error}</p>;

  return (
    <section>
      <Header>
        <InputContainer>
          <Icon></Icon>
          <SearchInput type="text" placeholder="제목, 게시글 검색" />
        </InputContainer>
        <TagContainer>
          <TagIcon></TagIcon>
          <TagBox>
            <Tag skill="JavaScript" />
            <Tag skill="TypeScript" />
            <Tag skill="React" />
            <Tag skill="Vue" />
            <Tag skill="Svelte" />
            <Tag skill="Next.js" />
            <Tag skill="Node.js" />
            <Tag skill="Java" />
            <Tag skill="Go" />
            <Tag skill="Spring" />
            <Tag skill="Nest.js" />
            <Tag skill="Figma" />
            <Tag skill="XD" />
          </TagBox>
        </TagContainer>
        <Part>
          <PartIcon></PartIcon>
          <PartList>
            <PartContent>전체</PartContent>
            <PartContent>PM</PartContent>
            <PartContent>디자이너</PartContent>
            <PartContent>프론트</PartContent>
            <PartContent>백엔드</PartContent>
            <PartContent>모바일</PartContent>
            <PartContent>기타</PartContent>
          </PartList>
        </Part>
      </Header>
      <SortContainer>
        <SortBox>
          <SortIcon></SortIcon>
          <SortText>정렬</SortText>
          <Select name="order">
            <option>업데이트 순</option>
            <option>인기 순</option>
          </Select>
        </SortBox>
        <ToggleBox>
          <ToggleText>모집 중</ToggleText>
          <ToggleBtn role="switch" type="checkbox" />
        </ToggleBox>
      </SortContainer>
      <ProjectGrid>
        {loading ? (
          <p>Loading...</p>
        ) : (
          projectList
            .filter((post) => post.type === '프로젝트')
            .map((post) => (
              <div onClick={() => navigate(`/postDetail/${post.board_id}`)}>
                <Post
                  key={post.board_id}
                  title={post.title}
                  type={post.type}
                  period={post.period}
                  roles={post.roles}
                  proceed_method={post.proceed_method}
                  username={post.username}
                  tags={post.tags}
                  createAt={post.createAt}
                  view_cnt={post.view_cnt}
                />
              </div>
            ))
        )}
      </ProjectGrid>
      <ul>
        <li onClick={() => setPage(0)}>1</li>
        <li onClick={() => setPage(1)}>2</li>
      </ul>
    </section>
  );
}

const Header = styled.div`
  margin: 1rem 1.5rem;
  border: 2px solid #dae9fc;
  border-radius: 15px;
`;

const InputContainer = styled.div`
  display: flex;
  padding: 0.5rem;
`;

const Icon = styled.div`
  background: url('icons/search.png');
  background-size: cover;
  width: 1rem;
  height: 1rem;
  margin: 0.5rem;
`;

const SearchInput = styled.input`
  border: none;
`;

const TagContainer = styled.div`
  display: flex;
  padding: 0.5rem;
`;

const TagBox = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-items: center;
`;

const TagIcon = styled.div`
  background: url('icons/tag.png');
  background-size: cover;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0.5rem;
  margin-right: 0.8rem;
`;

const Part = styled.div`
  display: flex;
  align-items: center;
`;

const PartIcon = styled.div`
  background: url('icons/part.png');
  background-size: cover;
  width: 0.9rem;
  height: 0.9rem;
  margin: 0.5rem;
  margin-right: 0.8rem;
  margin-left: 1rem;
`;

const PartList = styled.div`
  display: flex;
`;

const PartContent = styled.p`
  margin: 0.3rem 0.5rem;
  font-size: 0.7rem;
`;

const SortContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

const SortBox = styled.div`
  display: flex;
  align-items: center;
`;

const SortIcon = styled.div`
  background: url('icons/sort.png');
  background-size: cover;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0.5rem;
  margin-right: 0.8rem;
  margin-left: 2rem;
`;

const SortText = styled.p`
  font-size: 0.7rem;
  margin-right: 0.5rem;
`;

const Select = styled.select`
  font-size: 0.7rem;
  border: 2px solid #7cb2f3;
  border-radius: 1rem;
  padding: 0.3rem 0.5rem;
`;

const ToggleBox = styled.label`
  display: flex;
  align-items: center;
  margin-right: 0.5rem;
`;

const ToggleText = styled.span`
  font-size: 0.7rem;
  margin-right: 0.3rem;
`;

const ToggleBtn = styled.input`
  display: flex;
  align-items: center;
  justify-content: center;
  appearance: none;
  position: relative;
  border: max(2px, 0.1em) solid #7cb2f3;
  border-radius: 1.25em;
  width: 2.25em;
  height: 1.25em;

  &::before {
    content: '';
    position: absolute;
    left: 0;
    width: 1em;
    height: 1em;
    border-radius: 50%;
    transform: scale(0.8);
    background-color: #7cb2f3;
    transition: left 250ms linear;
  }

  &:checked {
    background-color: tomato;
    border-color: tomato;
  }

  &:checked::before {
    background-color: white;
    left: 1em;
  }

  &:focus-visible {
    outline-offset: max(2px, 0.1em);
    outline: max(2px, 0.1em) solid tomato;
  }
`;

// ProjectGrid Styles
const ProjectGrid = styled.div`
  margin-top: 1rem;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 1rem;

  @media (max-width: 768px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (min-width: 769px) and (max-width: 1024px) {
    grid-template-columns: repeat(3, 1fr);
  }
`;
