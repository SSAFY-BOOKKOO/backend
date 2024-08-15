import React from 'react';
import Button from '@components/@common/Button';
import RegisterInput from './RegisterInput';
import RadioButton from '@components/@common/RadioButton';

const RegisterStep3 = ({
  formData,
  errors,
  handleChange,
  handleSubmit,
  handlePrevStep,
}) => {
  const handleFormSubmit = e => {
    e.preventDefault();
    handleSubmit(e);
  };

  const reviewVisibilityTags = [
    { id: 1, value: 'PUBLIC', name: '전체 공개' },
    { id: 2, value: 'FOLLOWER_PUBLIC', name: '팔로워 공개' },
    { id: 3, value: 'PRIVATE', name: '비공개' },
  ];

  return (
    <>
      <div className='text-center mb-4'>
        <h3 className='text-xl font-bold text-left'>
          추가 정보를 입력해주세요
        </h3>
        <p className='text-gray-500 text-left'>
          다른 유저와의 교류를 위한 정보입니다.
        </p>
      </div>
      <label className='block mb-2 text-md font-medium text-gray-700'>
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
      <label className='block mb-2 text-md font-medium text-gray-700'>
        한줄평 공개 설정
        <RadioButton
          tags={reviewVisibilityTags}
          selectedTag={formData.reviewVisibility}
          setSelectedTag={value =>
            handleChange({ target: { name: 'reviewVisibility', value } })
          }
        />
        {errors.reviewVisibility && (
          <p className='text-red-500 text-sm'>{errors.reviewVisibility}</p>
        )}
      </label>
      <RegisterInput
        labelText='소개글'
        type='textarea'
        id='introduction'
        name='introduction'
        value={formData.introduction}
        onChange={handleChange}
        required
        error={errors.introduction}
      />
      <div className='flex justify-between mt-6'>
        <Button
          text='이전'
          type='button'
          color='text-white bg-gray-500 active:bg-gray-600'
          size='medium'
          full={false}
          onClick={handlePrevStep}
        />
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
