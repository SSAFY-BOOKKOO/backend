import { axiosInstance } from '@services/axiosInstance';

export const checkEmailDuplicate = async email => {
  try {
    const response = await axiosInstance.get(
      '/members/register/duplicate/email',
      {
        params: { email },
      }
    );
    return response.data.duplicate; // 중복 여부를 반환
  } catch (error) {
    if (error.response && error.response.status === 403) {
      return true; // 중복된 이메일
    }
    throw new Error('이메일 중복 확인 중 오류가 발생했습니다.');
  }
};

export const checkNicknameDuplicate = async nickname => {
  try {
    const response = await axiosInstance.get(
      '/members/register/duplicate/name',
      {
        params: { name: nickname },
      }
    );
    return response.data.duplicate; // 중복 여부를 반환
  } catch (error) {
    if (error.response && error.response.status === 403) {
      return true; // 중복된 닉네임
    }
    throw new Error('닉네임 중복 확인 중 오류가 발생했습니다.');
  }
};

export const sendEmailVerification = async email => {
  try {
    const response = await axiosInstance.post(
      '/members/register/validation',
      email,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );
    return response.data;
  } catch (error) {
    throw new Error('인증번호 발송 중 오류가 발생했습니다.');
  }
};

export const verifyEmailCode = async code => {
  try {
    const response = await axiosInstance.get('/members/register/validation', {
      params: { code },
    });
    return response.data;
  } catch (error) {
    throw new Error('이메일 인증 중 오류가 발생했습니다.');
  }
};
