import React from 'react';
import { Link } from 'react-router-dom';

const MyPage = () => {
  const member = {
    nickname: '닉네임',
    introduction: '소개문',
    category: '카테고리',
    profilePicture: 'https://via.placeholder.com/200',
  };

  return (
    <div className='p-4'>
      <div className='flex items-center justify-between'>
        <div className='flex items-center space-x-8'>
          <img
            src={member.profilePicture}
            alt='profile'
            className='w-32 h-32 rounded-full'
          />
          <div>
            <h2 className='text-2xl font-bold'>{member.nickname}</h2>
            <p className='text-lg'>{member.introduction}</p>
            <p className='text-lg'>{member.category}</p>
          </div>
        </div>
        <div className='flex space-x-4'>
          <button className='p-2 rounded'>
            <img
              src='src\assets\icons\setting.png'
              alt='setting'
              className='w-8 h-8'
            />
          </button>
        </div>
      </div>
      <hr className='my-4' />
      <div className='grid grid-cols-4 gap-4 text-center'>
        <div>
          <button className='bg-gray-200 p-4 rounded-full'>
            <svg
              xmlns='http://www.w3.org/2000/svg'
              className='h-8 w-8 mx-auto'
              fill='none'
              viewBox='0 0 24 24'
              stroke='currentColor'
            >
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                d='M12 4v16m8-8H4'
              />
            </svg>
          </button>
          <p className='mt-2'>내 일기</p>
        </div>
        <div>
          <Link to='/mypage/statistics'>
            <button className='p-4 rounded-full'>
              <img src='@assets\icons\statistics.png' alt='statistics' />
            </button>
          </Link>
          <p className='mt-2'>통계</p>
        </div>
        <div>
          <button className='bg-gray-200 p-4 rounded-full'>
            <svg
              xmlns='http://www.w3.org/2000/svg'
              className='h-8 w-8 mx-auto'
              fill='none'
              viewBox='0 0 24 24'
              stroke='currentColor'
            >
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                d='M12 12c2.21 0 4-1.79 4-4S14.21 4 12 4 8 5.79 8 8s1.79 4 4 4z'
              />
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                strokeWidth='2'
                d='M12 14c-4.418 0-8 2.239-8 5v1h16v-1c0-2.761-3.582-5-8-5z'
              />
            </svg>
          </button>
          <p className='mt-2'>나의 일기</p>
        </div>
        <div>
          <Link to='/mypage/friend'>
            <button className='p-4 rounded-full'>
              <img src='@assets\icons\friends.png' alt='friend' />
            </button>
          </Link>
          <p className='mt-2'>친구</p>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
