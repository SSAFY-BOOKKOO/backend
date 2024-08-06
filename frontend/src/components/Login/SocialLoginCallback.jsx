import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import Spinner from '../@common/Spinner';
import { useAtom } from 'jotai';
import { isAuthenticatedAtom } from '@atoms/authAtom';

const SocialLoginCallback = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [, setIsAuthenticated] = useAtom(isAuthenticatedAtom);

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get('token');

    if (token) {
      localStorage.setItem('ACCESS_TOKEN', token);
      setIsAuthenticated(true);

      navigate('/library');
    } else {
      navigate('/login');
    }
  }, [location, navigate]);

  return (
    <div>
      <Spinner />
    </div>
  );
};

export default SocialLoginCallback;
