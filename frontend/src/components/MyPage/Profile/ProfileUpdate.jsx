import React, { useState } from 'react';

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

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      setFormData({
        ...formData,
        profileImage: URL.createObjectURL(file),
      });
      setErrors(prevErrors => ({
        ...prevErrors,
        profileImage: '',
      }));
    }
  };

  const validateEmail = email => {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(email);
  };

  const validatePassword = password => {
    const re = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,16}$/;
    return re.test(password);
  };

  const validateForm = () => {
    const newErrors = {};
    if (!validateEmail(formData.email)) {
      newErrors.email = '올바른 이메일 형식을 입력하세요.';
    }
    if (formData.password && !validatePassword(formData.password)) {
      newErrors.password =
        '비밀번호는 영문, 숫자, 특수문자 조합으로 이루어진 8~16자여야 합니다.';
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
    <div className='max-w-md mx-auto mt-10 p-4 bg-white shadow rounded-lg'>
      <h2 className='text-xl font-bold mb-4'>회원 정보 수정</h2>
      <form onSubmit={handleSubmit}>
        <div className='mb-4'>
          <label className='block text-gray-700'>닉네임</label>
          <input
            type='text'
            name='nickname'
            value={formData.nickname}
            onChange={handleChange}
            className={`mt-1 p-2 block w-full border rounded-md ${errors.nickname ? 'border-red-500' : ''}`}
          />
          {errors.nickname && (
            <p className='text-red-500 text-xs italic'>{errors.nickname}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>프로필 이미지</label>
          <input
            type='file'
            name='profileImage'
            onChange={handleFileChange}
            className='mt-1 p-2 block w-full border rounded-md'
          />
          {formData.profileImage && (
            <img
              src={formData.profileImage}
              alt='Profile Preview'
              className='mt-2 w-32 h-32 object-cover rounded-full'
            />
          )}
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>이메일</label>
          <input
            type='email'
            name='email'
            value={formData.email}
            onChange={handleChange}
            className={`mt-1 p-2 block w-full border rounded-md ${errors.email ? 'border-red-500' : ''}`}
          />
          {errors.email && (
            <p className='text-red-500 text-xs italic'>{errors.email}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>비밀번호 (변경 시 입력)</label>
          <input
            type='password'
            name='password'
            value={formData.password}
            onChange={handleChange}
            className={`mt-1 p-2 block w-full border rounded-md ${errors.password ? 'border-red-500' : ''}`}
          />
          {errors.password && (
            <p className='text-red-500 text-xs italic'>{errors.password}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>레터 수신</label>
          <input
            type='checkbox'
            name='receiveLetters'
            checked={formData.receiveLetters}
            onChange={handleChange}
            className='mt-1'
          />
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>소개글</label>
          <textarea
            name='introduction'
            value={formData.introduction}
            onChange={handleChange}
            className={`mt-1 p-2 block w-full border rounded-md ${errors.introduction ? 'border-red-500' : ''}`}
          ></textarea>
          {errors.introduction && (
            <p className='text-red-500 text-xs italic'>{errors.introduction}</p>
          )}
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>선호 카테고리</label>
          <input
            type='text'
            name='favoriteCategory'
            value={formData.favoriteCategory}
            onChange={handleChange}
            className='mt-1 p-2 block w-full border rounded-md'
          />
        </div>
        <div className='flex justify-between'>
          <button
            type='submit'
            className='bg-green-400 text-white px-4 py-2 rounded-md'
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
