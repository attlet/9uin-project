import axios from 'axios';

const createAxiosInstance = (token, page = 0) => {
  return axios.create({
    baseURL: 'http://1.246.104.170:8080',
    headers: {
      'X-AUTH-TOKEN': token,
    },
    params: {
      page,
    },
  });
};

export { createAxiosInstance };
