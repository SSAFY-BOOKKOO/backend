import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import Spinner from '@components/@common/Spinner';
import { useAtom } from 'jotai';
import { isAuthenticatedAtom } from '@atoms/authAtom';
import { getMemberInfo } from '@services/Member';

const SocialLoginCallback = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [, setIsAuthenticated] = useAtom(isAuthenticatedAtom);

  const getNickname = async () => {
    const data = await getMemberInfo();
    localStorage.setItem('MEMBER_ID', data.memberId);
  };

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const token = searchParams.get('token');

    if (token) {
      localStorage.setItem('ACCESS_TOKEN', token);
      getNickname(); // 닉네임 저장
      setIsAuthenticated(true);

      navigate('/');
    } else {
      navigate('/login');
    }
  }, [location, navigate, setIsAuthenticated]);

  return (
    <div>
      <Spinner />
    </div>
  );
};

export default SocialLoginCallback;
