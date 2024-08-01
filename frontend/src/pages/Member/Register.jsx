import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import RegisterStep1 from '@components/Register/RegisterStep1';
import RegisterStep2 from '@components/Register/RegisterStep2';
import WrapContainer from '@components/Layout/WrapContainer';
import {
  emailDuplicateAtom,
  nicknameDuplicateAtom,
  errorAtom,
} from '@atoms/RegisterAtom';
import {
  checkEmailDuplicate,
  checkNicknameDuplicate,
} from '@utils/RegisterCheck';
import { axiosInstance } from '@services/axiosInstance';

const RegisterPage = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    email: '',
    nickname: '',
    password: '',
    confirmPassword: '',
    introduction: '',
    profile_img_url: null,
    year: '',
    gender: '',
    categories: [],
    socialType: 'bookkoo', // socialType 설정
  });

  const [errors, setErrors] = useState({});
  const [emailDuplicate, setEmailDuplicate] = useAtom(emailDuplicateAtom);
  const [nicknameDuplicate, setNicknameDuplicate] = useAtom(
    nicknameDuplicateAtom
  );
  const [error, setError] = useAtom(errorAtom);
  const navigate = useNavigate();

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setFormData(prevFormData => ({
      ...prevFormData,
      [name]: type === 'checkbox' ? checked : value,
    }));
    setErrors(prevErrors => ({
      ...prevErrors,
      [name]: '',
    }));
  };

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      setFormData(prevFormData => ({
        ...prevFormData,
        profile_img_url: file, // 파일 객체로 저장
      }));
      setErrors(prevErrors => ({
        ...prevErrors,
        profile_img_url: '',
      }));
    }
  };

  const handleCategoryChange = category => {
    setFormData(prevFormData => {
      const categories = [...prevFormData.categories];
      if (categories.includes(category)) {
        return {
          ...prevFormData,
          categories: categories.filter(cat => cat !== category),
        };
      } else {
        return { ...prevFormData, categories: [...categories, category] };
      }
    });
  };

  const validateEmail = email => {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(email);
  };

  const validatePassword = password => {
    const re = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,16}$/;
    return re.test(password);
  };

  const handleNextStep = async () => {
    const newErrors = {};
    if (!validateEmail(formData.email)) {
      newErrors.email = '올바른 이메일 형식을 입력하세요.';
    }
    if (!validatePassword(formData.password)) {
      newErrors.password =
        '비밀번호는 영문, 숫자, 특수문자 조합으로 이루어진 8~16자여야 합니다.';
    }
    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    }
    if (!formData.nickname) {
      newErrors.nickname = '닉네임을 입력하세요.';
    } else if (formData.nickname.length > 10) {
      newErrors.nickname = '닉네임은 10자 이내로 설정해야 합니다.';
    }

    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      try {
        const isEmailDuplicate = await checkEmailDuplicate(formData.email);
        const isNicknameDuplicate = await checkNicknameDuplicate(
          formData.nickname
        );

        setEmailDuplicate(isEmailDuplicate);
        setNicknameDuplicate(isNicknameDuplicate);

        if (isEmailDuplicate) {
          setError('이미 사용 중인 이메일입니다.');
        } else if (isNicknameDuplicate) {
          setError('이미 사용 중인 닉네임입니다.');
        } else {
          setError('');
          setStep(step + 1);
        }
      } catch (error) {
        setError(error.message);
      }
    }
  };

  const handlePrevStep = () => {
    setStep(step - 1);
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.email) newErrors.email = '이메일을 입력하세요.';
    if (!validateEmail(formData.email))
      newErrors.email = '올바른 이메일 형식을 입력하세요.';
    if (!formData.nickname) newErrors.nickname = '닉네임을 입력하세요.';
    if (!formData.password) newErrors.password = '비밀번호를 입력하세요.';
    if (!validatePassword(formData.password))
      newErrors.password =
        '비밀번호는 영문, 숫자, 특수문자 조합으로 이루어진 8~16자여야 합니다.';
    if (formData.password !== formData.confirmPassword)
      newErrors.confirmPassword = '비밀번호가 일치하지 않습니다.';
    if (formData.nickname.length > 10)
      newErrors.nickname = '닉네임은 10자 이내로 설정해야 합니다.';
    if (!formData.year) newErrors.year = '연령을 입력하세요.';
    if (!formData.gender) newErrors.gender = '성별을 선택하세요.';
    if (formData.categories.length === 0)
      newErrors.categories = '선호 카테고리를 선택하세요.';
    if (!formData.introduction) newErrors.introduction = '소개글을 입력하세요.';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async () => {
    if (validateForm()) {
      try {
        console.log('회원가입 정보:', formData);

        const formDataToSend = new FormData();

        const blob = new Blob(
          [
            JSON.stringify({
              email: formData.email,
              password: formData.password,
              nickName: formData.nickname,
              year: formData.year,
              gender: formData.gender,
              categories: formData.categories,
              introduction: formData.introduction,
              socialType: formData.socialType,
            }),
          ],
          {
            type: 'application/json',
          }
        );

        formDataToSend.append('requestRegisterMemberDto', blob);

        if (formData.profile_img_url) {
          formDataToSend.append('profileImg', formData.profile_img_url);
        } else {
          formDataToSend.append('profileImg', '');
        }

        const response = await axiosInstance.post(
          '/members/register',
          formDataToSend,
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          }
        );

        if (response.status === 200) {
          alert('회원가입이 완료되었습니다.');
          setStep(3);
        } else {
          console.error('Unexpected response status:', response.status);
          setError('회원가입에 실패했습니다.');
        }
      } catch (error) {
        console.error('Error submitting form:', error);
        setError('회원가입에 실패했습니다.');
      }
    }
  };

  return (
    <WrapContainer>
      <div className='flex flex-col justify-center items-center min-h-screen px-4 w-full'>
        <div className='w-full max-w-md'>
          <h2 className='text-2xl font-bold mb-4 text-center'>회원가입</h2>
          {step === 1 && (
            <RegisterStep1
              formData={formData}
              errors={errors}
              handleChange={handleChange}
              handleFileChange={handleFileChange}
              handleNextStep={handleNextStep}
            />
          )}
          {step === 2 && (
            <RegisterStep2
              formData={formData}
              errors={errors}
              handleChange={handleChange}
              handleCategoryChange={handleCategoryChange}
              handlePrevStep={handlePrevStep}
              handleSubmit={handleSubmit}
            />
          )}
        </div>
      </div>
    </WrapContainer>
  );
};

export default RegisterPage;
