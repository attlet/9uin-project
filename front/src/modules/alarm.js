// Action Types
const GET_ALARMLIST = 'alarm/GET_ALARMLIST';

// Action Creators
export const getAlarmList = (alarmList) => ({
  type: GET_ALARMLIST,
  payload: { alarmList },
});

// Initial State
const initialState = {
  alarmList: [],
};

// Reducer
export default function alarmReducer(state = initialState, action) {
  switch (action.type) {
    case GET_ALARMLIST:
      return {
        ...state,
        alarmList: action.payload.alarmList,
      };
    default:
      return state;
  }
}
