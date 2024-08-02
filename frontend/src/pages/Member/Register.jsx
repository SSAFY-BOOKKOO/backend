import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import RegisterStep1 from '@components/Register/RegisterStep1';
import RegisterStep2 from '@components/Register/RegisterStep2';
import RegisterSummary from '@components/Register/RegisterSummary';

const Register = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    email: '',
    nickname: '',
    password: '',
    confirmPassword: '',
    introduction: '',
    profile_img_url: '',
    age: '',
    gender: '',
    categories: [],
    is_receive_letter_email: false,
  });

  const [errors, setErrors] = useState({});
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
        profile_img_url: URL.createObjectURL(file),
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

  const handleNextStep = () => {
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
    if (formData.nickname.length > 10) {
      newErrors.nickname = '닉네임은 10자 이내로 설정해야 합니다.';
    }
    if (!formData.introduction) {
      newErrors.introduction = '소개글을 입력하세요.';
    }

    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      setStep(step + 1);
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
    if (!formData.introduction) newErrors.introduction = '소개글을 입력하세요.';
    if (formData.nickname.length > 10)
      newErrors.nickname = '닉네임은 10자 이내로 설정해야 합니다.';
    if (!formData.age) newErrors.age = '연령을 입력하세요.';
    if (!formData.gender) newErrors.gender = '성별을 선택하세요.';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = e => {
    e.preventDefault();

    if (validateForm()) {
      alert('회원가입이 완료되었습니다.');
      setStep(3);
    }
  };

  return (
    <div className='flex flex-col justify-center items-center min-h-screen px-4 w-full'>
      <div className='w-full max-w-md'>
        <h2 className='text-2xl font-bold mb-4 text-center'>회원가입</h2>
        <form onSubmit={handleSubmit}>
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
          {step === 3 && (
            <RegisterSummary formData={formData} navigate={navigate} />
          )}
        </form>
      </div>
    </div>
  );
};

export default Register;
