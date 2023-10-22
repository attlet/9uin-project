import React, { useEffect } from 'react';
import styled from 'styled-components';
import Post from '../../ components/Post';
import { useDispatch, useSelector } from 'react-redux';
import { fetchClipList } from '../../modules/user';

const HeaderBox = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 2rem;
  margin: 1rem 2rem;
`;

const Icon = styled.div`
  background: url('/icons/prev.png');
  background-size: cover;
  width: 2.5rem;
  height: 2.5rem;
  margin-left: 8rem;
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

  &:before {
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

const ProjectGrid = styled.div`
  margin-top: 1rem;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 1rem;

  padding: 3rem;
`;

export default function ClipView() {
  const clipList = useSelector((state) => state.user.clipList);
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchClipList());
  }, [dispatch]);

  console.log(clipList);

  return (
    <section>
      <HeaderBox>
        <Icon></Icon>
        <ToggleBox>
          <ToggleText>모집 중</ToggleText>
          <ToggleBtn role="switch" type="checkbox" />
        </ToggleBox>
      </HeaderBox>
      <ProjectGrid>
        {clipList &&
          clipList.map((clip) => (
            <Post
              key={clip.board_id}
              title={clip.title}
              type={clip.type}
              roles={clip.roles}
              period={clip.period}
              proceed_method={clip.proceed_method}
              username={clip.username}
              tags={clip.tags}
              board_id={clip.board_id}
              createAt={clip.createAt}
              view_cnt={clip.view_cnt}
            />
          ))}
      </ProjectGrid>
    </section>
  );
}
