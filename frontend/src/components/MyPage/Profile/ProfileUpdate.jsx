import React, { useState } from 'react';
import Button from '@components/@common/Button';

const categoriesList = [
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
];

const ProfileUpdate = ({ userInfo, onSave, onCancel }) => {
  const [formData, setFormData] = useState(userInfo);
  const [errors, setErrors] = useState({});

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
    setErrors(prevErrors => ({
      ...prevErrors,
      [name]: '',
    }));
  };

  const handleCategoryChange = category => {
    setFormData(prevState => {
      const { categories } = prevState;
      if (categories.includes(category)) {
        return {
          ...prevState,
          categories: categories.filter(c => c !== category),
        };
      } else {
        return {
          ...prevState,
          categories: [...categories, category],
        };
      }
    });
  };

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      setFormData({
        ...formData,
        profile_img_url: URL.createObjectURL(file),
      });
      setErrors(prevErrors => ({
        ...prevErrors,
        profile_img_url: '',
      }));
    }
  };

  const validateEmail = email => {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(email);
  };

  const validateForm = () => {
    const newErrors = {};
    if (!validateEmail(formData.email)) {
      newErrors.email = '올바른 이메일 형식을 입력하세요.';
    }
    if (!formData.nickname) {
      newErrors.nickname = '닉네임을 입력하세요.';
    }
    if (!formData.introduction) {
      newErrors.introduction = '소개글을 입력하세요.';
    }
    if (formData.nickname.length > 10) {
      newErrors.nickname = '닉네임은 10자 이내로 설정해야 합니다.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (validateForm()) {
      onSave(formData);
    }
  };

  return (
    <div className='max-w-md mx-4 mt-10 p-4 bg-white border border-gray-300 rounded-lg'>
      <h2 className='text-xl font-bold mb-4'>회원 정보 수정</h2>
      <div className='border-t border-gray-300 mb-4'></div>
      <form onSubmit={handleSubmit}>
        <div className='mb-4'>
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='email'
          >
            이메일
          </label>
          <input
            type='email'
            id='email'
            name='email'
            value={formData.email}
            onChange={handleChange}
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.email ? 'border-red-500' : ''
            }`}
          />
          {errors.email && (
            <p className='text-red-500 text-xs italic'>{errors.email}</p>
          )}
        </div>
        <div className='mb-4'>
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='nickname'
          >
            닉네임
          </label>
          <input
            type='text'
            id='nickname'
            name='nickname'
            value={formData.nickname}
            onChange={handleChange}
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.nickname ? 'border-red-500' : ''
            }`}
          />
          {errors.nickname && (
            <p className='text-red-500 text-xs italic'>{errors.nickname}</p>
          )}
        </div>
        <div className='mb-4'>
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='profile_img_url'
          >
            프로필 이미지
          </label>
          <input
            type='file'
            id='profile_img_url'
            name='profile_img_url'
            onChange={handleFileChange}
            className='mt-1 p-2 block w-full border rounded-md'
          />
          {formData.profile_img_url && (
            <img
              src={formData.profile_img_url}
              alt='Profile Preview'
              className='mt-2 w-32 h-32 object-cover rounded-full inline-block'
            />
          )}
        </div>
        <div className='mb-4'>
          <label
            className='block mb-2 text-sm font-medium text-gray-700'
            htmlFor='introduction'
          >
            소개글
          </label>
          <textarea
            id='introduction'
            name='introduction'
            value={formData.introduction}
            onChange={handleChange}
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
              errors.introduction ? 'border-red-500' : ''
            }`}
          ></textarea>
          {errors.introduction && (
            <p className='text-red-500 text-xs italic'>{errors.introduction}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block mb-2 text-sm font-medium text-gray-700'>
            선호 카테고리
          </label>
          <div className='flex flex-wrap'>
            {categoriesList.map(category => (
              <span
                key={category}
                className={`mr-2 mb-2 px-2 py-1 border rounded-lg cursor-pointer text-gray-700 ${
                  formData.categories.includes(category)
                    ? 'bg-pink-100'
                    : 'bg-gray-100'
                }`}
                onClick={() => handleCategoryChange(category)}
              >
                {category}
              </span>
            ))}
          </div>
        </div>
        <div className='flex justify-end mt-14'>
          <button
            type='submit'
            className='bg-green-400 text-white px-4 py-2 rounded-md mr-4'
          >
            저장
          </button>
          <button
            type='button'
            className='bg-gray-500 text-white px-4 py-2 rounded-md'
            onClick={onCancel}
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
};

export default ProfileUpdate;
