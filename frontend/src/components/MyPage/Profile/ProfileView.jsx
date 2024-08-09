import React from 'react';
import Button from '@components/@common/Button';
import profileImgSample from '@assets/images/profile_img_sample.png';

const ProfileView = ({ member, categories, onEdit, onChangePassword }) => {
  const getCategoryName = categoryId => {
    const category = categories?.find(cat => cat.id === categoryId);
    return category ? category.name : '';
  };

  return (
    <div className='space-y-4 mb-16'>
      <div className='max-w-md mx-4 mt-10 p-4 bg-white border border-gray-300 rounded-lg'>
        <h2 className='text-xl font-bold mb-4'>회원 정보</h2>
        <div className='border-t border-gray-300 mb-4'></div>
        <div className='mb-4 flex'>
          <label className='text-gray-700 font-medium w-1/3'>닉네임</label>
          <p className='text-gray-700 font-medium w-2/3 text-right'>
            {member.nickName}
          </p>
        </div>
        <div className='mb-4 flex'>
          <label className='text-gray-700 font-medium w-1/3'>
            프로필 이미지
          </label>
          <div className='w-2/3 text-right'>
            <img
              src={member.profileImgUrl || profileImgSample}
              alt='Profile'
              className='w-16 h-16 rounded-full inline-block'
            />
          </div>
        </div>
        <div className='mb-4 flex'>
          <label className='text-gray-700 font-medium w-1/3'>소개글</label>
          <p className='text-gray-700 font-medium w-2/3 text-right max-h-24 overflow-y-auto'>
            {member.introduction}
          </p>
        </div>
        <div className='mb-4 flex'>
          <label className='text-gray-700 font-medium w-1/3'>
            선호 카테고리
          </label>
          <div className='flex flex-wrap w-2/3 text-right justify-end'>
            {member.categories?.map(category => (
              <span
                key={category}
                className='mr-2 mb-2 px-2 py-1 border rounded-lg text-gray-700 bg-pink-100'
              >
                {getCategoryName(category)}
              </span>
            ))}
          </div>
        </div>
        <div className='flex justify-end mt-4'>
          <Button text='수정' size='medium' onClick={onEdit} />
        </div>
      </div>
      <div className='max-w-md mx-4 p-4 bg-white border border-gray-300 rounded-lg mb-4'>
        <h2 className='text-xl font-bold mb-4'>비밀번호 변경</h2>
        <div className='border-t border-gray-300 mb-4'></div>
        <div className='flex justify-between items-center mt-4'>
          <span className='text-gray-700 font-medium'>비밀번호 변경</span>
          <Button
            text='변경'
            size='medium'
            color='text-white bg-blue-500 active:bg-blue-600'
            onClick={onChangePassword}
          />
        </div>
      </div>
      <div className='max-w-md mx-4 p-4 bg-white border border-gray-300 rounded-lg mb-4'>
        <h2 className='text-xl font-bold mb-4'>회원탈퇴</h2>
        <div className='border-t border-gray-300 mb-4'></div>
        <div className='flex justify-between items-center mt-4'>
          <span className='text-gray-700 font-medium'>회원탈퇴</span>
          <Button
            text='탈퇴'
            size='medium'
            color='text-white bg-pink-500 active:bg-pink-600'
            onClick={() => console.log('탈퇴')}
          />
        </div>
      </div>
    </div>
  );
};

export default ProfileView;
