import NaverIconImg from '@assets/icons/naver_login_icon.png';
import KakaoIconImg from '@assets/icons/kakao_login_icon.png';
import GoogleIconImg from '@assets/icons/google_login_icon.png';

const SocialLoginButton = () => {
  const handleNaverLogin = () => {};

  const handleKakaoLogin = () => {};

  const handleGoogleLogin = () => {};

  return (
    <div className='flex flex-row justify-center h-20 py-3'>
      <button className='p-2 w-20 h-20' onClick={handleNaverLogin}>
        <img src={NaverIconImg}></img>
      </button>
      <button className='p-2 w-20 h-20' onClick={handleKakaoLogin}>
        <img src={KakaoIconImg}></img>
      </button>
      <button className='p-2 w-20 h-20' onClick={handleGoogleLogin}>
        <img src={GoogleIconImg}></img>
      </button>
    </div>
  );
};

export default SocialLoginButton;
