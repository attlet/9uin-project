import { combineReducers } from 'redux';
import authReducer from './auth';
import userReducer from './user';
import alarmReducer from './alarm';

const rootReducer = combineReducers({
  auth: authReducer,
  user: userReducer,
  alarm: alarmReducer,
  // 다른 리듀서 추가
});

export default rootReducer;
