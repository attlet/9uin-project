import { useState, useEffect } from 'react';
import axios from 'axios';

function useFetchData(path, header = {}, page = 0) {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const baseURL = 'http://1.246.104.170:8080';

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(baseURL + path, {
          headers: {
            'X-AUTH-TOKEN': header,
          },
          params: {
            page,
          },
        });
        setData(response.data);
        setLoading(false);
        console.log('Data GET 성공!', response.data);
      } catch (error) {
        setError('네트워크 에러가 발생했습니다.');
      }
    };

    fetchData();
  }, [path]);

  return { data, loading, error };
}

export default useFetchData;
