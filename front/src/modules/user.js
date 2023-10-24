import { createAxiosInstance } from '../api/instance';
import { getNewTokens } from '../api/refreshToken';
import { updateTokens } from './auth';

// Action Types
const GET_CLIPLIST = 'user/GET_CLIPLIST';

// Action Creators
export const getClipList = (clipList) => ({
  type: GET_CLIPLIST,
  payload: { clipList },
});

// 비동기 액션 생성자 (Thunk)
export const fetchClipList = () => async (dispatch, getState) => {
  try {
    const token = getState().auth.token;
    const axiosInstance = createAxiosInstance(token);
    const response = await axiosInstance.get('cliped');

    dispatch(getClipList(response.data));
  } catch (error) {
    console.error(error);

    if (error.response && error.response.status === 401) {
      try {
        const { newAccessToken, newRefreshToken } = await getNewTokens();

        dispatch(updateTokens(newAccessToken, newRefreshToken));

        const axiosInstance = createAxiosInstance(newRefreshToken);
        const response = await axiosInstance.get('cliped');

        dispatch(getClipList(response.data));
      } catch (error) {
        console.error('토큰 갱신 또는 클립 목록 가져오기 실패', error);
      }
    }
  }
};

// Initial State
const initialState = {
  clipList: [],
};

// Reducer
export default function userReducer(state = initialState, action) {
  switch (action.type) {
    case GET_CLIPLIST:
      return {
        ...state,
        clipList: action.payload.clipList,
      };
    default:
      return state;
  }
}
