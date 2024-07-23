import React, { useState } from 'react';

const ProfileUpdate = ({ userInfo, onSave, onCancel }) => {
  const [formData, setFormData] = useState(userInfo);

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = e => {
    e.preventDefault();
    onSave(formData);
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
            className='mt-1 p-2 block w-full border rounded-md'
          />
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>프로필 이미지</label>
          <input
            type='file'
            name='profileImage'
            onChange={e =>
              setFormData({
                ...formData,
                profileImage: URL.createObjectURL(e.target.files[0]),
              })
            }
            className='mt-1 p-2 block w-full border rounded-md'
          />
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>이메일</label>
          <input
            type='email'
            name='email'
            value={formData.email}
            onChange={handleChange}
            className='mt-1 p-2 block w-full border rounded-md'
          />
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700'>비밀번호</label>
          <input
            type='password'
            name='password'
            value={formData.password}
            onChange={handleChange}
            className='mt-1 p-2 block w-full border rounded-md'
          />
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
            className='mt-1 p-2 block w-full border rounded-md'
          ></textarea>
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
