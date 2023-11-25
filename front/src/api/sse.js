import { useEffect, useState } from 'react';
import config from '../common/config';

const useSSE = (endPoint) => {
  const [data, setData] = useState(null);
  const baseURL =
    process.env.REACT_APP_NODE_ENV === 'development'
      ? config.development.apiUrl
      : process.env.REACT_APP_NODE_ENV === 'local'
      ? config.local.apiUrl
      : '';

  useEffect(() => {
    const sseUrl = `${baseURL}${endPoint}`;
    const eventSource = new EventSource(sseUrl);

    eventSource.onmessage = (event) => {
      console.log(event);
      const eventData = JSON.parse(event.data);
      setData(eventData);
    };

    eventSource.onerror = (error) => {
      console.error('Error with SSE:', error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, [endPoint]);

  return data;
};

export default useSSE;
