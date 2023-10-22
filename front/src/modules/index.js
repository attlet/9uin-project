import { combineReducers } from 'redux';
import authReducer from './auth';
import userReducer from './user';

const rootReducer = combineReducers({
  auth: authReducer,
  user: userReducer,
  // 다른 리듀서 추가
});

export default rootReducer;
