import { useState, useEffect } from 'react';
import Input from '../components/@common/Input';
import WrapContainer from '../components/Layout/WrapContainer';
import Button from '../components/@common/Button';
import useInput from '../hooks/useInput';
import { useNavigate } from 'react-router-dom';
import SocialLoginButton from '@components/Login/SocialLoginButton';

const Login = () => {
  const {
    input: loginInfo,
    onChange: handleChange,
    reset,
  } = useInput({
    email: '',
    password: '',
  });

  const [buttonDisabled, setButtonDisabled] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();

  // 회원가입 페이지로 이동
  const handleRegisterMove = () => {
    navigate('/register');
  };

  // 비밀번호 찾기 페이지로 이동
  const handleFindPasswordMove = () => {
    navigate('/find-password');
  };

  useEffect(() => {
    if (loginInfo.email && loginInfo.password) {
      setButtonDisabled(false);
    } else {
      setButtonDisabled(true);
    }
  }, [loginInfo]);

  // 로그인 API
  const handleLogin = e => {
    e.preventDefault();

    // 로그인 연동 작성
    if (errorMessage === '' && !buttonDisabled) {
      // 로그인
    }
  };

  return (
    <div className='h-screen flex items-center justify-center'>
      <WrapContainer className='flex justify-center items-center'>
        <div className='flex justify-center  items-center '>
          <div className='w-11/12'>
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
