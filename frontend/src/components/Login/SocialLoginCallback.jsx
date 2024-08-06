import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import Spinner from '../@common/Spinner';

const SocialLoginCallback = () => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get('token');

    if (token) {
      localStorage.setItem('ACCESS_TOKEN', token);

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
