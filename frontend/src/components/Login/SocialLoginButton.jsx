import React, { useState } from 'react';
import NaverIconImg from '@assets/icons/naver_login_icon.png';
import KakaoIconImg from '@assets/icons/kakao_login_icon.png';
import GoogleIconImg from '@assets/icons/google_login_icon.png';

const SocialLoginButton = () => {
  const [error, setError] = useState(null);

  const handleSocialLogin = async provider => {
    setError(null);
    window.location.href = `https://api.i11a506.ssafy.io/auth/login/oauth2/authorization/${provider}`;
  };

  return (
    <div className='flex flex-row justify-center h-20 py-3'>
      <button
        className='p-2 w-20 h-20'
        onClick={() => handleSocialLogin('naver')}
        aria-label='네이버로 로그인'
      >
        <img src={NaverIconImg} alt='Naver Login' />
      </button>
      <button
        className='p-2 w-20 h-20'
        onClick={() => handleSocialLogin('kakao')}
        aria-label='카카오로 로그인'
      >
        <img src={KakaoIconImg} alt='Kakao Login' />
      </button>
      <button
        className='p-2 w-20 h-20'
        onClick={() => handleSocialLogin('google')}
        aria-label='구글로 로그인'
      >
        <img src={GoogleIconImg} alt='Google Login' />
      </button>
      {error && <p className='text-red-500'>{error}</p>}
    </div>
  );
};

export default SocialLoginButton;
