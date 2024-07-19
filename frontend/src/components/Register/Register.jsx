// src/components/Register/Register.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Input from '../@common/Input';
import Button from '../@common/Button';
import Main from '../layout/Main';

const Register = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    email: '',
    nickname: '',
    password: '',
    confirmPassword: '',
    introduction: '',
    profile_img_url: '',
    age: '',
    gender: '',
    categories: [],
    is_receive_letter_email: false,
  });

  const navigate = useNavigate();

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setFormData(prevFormData => ({
      ...prevFormData,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleFileChange = e => {
    const file = e.target.files[0];
    if (file) {
      setFormData(prevFormData => ({
        ...prevFormData,
        profile_img_url: URL.createObjectURL(file),
      }));
    }
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

  const handleNextStep = () => {
    if (step === 1 && formData.password !== formData.confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    setStep(step + 1);
  };

  const handlePrevStep = () => {
    setStep(step - 1);
  };

  const handleSubmit = e => {
    e.preventDefault();

    if (formData.nickname.length > 10) {
      alert('닉네임은 10자 이내로 설정해야 합니다.');
      return;
    }

    alert('회원가입이 완료되었습니다.');
    setStep(3);
  };

  return (
    <Main>
      <div className='flex flex-col justify-center items-center min-h-screen px-4 w-full'>
        <div className='w-full max-w-md'>
          <h2 className='text-2xl font-bold mb-4 text-center px-44'>
            회원가입
          </h2>
          <form onSubmit={handleSubmit}>
            {step === 1 && (
              <>
                <Input
                  labelText='이메일'
                  type='email'
                  id='email'
                  name='email'
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
                <Input
                  labelText='닉네임'
                  type='text'
                  id='nickname'
                  name='nickname'
                  value={formData.nickname}
                  onChange={handleChange}
                  required
                />
                <Input
                  labelText='비밀번호'
                  type='password'
                  id='password'
                  name='password'
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
                <Input
                  labelText='비밀번호 확인'
                  type='password'
                  id='confirmPassword'
                  name='confirmPassword'
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                />
                <Input
                  labelText='소개글'
                  type='text'
                  id='introduction'
                  name='introduction'
                  value={formData.introduction}
                  onChange={handleChange}
                  required
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
                      color='text-white bg-blue-500 active:bg-blue-600'
                      size='small'
                      full={false}
                      onClick={() =>
                        document.getElementById('profile_img_input').click()
                      }
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
                    color='text-white bg-green-500 active:bg-green-600'
                    size='large'
                    full
                    onClick={handleNextStep}
                  />
                </div>
              </>
            )}
            {step === 2 && (
              <>
                <h3 className='text-xl font-bold mb-4 text-center'>
                  추가 정보를 알려주세요.
                </h3>
                <Input
                  labelText='연령'
                  type='number'
                  id='age'
                  name='age'
                  value={formData.age}
                  onChange={handleChange}
                  required
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
                  </div>
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
                      '과학/사회과학',
                      '자기계발',
                    ].map(category => (
                      <div key={category} className='mr-2 mb-2'>
                        <label className='flex items-center border px-2 py-1 rounded-lg cursor-pointer'>
                          <input
                            type='checkbox'
                            name='categories'
                            value={category}
                            checked={formData.categories.includes(category)}
                            onChange={() => handleCategoryChange(category)}
                            className='hidden'
                          />
                          <span
                            className={`ml-2 ${
                              formData.categories.includes(category)
                                ? 'text-green-500'
                                : 'text-gray-700'
                            }`}
                          >
                            {category}
                          </span>
                        </label>
                      </div>
                    ))}
                  </div>
                </label>
                <div className='flex justify-between mt-6'>
                  <Button
                    text='이전'
                    type='button'
                    color='text-white bg-gray-500 active:bg-gray-600'
                    size='large'
                    full={false}
                    onClick={handlePrevStep}
                  />
                  <Button
                    text='가입 완료'
                    type='submit'
                    color='text-white bg-green-500 active:bg-green-600'
                    size='large'
                    full={false}
                  />
                </div>
              </>
            )}
            {step === 3 && (
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
                  color='text-white bg-green-500 active:bg-green-600'
                  size='large'
                  full
                  onClick={() => navigate('/Library')}
                />
              </div>
            )}
          </form>
        </div>
      </div>
    </Main>
  );
};

export default Register;
