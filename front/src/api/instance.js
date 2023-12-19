import axios from 'axios';
import config from '../common/config';

const createAxiosInstance = (token, params) => {
  const headers = token ? { 'X-AUTH-TOKEN': token } : {};

  return axios.create({
    baseURL:
      process.env.REACT_APP_NODE_ENV === 'development'
        ? config.development.apiUrl
        : process.env.REACT_APP_NODE_ENV === 'local'
        ? config.local.apiUrl
        : '',
    headers,
    params,
  });
};

export { createAxiosInstance };
