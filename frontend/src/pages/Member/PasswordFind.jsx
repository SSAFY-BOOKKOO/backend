import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import Button from '@components/@common/Button';
import Input from '@components/@common/Input';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import { validateForm } from '@utils/ValidateForm';

const PasswordFind = () => {
  const [email, setEmail] = useState('');
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();
  const [, setAlert] = useAtom(alertAtom);

  const handleChange = e => {
    setEmail(e.target.value);
    setErrors(prevErrors => ({
      ...prevErrors,
      email: '',
    }));
  };

  const handleSubmit = e => {
    e.preventDefault();
    const validationConfig = { email: true };
    const formData = { email };
    const newErrors = validateForm(formData, validationConfig);
    setErrors(newErrors);

    if (Object.keys(newErrors).length === 0) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '비밀번호 재설정 이메일이 전송되었습니다.',
        onConfirm: () => navigate('/login'),
      });
    }
  };

  return (
    <div className='flex flex-col justify-center items-center min-h-screen px-4 w-full'>
      <div className='w-full max-w-md'>
        <h2 className='text-2xl font-bold mb-4 text-center'>비밀번호 찾기</h2>
        <form onSubmit={handleSubmit}>
          <div className='mb-4'>
            <label className='block text-gray-700'>이메일</label>
            <Input
              type='email'
              name='email'
              value={email}
              onChange={handleChange}
              className={`mt-1 p-2 block w-full border rounded-md ${errors.email ? 'border-red-500' : ''}`}
            />
            {errors.email && (
              <p className='text-red-500 text-xs italic'>{errors.email}</p>
            )}
          </div>
          <div className='flex justify-center mt-6'>
            <Button
              text='확인'
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

export default PasswordFind;
