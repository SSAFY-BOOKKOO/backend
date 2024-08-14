import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import RegisterStep1 from '@components/Register/RegisterStep1';
import RegisterStep2 from '@components/Register/RegisterStep2';
import RegisterStep3 from '@components/Register/RegisterStep3';
import WrapContainer from '@components/Layout/WrapContainer';
import Alert from '@components/@common/Alert';
import {
  emailDuplicateAtom,
  nicknameDuplicateAtom,
  errorAtom,
} from '@atoms/RegisterAtom';
import { alertAtom } from '@atoms/alertAtom';
import {
  checkEmailDuplicate,
  checkNicknameDuplicate,
} from '@utils/RegisterCheck';
import { axiosInstance } from '@services/axiosInstance';
import { validateForm } from '@utils/ValidateForm';
import { postCategories } from '@services/Book';

const RegisterPage = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    email: '',
    nickname: '',
    password: '',
    confirmPassword: '',
    introduction: '',
    profileImgUrl: '',
    year: '',
    gender: '',
    categories: [],
    socialType: 'bookkoo',
    memberSettingDto: {
      isLetterReceive: false,
      reviewVisibility: 'PUBLIC',
    },
  });
  const [isEmailVerified, setIsEmailVerified] = useState(false);
  const [errors, setErrors] = useState({});
  const [emailDuplicate, setEmailDuplicate] = useAtom(emailDuplicateAtom);
  const [nicknameDuplicate, setNicknameDuplicate] = useAtom(
    nicknameDuplicateAtom
  );
  const [, setAlert] = useAtom(alertAtom);
  const navigate = useNavigate();
  const location = useLocation();
  const [categories, setCategories] = useState([]);
  const [isSocial, setIsSocial] = useState(false);

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const email = searchParams.get('email');
    const socialType = searchParams.get('socialType');

    if (email && socialType) {
      setFormData(prevFormData => ({
        ...prevFormData,
        email,
        socialType,
      }));
      setIsEmailVerified(true);
      setIsSocial(true);
    }
  }, [location]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await postCategories();
        setCategories(response);
      } catch (error) {
        console.error('Failed to fetch categories', error);
      }
    };
    fetchCategories();
  }, []);

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    if (name in formData.memberSettingDto) {
      setFormData(prevFormData => ({
        ...prevFormData,
        memberSettingDto: {
          ...prevFormData.memberSettingDto,
          [name]: type === 'checkbox' ? checked : value,
        },
      }));
    } else {
      setFormData(prevFormData => ({
        ...prevFormData,
        [name]: type === 'checkbox' ? checked : value,
      }));
    }
    setErrors(prevErrors => ({
      ...prevErrors,
      [name]: '',
    }));
  };

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      if (file.size > 3 * 1024 * 1024) {
        // 3MB 제한
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '파일 크기는 3MB를 초과할 수 없습니다.',
        });
        return;
      }
      setFormData(prevFormData => ({
        ...prevFormData,
        profileImgUrl: file,
      }));
      setErrors(prevErrors => ({
        ...prevErrors,
        profileImgUrl: '',
      }));
    }
  };

  const handleCategoryChange = category => {
    setFormData(prevFormData => {
      const categories = [...prevFormData.categories];
      if (categories.includes(category.id)) {
        return {
          ...prevFormData,
          categories: categories.filter(cat => cat !== category.id),
        };
      } else {
        return { ...prevFormData, categories: [...categories, category.id] };
      }
    });
  };

  const handleNextStep = async () => {
    const validationConfig = {
      nickname: true,
      ...(!isSocial && {
        email: true,
        password: true,
        confirmPassword: true,
      }),
    };

    const newErrors = validateForm(formData, validationConfig);
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      try {
        const isEmailDuplicate = isSocial
          ? false
          : await checkEmailDuplicate(formData.email);
        const isNicknameDuplicate = await checkNicknameDuplicate(
          formData.nickname
        );

        setEmailDuplicate(isEmailDuplicate);
        setNicknameDuplicate(isNicknameDuplicate);

        if (isEmailDuplicate) {
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '이미 사용 중인 이메일입니다.',
          });
        } else if (isNicknameDuplicate) {
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '이미 사용 중인 닉네임입니다.',
          });
        } else {
          setStep(step + 1);
        }
      } catch (error) {
        setAlert({
          isOpen: true,
          message: error.message,
        });
      }
    }
  };

  const handlePrevStep = () => {
    setStep(step - 1);
  };

  const handleSendVerificationCode = async () => {
    try {
      await axiosInstance.post('/members/register/validation', formData.email, {
        headers: {
          'Content-Type': 'text/plain',
        },
      });
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '이메일이 오지 않는 경우 잠시 후 다시 전송바랍니다',
      });
    } catch (error) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '인증 코드 전송에 실패했습니다.',
      });
    }
  };

  const handleVerifyCode = async verificationCode => {
    try {
      await axiosInstance.get('/members/register/validation', {
        params: {
          email: formData.email,
          certNum: verificationCode,
        },
      });
      setIsEmailVerified(true);
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '인증이 성공적으로 완료되었습니다.',
      });
    } catch (error) {
      setIsEmailVerified(false);
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '인증 코드 확인에 실패했습니다.',
      });
    }
  };

  const handleSubmit = async () => {
    const validationConfig = {
      nickname: true,
      year: true,
      gender: true,
      categories: true,
      introduction: true,
      ...(!isSocial && {
        email: true,
        password: true,
        confirmPassword: true,
      }),
    };

    const newErrors = validateForm(formData, validationConfig);
    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      try {
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
              socialType: formData?.socialType,
              memberSettingDto: formData.memberSettingDto,
            }),
          ],
          {
            type: 'application/json',
          }
        );

        formDataToSend.append('requestRegisterMemberDto', blob);

        if (formData.profileImgUrl) {
          formDataToSend.append('profileImg', formData.profileImgUrl);
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
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '회원가입이 완료되었습니다.',
            onConfirm: () => navigate('/library'),
          });
        } else {
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '회원가입에 실패했습니다.',
          });
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <WrapContainer>
      <div className='flex flex-col justify-center items-center min-h-screen px-4 w-full'>
        <div className='w-full max-w-md'>
          {step === 1 && (
            <RegisterStep1
              formData={formData}
              errors={errors}
              handleChange={handleChange}
              handleFileChange={handleFileChange}
              handleNextStep={handleNextStep}
              handleSendVerificationCode={handleSendVerificationCode}
              handleVerifyCode={handleVerifyCode}
              isSocialLogin={isSocial}
              isEmailVerified={isEmailVerified}
              setFormData={setFormData}
              setIsEmailVerified={setIsEmailVerified}
            />
          )}
          {step === 2 && (
            <RegisterStep2
              formData={formData}
              errors={errors}
              handleChange={handleChange}
              handleCategoryChange={handleCategoryChange}
              categoriesList={categories}
              handlePrevStep={handlePrevStep}
              handleNextStep={() => setStep(3)}
            />
          )}
          {step === 3 && (
            <RegisterStep3
              formData={{ ...formData, ...formData.memberSettingDto }}
              errors={errors}
              handleChange={handleChange}
              handleSubmit={handleSubmit}
              handlePrevStep={handlePrevStep}
            />
          )}
        </div>
      </div>
      <Alert />
    </WrapContainer>
  );
};

export default RegisterPage;
