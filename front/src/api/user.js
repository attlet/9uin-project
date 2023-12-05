import axios from 'axios';
import { createAxiosInstance } from './instance';
import { updateTokens } from '../modules/auth';
import config from '../common/config';

export async function refreshTokenAndRetry(
  method,
  url,
  data,
  headers,
  dispatch
) {
  const baseURL =
    process.env.REACT_APP_NODE_ENV === 'development'
      ? config.development.apiUrl
      : process.env.REACT_APP_NODE_ENV === 'local'
      ? config.local.apiUrl
      : '';

  const axiosInstance = createAxiosInstance(
    localStorage.getItem('refreshToken')
  );

  try {
    const refreshData = {
      refreshToken: localStorage.getItem('refreshToken'),
    };

    const refreshResponse = await axiosInstance.post(
      '/sign/reissue',
      refreshData
    );

    // Update tokens
    const newAccessToken = refreshResponse.data.accessToken;
    const newRefreshToken = refreshResponse.data.refreshToken;
    localStorage.setItem('token', newAccessToken);
    localStorage.setItem('refreshToken', newRefreshToken);

    dispatch(updateTokens(newAccessToken, newRefreshToken));

    const newHeaders = {
      ...headers,
      'X-AUTH-TOKEN': newAccessToken,
    };

    const retryResponse = await axios({
      method: method,
      url: baseURL + url,
      data: data,
      headers: newHeaders,
    });

    console.log('요청 성공 (재시도)');
    return retryResponse;
  } catch (refreshError) {
    console.error('새로운 액세스 토큰 얻기 실패', refreshError);
    throw refreshError;
  }
}
