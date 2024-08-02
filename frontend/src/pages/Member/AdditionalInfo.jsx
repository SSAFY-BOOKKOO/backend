import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import RegisterInput from '@components/Register/RegisterInput';
import Button from '@components/@common/Button';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import { axiosInstance } from '@services/axiosInstance';
import { validateForm } from '@utils/ValidateForm';

const AdditionalInfo = ({ userInfo, onSave }) => {
  const [formData, setFormData] = useState({
    age: '',
    gender: '',
    categories: [],
    introduction: '',
    ...userInfo,
  });
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();
  const [, setAlert] = useAtom(alertAtom);

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

  const handleSubmit = async e => {
    e.preventDefault();

    const validationConfig = {
      year: true,
      gender: true,
      categories: true,
      introduction: true,
    };

    const newErrors = validateForm(formData, validationConfig);
    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      try {
        const requestData = {
          memberId: formData.memberId,
          nickName: formData.nickname,
          year: parseInt(formData.year),
          gender: formData.gender.toUpperCase(),
          categories: formData.categories.map(category => parseInt(category)),
          introduction: formData.introduction || '',
        };

        await axiosInstance.post('/members/register/info', requestData);

        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '회원가입이 완료되었습니다.',
          onConfirm: () => navigate('/library'),
        });
      } catch (error) {
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '회원정보 저장 중 오류가 발생했습니다.',
        });
      }
    }
  };

  return (
    <div className='flex flex-col justify-center items-center min-h-screen px-4 w-full'>
      <div className='w-full max-w-md'>
        <h2 className='text-2xl font-bold mb-4 text-center'>추가 정보 입력</h2>
        <form onSubmit={handleSubmit}>
          <RegisterInput
            labelText='연령'
            type='number'
            id='age'
            name='age'
            value={formData.age}
            onChange={handleChange}
            required
            error={errors.age}
          />
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            성별
            <div className='flex space-x-4 mt-2 justify-center'>
              <label className='flex items-center'>
                <input
                  type='radio'
                  id='gender-male'
                  name='gender'
                  value='male'
                  checked={formData.gender === 'male'}
                  onChange={handleChange}
                />
                <span className='ml-2'>남성</span>
              </label>
              <label className='flex items-center'>
                <input
                  type='radio'
                  id='gender-female'
                  name='gender'
                  value='female'
                  checked={formData.gender === 'female'}
                  onChange={handleChange}
                />
                <span className='ml-2'>여성</span>
              </label>
              <label className='flex items-center'>
                <input
                  type='radio'
                  id='gender-none'
                  name='gender'
                  value='none'
                  checked={formData.gender === 'none'}
                  onChange={handleChange}
                />
                <span className='ml-2'>선택 안함</span>
              </label>
            </div>
            {errors.gender && (
              <p className='text-red-500 text-sm'>{errors.gender}</p>
            )}
          </label>
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            선호 카테고리
            <div className='flex flex-wrap mt-2 justify-center'>
              {[
                '추리/스릴러',
                '로맨스',
                '인문학',
                '철학',
                '경제/경영',
                '역사',
                '시',
                '에세이',
                '소설',
                '과학',
                '사회과학',
                '자기계발',
                '기타',
              ].map(category => (
                <div key={category} className='mr-2 mb-2'>
                  <label
                    className={`flex items-center justify-center border px-2 py-1 rounded-lg cursor-pointer ${
                      formData.categories.includes(category)
                        ? 'bg-pink-100'
                        : ''
                    }`}
                    htmlFor={`category-${category}`}
                  >
                    <input
                      type='checkbox'
                      id={`category-${category}`}
                      name='categories'
                      value={category}
                      checked={formData.categories.includes(category)}
                      onChange={() => handleCategoryChange(category)}
                      className='hidden'
                    />
                    <span className='text-center'>{category}</span>
                  </label>
                </div>
              ))}
            </div>
            {errors.categories && (
              <p className='text-red-500 text-sm'>{errors.categories}</p>
            )}
          </label>
          <RegisterInput
            labelText='소개글'
            type='text'
            id='introduction'
            name='introduction'
            value={formData.introduction}
            onChange={handleChange}
            error={errors.introduction}
          />
          <div className='flex justify-center mt-6'>
            <Button
              text='가입 완료'
              type='submit'
              color='text-white bg-green-400 active:bg-green-600'
              size='large'
              full
            />
          </div>
        </form>
      </div>
      <Alert />
    </div>
  );
};

export default AdditionalInfo;
