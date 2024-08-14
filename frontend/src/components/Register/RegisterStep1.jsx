import React, { useState, useEffect } from 'react';
import { useSetAtom } from 'jotai';
import RegisterInput from './RegisterInput';
import Button from '@components/@common/Button';
import {
  checkEmailDuplicate,
  checkNicknameDuplicate,
} from '@utils/RegisterCheck';
import {
  emailDuplicateAtom,
  nicknameDuplicateAtom,
  errorAtom,
} from '@atoms/RegisterAtom';
import { alertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';

const RegisterStep1 = ({
  formData,
  errors,
  handleChange,
  handleFileChange,
  handleNextStep,
  handleSendVerificationCode,
  handleVerifyCode,
  isSocialLogin,
  isEmailVerified,
  setFormData,
  setIsEmailVerified,
}) => {
  const setEmailDuplicate = useSetAtom(emailDuplicateAtom);
  const setNicknameDuplicate = useSetAtom(nicknameDuplicateAtom);
  const setError = useSetAtom(errorAtom);
  const setAlert = useSetAtom(alertAtom);

  const [emailError, setEmailError] = useState('');
  const [nicknameError, setNicknameError] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  const [isSendingCode, setIsSendingCode] = useState(false);

  useEffect(() => {
    if (isSocialLogin) {
      setFormData(prevFormData => ({
        ...prevFormData,
        isEmailChecked: true,
      }));
      setIsEmailVerified(true);
    }
  }, [isSocialLogin, setFormData, setIsEmailVerified]);

  const handleEmailCheck = async () => {
    if (!formData.email) {
      setEmailError('이메일을 입력하세요.');
      return;
    }
    try {
      const isDuplicate = await checkEmailDuplicate(formData.email);
      setEmailDuplicate(isDuplicate);
      setFormData(prevFormData => ({
        ...prevFormData,
        isEmailChecked: !isDuplicate,
      }));
      if (isDuplicate) {
        setEmailError('이미 사용 중인 이메일입니다.');
      } else {
        setEmailError('');
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '사용 가능한 이메일입니다.',
        });
      }
    } catch (error) {
      setEmailError(error.message);
    }
  };

  const handleNicknameCheck = async () => {
    if (!formData.nickname) {
      setNicknameError('닉네임을 입력하세요.');
      return;
    }
    try {
      const isDuplicate = await checkNicknameDuplicate(formData.nickname);
      setNicknameDuplicate(isDuplicate);
      setFormData(prevFormData => ({
        ...prevFormData,
        isNicknameChecked: !isDuplicate,
      }));
      if (isDuplicate) {
        setNicknameError('이미 사용 중인 닉네임입니다.');
      } else {
        setNicknameError('');
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '사용 가능한 닉네임입니다.',
        });
      }
    } catch (error) {
      setNicknameError(error.message);
    }
  };

  const handleSendVerificationCodeClick = async () => {
    setIsSendingCode(true);
    await handleSendVerificationCode();
    setIsSendingCode(false);
  };

  const validateAndProceed = () => {
    if (!isSocialLogin && !formData.isEmailChecked) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '이메일 중복확인을 해주세요.',
      });
      return;
    }
    if (!formData.isNicknameChecked) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '닉네임 중복확인을 해주세요.',
      });
      return;
    }
    if (emailError || nicknameError) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '중복 확인을 다시 해주세요.',
      });
      return;
    }
    if (!isEmailVerified && !isSocialLogin) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '이메일 인증을 완료해주세요.',
      });
      return;
    }
    handleNextStep();
  };

  return (
    <>
      <h2 className='text-2xl font-bold mb-4 text-center'>회원가입</h2>
      <div className='mb-4 relative'>
        <label className='block text-gray-700 font-medium'>이메일</label>
        <div className='relative'>
          <input
            type='email'
            id='email'
            name='email'
            value={formData.email}
            onChange={e => {
              handleChange(e);
              if (!isSocialLogin) {
                setFormData(prevFormData => ({
                  ...prevFormData,
                  isEmailChecked: false,
                }));
              }
            }}
            required
            disabled={isSocialLogin}
            className={`mt-1 p-2 block w-full border rounded-md pr-32 ${
              errors.email || emailError ? 'border-red-500' : ''
            }`}
          />
          {!isSocialLogin && (
            <Button
              text='중복확인'
              type='button'
              color='text-white bg-pink-500 active:bg-pink-600'
              size='small'
              full={false}
              onClick={handleEmailCheck}
              className='absolute right-0 top-0 mt-2 mr-2'
            />
          )}
        </div>
        {(errors.email || emailError) && (
          <p className='text-red-500 text-xs italic'>
            {errors.email || emailError}
          </p>
        )}
      </div>
      {!isSocialLogin && formData.isEmailChecked && (
        <>
          <div className='mb-4 relative'>
            <label className='block mb-2 text-sm font-medium text-gray-700'>
              인증 코드
            </label>
            <div className='relative'>
              <input
                type='text'
                id='verificationCode'
                name='verificationCode'
                value={verificationCode}
                onChange={e => setVerificationCode(e.target.value)}
                required
                className={`mt-1 p-2 block w-full border rounded-md pr-32 ${
                  errors.verificationCode ? 'border-red-500' : ''
                }`}
              />
              <Button
                text='인증 코드 확인'
                type='button'
                color='text-white bg-pink-500 active:bg-pink-600'
                size='small'
                full={false}
                onClick={async () => {
                  await handleVerifyCode(verificationCode);
                  setIsEmailVerified(true);
                }}
                className='absolute right-0 top-0 mt-2 mr-2'
              />
            </div>
            {errors.verificationCode && (
              <p className='text-red-500 text-xs italic'>
                {errors.verificationCode}
              </p>
            )}
          </div>
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            <div className='flex items-center mt-2'>
              <Button
                text='인증 코드 전송'
                type='button'
                color='text-white bg-pink-500 active:bg-pink-600'
                size='small'
                full={false}
                onClick={handleSendVerificationCodeClick}
                disabled={isSendingCode}
              />
            </div>
          </label>
        </>
      )}
      <div className='mb-4 relative'>
        <label className='block text-gray-700 font-medium'>닉네임</label>
        <div className='relative'>
          <input
            type='text'
            id='nickname'
            name='nickname'
            value={formData.nickname}
            onChange={e => {
              handleChange(e);
              setFormData(prevFormData => ({
                ...prevFormData,
                isNicknameChecked: false,
              }));
            }}
            required
            className={`mt-1 p-2 block w-full border rounded-md pr-32 ${
              errors.nickname || nicknameError ? 'border-red-500' : ''
            }`}
          />
          <Button
            text='중복확인'
            type='button'
            color='text-white bg-pink-500 active:bg-pink-600'
            size='small'
            full={false}
            onClick={handleNicknameCheck}
            className='absolute right-0 top-0 mt-2 mr-2'
          />
        </div>
        {(errors.nickname || nicknameError) && (
          <p className='text-red-500 text-xs italic'>
            {errors.nickname || nicknameError}
          </p>
        )}
      </div>
      {!isSocialLogin && (
        <>
          <RegisterInput
            labelText='비밀번호'
            type='password'
            id='password'
            name='password'
            value={formData.password}
            onChange={handleChange}
            required
            error={errors.password}
          />
          <RegisterInput
            labelText='비밀번호 확인'
            type='password'
            id='confirmPassword'
            name='confirmPassword'
            value={formData.confirmPassword}
            onChange={handleChange}
            required
            error={errors.confirmPassword}
          />
        </>
      )}
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        프로필 이미지
        <div className='flex items-center mt-2'>
          <input
            type='file'
            accept='image/*'
            onChange={handleFileChange}
            className='hidden'
            id='profile_img_input'
          />
          <Button
            text='찾기'
            type='button'
            color='text-white bg-green-400 active:bg-green-600'
            size='small'
            full={false}
            onClick={() => document.getElementById('profile_img_input').click()}
          />
        </div>
        {formData.profileImgUrl && (
          <img
            src={URL.createObjectURL(formData.profileImgUrl)}
            alt='Profile Preview'
            className='mt-2 w-32 h-32 object-cover rounded-full'
          />
        )}
      </label>
      <div className='flex justify-center mt-6'>
        <Button
          text='다음'
          type='button'
          color='text-white bg-green-400 active:bg-green-600'
          size='medium'
          full
          onClick={validateAndProceed}
        />
      </div>
      <Alert />
    </>
  );
};

export default RegisterStep1;
