import React, { useState } from 'react';
import Button from '@components/@common/Button';

const AdditionalSetting = ({ userInfo, onSave }) => {
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
    <div className='space-y-4 mb-16'>
      <div className='max-w-md mx-4 mt-10 p-4 bg-white border border-gray-300 rounded-lg'>
        <h2 className='text-xl font-bold mb-4'>한줄평 공개 설정</h2>
        <div className='border-t border-gray-300 mb-4'></div>
        <label className='block mb-2 text-sm font-medium text-gray-700'>
          <div className='flex flex-col mt-2'>
            <label className='flex items-center'>
              <input
                type='radio'
                id='one_line_review_privacy-0'
                name='one_line_review_privacy'
                value='public_reveal'
                checked={formData.one_line_review_privacy === 'public_reveal'}
                onChange={handleChange}
              />
              <span className='ml-2'>모두에게</span>
            </label>
            <label className='flex items-center'>
              <input
                type='radio'
                id='one_line_review_privacy-1'
                name='one_line_review_privacy'
                value='follower_reveal'
                checked={formData.one_line_review_privacy === 'follower_reveal'}
                onChange={handleChange}
              />
              <span className='ml-2'>팔로워에게만</span>
            </label>
            <label className='flex items-center'>
              <input
                type='radio'
                id='one_line_review_privacy-2'
                name='one_line_review_privacy'
                value='private_reveal'
                checked={formData.one_line_review_privacy === 'private_reveal'}
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
          <label className='text-gray-700 font-medium w-1/3'>레터 수신</label>
          <div className='w-2/3 flex justify-end'>
            <input
              type='checkbox'
              name='receiveLetters'
              checked={formData.receiveLetters}
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
