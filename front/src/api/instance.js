import axios from 'axios';
import config from '../common/config';

const createAxiosInstance = (token = null, page = 0) => {
  return axios.create({
    baseURL:
      process.env.REACT_APP_NODE_ENV === 'development'
        ? config.development.apiUrl
        : process.env.REACT_APP_NODE_ENV === 'local'
        ? config.local.apiUrl
        : '',
    headers: {
      'X-AUTH-TOKEN': token,
    },
    params: {
      page,
    },
  });
};

export { createAxiosInstance };
