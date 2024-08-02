import React from 'react';

const ProfileView = ({ userInfo, onEdit }) => {
  return (
    <div className='max-w-md mx-auto mt-10 p-4 bg-white shadow rounded-lg'>
      <h2 className='text-xl font-bold mb-4'>회원 정보</h2>
      <div className='mb-4'>
        <label className='block text-gray-700'>닉네임</label>
        <p>{userInfo.nickname}</p>
      </div>
      <div className='mb-4'>
        <label className='block text-gray-700'>프로필 이미지</label>
        <img
          src={userInfo.profile_img_url}
          alt='Profile'
          className='w-16 h-16 rounded-full'
        />
      </div>
      <div className='mb-4'>
        <label className='block text-gray-700'>이메일</label>
        <p>{userInfo.email}</p>
      </div>
      <div className='mb-4'>
        <label className='block text-gray-700'>비밀번호</label>
        <p>********</p>
      </div>
      <div className='mb-4'>
        <label className='block text-gray-700'>레터 수신</label>
        <p>{userInfo.receiveLetters ? '예' : '아니오'}</p>
      </div>
      <div className='mb-4'>
        <label className='block text-gray-700'>소개글</label>
        <p>{userInfo.introduction}</p>
      </div>
      <div className='mb-4'>
        <label className='block text-gray-700'>선호 카테고리</label>
        <p>{userInfo.favoriteCategory}</p>
      </div>
      <div className='flex justify-between'>
        <button
          type='button'
          className='bg-green-400 text-white px-4 py-2 rounded-md'
          onClick={onEdit}
        >
          수정
        </button>
        <button
          type='button'
          className='bg-red-500 text-white px-4 py-2 rounded-md'
          onClick={() => console.log('탈퇴')}
        >
          탈퇴
        </button>
      </div>
    </div>
  );
};

export default ProfileView;
