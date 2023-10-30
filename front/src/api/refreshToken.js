import axios from "axios";
import config from '../common/config';

const getNewTokens = async () => {
  let apiUrl = '';

  apiUrl =
    process.env.REACT_APP_NODE_ENV === 'development'
      ? config.development.apiUrl
      : process.env.REACT_APP_NODE_ENV === 'local'
      ? config.local.apiUrl
      : '';

  const refreshData = {
    refreshToken: localStorage.getItem('refreshToken'),
  };

  const refreshResponse = await axios.post(apiUrl + '/sign/reissue',
    refreshData
  );

  const newAccessToken = refreshResponse.data.accessToken;
  const newRefreshToken = refreshResponse.data.refreshToken;
  localStorage.setItem('token', newAccessToken);
  localStorage.setItem('refreshToken', newRefreshToken);

  return { accessToken: newAccessToken, refreshToken: newRefreshToken };
};

export { getNewTokens };
