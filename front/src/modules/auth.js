// Action Types
const LOGIN = 'auth/LOGIN';
const LOGOUT = 'auth/LOGOUT';
const UPDATE_TOKENS = 'auth/UPDATE_TOKENS';

// Action Creators
export const login = (token, refreshToken, userId, username) => ({
  type: LOGIN,
  payload: { token, refreshToken, userId, username },
});

export const logout = () => ({
  type: LOGOUT,
});

export const updateTokens = (token, refreshToken) => ({
  type: updateTokens,
  payload: { token, refreshToken },
});

// Initial State
const initialState = {
  token: localStorage.getItem('token') || null,
  refreshToken: localStorage.getItem('refreshToken') || null,
  userId: 0,
};

// Reducer
export default function authReducer(state = initialState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        token: action.payload.token,
        refreshToken: action.payload.refreshToken,
        userId: action.payload.userId,
        username: action.payload.username,
      };
    case LOGOUT:
      return {
        ...state,
        token: null,
        refreshToken: null,
        userId: 0,
        username: '',
      };
    case UPDATE_TOKENS:
      return {
        ...state,
        token: action.payload.token,
        refreshToken: action.payload.refreshToken,
      };
    default:
      return state;
  }
}
