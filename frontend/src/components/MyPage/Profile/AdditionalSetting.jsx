import React, { useState } from 'react';
import { useAtom } from 'jotai';
import Button from '@components/@common/Button';
import { authAxiosInstance } from '@services/axiosInstance';
import { alertAtom } from '@atoms/alertAtom';

const AdditionalSetting = ({ userInfo, onSave }) => {
  const [formData, setFormData] = useState({
    isLetterReceive: userInfo?.isLetterReceive ?? false,
    reviewVisibility: userInfo?.reviewVisibility ?? 'PUBLIC',
  });
  const [, setAlert] = useAtom(alertAtom);

  const handleChange = e => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const response = await authAxiosInstance.put('/members/info/setting', {
        isLetterReceive: formData.isLetterReceive,
        reviewVisibility: formData.reviewVisibility,
      });
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '설정이 성공적으로 저장되었습니다.',
      });
      onSave(response.data);
    } catch (error) {
      console.error('Failed to update settings:', error);
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '설정을 업데이트하는 중 오류가 발생했습니다.',
      });
    }
  };

  return (
    <div className='space-y-4 mb-16'>
      <div className='max-w-md mx-4 mt-10 p-4 bg-white border border-gray-300 rounded-lg'>
        <h2 className='text-xl font-bold mb-4'>한줄평 공개 설정</h2>
        <div className='border-t border-gray-300 mb-4'></div>
        <label className='block mb-2 text-sm font-medium text-gray-700'>
          <div className='flex flex-col mt-2'>
            <label className='flex items-center mb-2 text-sm'>
              {' '}
              <input
                type='radio'
                id='reviewVisibility-public'
                name='reviewVisibility'
                value='PUBLIC'
                checked={formData.reviewVisibility === 'PUBLIC'}
                onChange={handleChange}
              />
              <span className='ml-2'>모두에게</span>
            </label>
            <label className='flex items-center mb-2 text-sm'>
              {' '}
              <input
                type='radio'
                id='reviewVisibility-follower_public'
                name='reviewVisibility'
                value='FOLLOWER_PUBLIC'
                checked={formData.reviewVisibility === 'FOLLOWER_PUBLIC'}
                onChange={handleChange}
              />
              <span className='ml-2'>팔로워에게만</span>
            </label>
            <label className='flex items-center text-sm'>
              {' '}
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
        </label>
      </div>
      <div className='max-w-md mx-4 p-4 bg-white border border-gray-300 rounded-lg mb-4'>
        <h2 className='text-xl font-bold mb-4'>레터 수신 설정</h2>
        <div className='border-t border-gray-300 mb-4'></div>
        <div className='flex items-center'>
          <label className='text-gray-700 font-medium w-1/3 text-sm'>
            {' '}
            레터 수신
          </label>
          <div className='w-2/3 flex justify-end'>
            <input
              type='checkbox'
              name='isLetterReceive'
              checked={formData.isLetterReceive}
              onChange={handleChange}
              className='h-6 w-6'
            />
          </div>
        </div>
      </div>
      <div className='flex justify-end mt-4 mx-4'>
        <Button text='저장' size='medium' onClick={handleSubmit} />
      </div>
    </div>
  );
};

export default AdditionalSetting;
