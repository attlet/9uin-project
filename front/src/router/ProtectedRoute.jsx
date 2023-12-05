import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
  const isToken = useSelector((state) => state.auth.token);

  if (!isToken) {
    return <Navigate to="/user/login" replace={true} />;
  }

  return children;
};

export default ProtectedRoute;
