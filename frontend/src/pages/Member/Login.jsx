import { useState, useMemo } from 'react';
import Input from '@components/@common/Input';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import useInput from '@hooks/useInput';
import { useNavigate } from 'react-router-dom';
import SocialLoginButton from '@components/Login/SocialLoginButton';
import { postLogin, getMemberInfo } from '@services/Member';
import { useAtom } from 'jotai';
import { isAuthenticatedAtom } from '@atoms/authAtom';

const Login = () => {
  const {
    input: loginInfo,
    onChange: handleChange,
    reset,
  } = useInput({
    email: '',
    password: '',
  });

  const [errorMessage, setErrorMessage] = useState('');
  const [, setIsAuthenticated] = useAtom(isAuthenticatedAtom); // 로그인 여부

  const navigate = useNavigate();

  // 회원가입 페이지로 이동
  const handleRegisterMove = () => {
    navigate('/register');
  };

  // 비밀번호 찾기 페이지로 이동
  const handleFindPasswordMove = () => {
    navigate('/find-password');
  };

  const buttonDisabled = useMemo(() => {
    return !(loginInfo.email && loginInfo.password);
  }, [loginInfo]);

  const getNickname = async () => {};

  const handleLogin = async e => {
    e.preventDefault();

    if (buttonDisabled) return;

    try {
      const data = await postLogin(loginInfo);

      // 로그인 성공
      localStorage.setItem('ACCESS_TOKEN', data.accessToken);

      const userData = await getMemberInfo();
      localStorage.setItem('MEMBER_ID', userData.memberId);

      setIsAuthenticated(true);
      navigate('/');

      // 페이지 이동 하기
    } catch (error) {
      // 에러 처리
      setErrorMessage('아이디 혹은 비밀번호가 일치하지 않습니다.');
    }
  };

  return (
    <div className='h-screen flex items-center justify-center'>
      <WrapContainer className='flex justify-center items-center'>
        <div className='flex justify-center items-center w-11/12 max-w-md'>
          <div className='w-full px-6'>
            <h2 className='text-center text-3xl font-bold mb-8'>북꾸북꾸</h2>
            <form>
              <Input
                labelText='이메일'
                id='email'
                value={loginInfo.email}
                onChange={handleChange}
                inputClassName='mb-3'
              />
              <Input
                labelText='비밀번호'
                id='password'
                type='password'
                value={loginInfo.password}
                onChange={handleChange}
              />
              <div className='h-6 mt-2'>
                {errorMessage && (
                  <p className='text-red-500 text-sm'>{errorMessage}</p>
                )}
              </div>
              <Button
                full
                size='large'
                className='mt-3'
                type='submit'
                onClick={handleLogin}
                disabled={buttonDisabled}
              >
                로그인
              </Button>
            </form>
            <div className='flex flex-row justify-center mt-4'>
              <p
                className='w-fit p-2 cursor-pointer'
                onClick={handleRegisterMove}
              >
                회원가입
              </p>
              <p
                className='w-fit p-2 cursor-pointer'
                onClick={handleFindPasswordMove}
              >
                비밀번호 찾기
              </p>
            </div>
            {/* 소셜 로그인 */}
            <SocialLoginButton />
          </div>
        </div>
      </WrapContainer>
    </div>
  );
};

export default Login;
