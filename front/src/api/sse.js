import { useEffect, useState } from 'react';
import config from '../common/config';
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';
import { useSelector } from 'react-redux';

const useSSE = (endPoint) => {
  const [data, setData] = useState(null);
  const token = useSelector((state) => state.auth.token);
  const baseURL =
    process.env.REACT_APP_NODE_ENV === 'development'
      ? config.development.apiUrl
      : process.env.REACT_APP_NODE_ENV === 'local'
      ? config.local.apiUrl
      : '';

  useEffect(() => {
    const sseUrl = `${baseURL}${endPoint}`;
    const EventSource = EventSourcePolyfill || NativeEventSource;
    const eventSource = new EventSource(sseUrl, {
      headers: {
        'X-AUTH-TOKEN': token,
        'Content-Type': 'text/event-stream',
      },
    });

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
