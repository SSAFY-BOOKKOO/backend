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
            id='is_receive_letter_email'
            name='is_receive_letter_email'
            checked={formData.is_receive_letter_email}
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
              id='one_line_review_privacy-0'
              name='one_line_review_privacy'
              value='public_reveal'
              checked={formData?.one_line_review_privacy === 'public_reveal'}
              onChange={handleChange}
            />
            <span className='ml-2'>전체 공개</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='one_line_review_privacy-1'
              name='one_line_review_privacy'
              value='follower_reveal'
              checked={formData?.one_line_review_privacy === 'follower_reveal'}
              onChange={handleChange}
            />
            <span className='ml-2'>팔로워 공개</span>
          </label>
          <label className='flex items-center'>
            <input
              type='radio'
              id='one_line_review_privacy-2'
              name='one_line_review_privacy'
              value='private_reveal'
              checked={formData?.one_line_review_privacy === 'private_reveal'}
              onChange={handleChange}
            />
            <span className='ml-2'>비공개</span>
          </label>
        </div>
        {errors?.one_line_review_privacy && (
          <p className='text-red-500 text-sm'>
            {errors?.one_line_review_privacy}
          </p>
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
