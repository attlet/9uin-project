import axios from "axios";

const getNewTokens = async () => {
  const refreshData = {
    refreshToken: localStorage.getItem('refreshToken'),
  };

  const refreshResponse = await axios.post(
    'http://1.246.104.170:8080/sign/reissue',
    refreshData
  );

  const newAccessToken = refreshResponse.data.accessToken;
  const newRefreshToken = refreshResponse.data.refreshToken;
  localStorage.setItem('token', newAccessToken);
  localStorage.setItem('refreshToken', newRefreshToken);

  return { accessToken: newAccessToken, refreshToken: newRefreshToken };
};

export { getNewTokens };
