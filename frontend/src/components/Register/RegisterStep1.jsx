import React from 'react';
import RegisterInput from './RegisterInput';
import Button from '../@common/Button';

const RegisterStep1 = ({
  formData,
  errors,
  handleChange,
  handleFileChange,
  handleNextStep,
}) => {
  return (
    <>
      <RegisterInput
        labelText='이메일'
        type='email'
        id='email'
        name='email'
        value={formData.email}
        onChange={handleChange}
        required
        error={errors.email}
      />
      <RegisterInput
        labelText='닉네임'
        type='text'
        id='nickname'
        name='nickname'
        value={formData.nickname}
        onChange={handleChange}
        required
        error={errors.nickname}
      />
      <RegisterInput
        labelText='비밀번호'
        type='password'
        id='password'
        name='password'
        value={formData.password}
        onChange={handleChange}
        required
        error={errors.password}
      />
      <RegisterInput
        labelText='비밀번호 확인'
        type='password'
        id='confirmPassword'
        name='confirmPassword'
        value={formData.confirmPassword}
        onChange={handleChange}
        required
        error={errors.confirmPassword}
      />
      <RegisterInput
        labelText='소개글'
        type='text'
        id='introduction'
        name='introduction'
        value={formData.introduction}
        onChange={handleChange}
        required
        error={errors.introduction}
      />
      <label className='block mb-2 text-sm font-medium text-gray-700'>
        프로필 이미지
        <div className='flex items-center mt-2'>
          <input
            type='file'
            accept='image/*'
            onChange={handleFileChange}
            className='hidden'
            id='profile_img_input'
          />
          <Button
            text='찾기'
            type='button'
            color='text-white bg-green-400 active:bg-green-600'
            size='small'
            full={false}
            onClick={() => document.getElementById('profile_img_input').click()}
          />
        </div>
        {formData.profile_img_url && (
          <img
            src={formData.profile_img_url}
            alt='Profile Preview'
            className='mt-2 w-32 h-32 object-cover rounded-full'
          />
        )}
      </label>
      <div className='flex justify-center mt-6 '>
        <Button
          text='다음'
          type='button'
          color='text-white bg-green-400 active:bg-green-600'
          size='large'
          full
          onClick={handleNextStep}
        />
      </div>
    </>
  );
};

export default RegisterStep1;
