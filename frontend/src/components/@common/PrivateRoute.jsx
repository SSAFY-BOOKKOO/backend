import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';

const PrivateRoute = ({ userAuthentication, isAuthenticated }) => {
  if (userAuthentication && !isAuthenticated) {
    return <Navigate to='/intro' />;
  } else if (!userAuthentication && isAuthenticated) {
    return <Navigate to='/' />;
  }
  return <Outlet />;
};

export default PrivateRoute;
