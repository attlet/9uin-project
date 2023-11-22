import { useEffect, useState } from 'react';

const useSSE = (url) => {
  const [data, setData] = useState(null);

  useEffect(() => {
    const eventSource = new EventSource(url);

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
  }, [url]);

  return data;
};

export default useSSE;
