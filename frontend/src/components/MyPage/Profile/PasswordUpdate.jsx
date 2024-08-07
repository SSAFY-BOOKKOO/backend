import React, { useState } from 'react';
import { useSetAtom } from 'jotai';
import { authAxiosInstance } from '@services/axiosInstance';
import Button from '@components/@common/Button';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';

const PasswordUpdate = ({ onCancel }) => {
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const setAlert = useSetAtom(alertAtom);

  const handleSubmit = async e => {
    e.preventDefault();

    if (newPassword !== confirmPassword) {
      setError('새 비밀번호가 일치하지 않습니다.');
      return;
    }

    try {
      await authAxiosInstance.patch('/members/info/password', {
        password: newPassword,
      });
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '비밀번호가 성공적으로 변경되었습니다.',
        onConfirm: onCancel,
      });
    } catch (error) {
      setError('비밀번호 변경에 실패했습니다.');
    }
  };

  return (
    <div className='max-w-md mx-4 mt-10 p-4 bg-white border border-gray-300 rounded-lg'>
      <h2 className='text-xl font-bold mb-4'>비밀번호 변경</h2>
      <form onSubmit={handleSubmit}>
        <div className='mb-4'>
          <label className='block text-gray-700 font-medium'>새 비밀번호</label>
          <input
            type='password'
            value={newPassword}
            onChange={e => setNewPassword(e.target.value)}
            required
            className='mt-1 p-2 block w-full border rounded-md'
          />
        </div>
        <div className='mb-4'>
          <label className='block text-gray-700 font-medium'>
            새 비밀번호 확인
          </label>
          <input
            type='password'
            value={confirmPassword}
            onChange={e => setConfirmPassword(e.target.value)}
            required
            className='mt-1 p-2 block w-full border rounded-md'
          />
        </div>
        {error && <p className='text-red-500 text-xs italic'>{error}</p>}
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
      <Alert />
    </div>
  );
};

export default PasswordUpdate;
