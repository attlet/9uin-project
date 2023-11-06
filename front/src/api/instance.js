import axios from 'axios';
import config from '../common/config';

const createAxiosInstance = (token, page) => {
  const headers = token ? { 'X-AUTH-TOKEN': token } : {};
  const params = page !== undefined ? { page } : {};

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
