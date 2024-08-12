import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { isAuthenticatedAtom } from '@atoms/authAtom';

const PrivateRoute = () => {
  const [isAuthenticated] = useAtom(isAuthenticatedAtom);

  if (!isAuthenticated) {
    return <Navigate to='/intro' replace />;
  }

  return <Outlet />;
};

export default PrivateRoute;
