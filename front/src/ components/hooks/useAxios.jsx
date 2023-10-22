import { useState, useEffect } from 'react';
import axios from 'axios';
import config from '../../common/config';

axios.defaults.baseURL =
  process.env.REACT_APP_NODE_ENV === 'development'
    ? config.development.apiUrl
    : process.env.REACT_APP_NODE_ENV === 'local'
    ? config.local.apiUrl
    : '';

function useAxios(params) {
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  const fetchData = async (params) => {
    try {
      const res = await axios({
        ...params,
      });
      setResponse(res.data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData(params);
  }, []);

  return { response, error, loading };
}

export default useAxios;
