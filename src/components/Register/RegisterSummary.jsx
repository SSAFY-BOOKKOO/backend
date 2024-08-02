import React from 'react';
import Button from '../@common/Button';

const RegisterSummary = ({ formData, navigate }) => {
  return (
    <div className='text-center'>
      <h3 className='text-xl font-bold mb-4'>입력된 정보</h3>
      <p>
        <strong>이메일:</strong> {formData.email}
      </p>
      <p>
        <strong>닉네임:</strong> {formData.nickname}
      </p>
      <p>
        <strong>소개글:</strong> {formData.introduction}
      </p>
      <p>
        <strong>프로필 이미지:</strong>{' '}
        {formData.profile_img_url ? (
          <img
            src={formData.profile_img_url}
            alt='Profile'
            className='mt-2 w-32 h-32 object-cover rounded-full mx-auto'
          />
        ) : (
          '선택하지 않음'
        )}
      </p>
      <p>
        <strong>연령:</strong> {formData.age}
      </p>
      <p>
        <strong>성별:</strong> {formData.gender || '선택하지 않음'}
      </p>
      <p>
        <strong>선호 카테고리:</strong>{' '}
        {formData.categories.length > 0
          ? formData.categories.join(', ')
          : '선택하지 않음'}
      </p>
      <p>
        <strong>레터 이메일 수신 여부:</strong>{' '}
        {formData.is_receive_letter_email ? '예' : '아니요'}
      </p>
      <Button
        text='확인'
        type='button'
        color='text-white bg-green-400 active:bg-green-600'
        size='large'
        full
        onClick={() => navigate('/Library')}
      />
    </div>
  );
};

export default RegisterSummary;
