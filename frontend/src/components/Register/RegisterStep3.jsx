import React from 'react';
import Button from '../@common/Button';

const RegisterStep3 = ({ formData, errors, handleChange, handleSubmit }) => {
  const handleFormSubmit = e => {
    e.preventDefault();
    handleSubmit(e);
  };

  return (
    <>
      <h3 className='text-xl font-bold mb-4 text-center'>추가 설정</h3>
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        큐레이션 레터 수신 알림
        <div className='mt-2'>
          <input
            type='checkbox'
            id='isLetterReceive'
            name='isLetterReceive'
            checked={formData.isLetterReceive}
            onChange={handleChange}
            className='mr-2'
          />
          <span>수신 여부</span>
        </div>
      </label>
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        한줄평 공개 설정
        <div className='flex space-x-4 mt-2 justify-center'>
          <label className='flex items-center'>
            <input
              type='radio'
              id='reviewVisibility-public'
              name='reviewVisibility'
              value='PUBLIC'
              checked={formData.reviewVisibility === 'PUBLIC'}
              onChange={handleChange}
            />
            <span className='ml-2'>전체 공개</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='reviewVisibility-follower_public'
              name='reviewVisibility'
              value='FOLLOWER_PUBLIC'
              checked={formData.reviewVisibility === 'FOLLOWER_PUBLIC'}
              onChange={handleChange}
            />
            <span className='ml-2'>팔로워 공개</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='reviewVisibility-private'
              name='reviewVisibility'
              value='PRIVATE'
              checked={formData.reviewVisibility === 'PRIVATE'}
              onChange={handleChange}
            />
            <span className='ml-2'>비공개</span>
          </label>
        </div>
        {errors.reviewVisibility && (
          <p className='text-red-500 text-sm'>{errors.reviewVisibility}</p>
        )}
      </label>
      <div className='flex justify-end mt-6'>
        <Button
          text='가입 완료'
          type='submit'
          color='text-white bg-green-400 active:bg-green-600'
          size='medium'
          full={false}
          onClick={handleFormSubmit}
        />
      </div>
    </>
  );
};

export default RegisterStep3;
