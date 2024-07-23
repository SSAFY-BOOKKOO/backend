import React from 'react';
import { Link } from 'react-router-dom';
import settingIcon from '@assets/icons/setting.png';
import statisticsIcon from '@assets/icons/statistics.png';
import friendsIcon from '@assets/icons/friends.png';
import quoteIcon from '@assets/icons/quote.png';
import myWritingIcon from '@assets/icons/my_writing.png';

const MyPage = () => {
  const member = {
    nickname: '닉네임',
    introduction: '소개문',
    category: '카테고리',
    profilePicture: 'https://via.placeholder.com/200',
  };

  return (
    <div className='p-4 min-h-screen'>
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
          <Link to='/mypage/profile'>
            <button className='p-2 rounded'>
              <img src={settingIcon} alt='setting' className='w-8 h-8' />
            </button>
          </Link>
        </div>
      </div>
      <hr className='my-4' />
      <div className='grid grid-cols-2 gap-x-8 gap-y-12 text-center'>
        <div className='flex flex-col items-center'>
          <Link to='/mypage/statistics'>
            <button className='p-4 rounded-full'>
              <img src={statisticsIcon} alt='statistics' className='w-8 h-8' />
            </button>
          </Link>
          <p className='mt-2'>통계</p>
        </div>
        <div className='flex flex-col items-center'>
          <Link to='/mypage/quote'>
            <button className='p-4 rounded-full'>
              <img src={quoteIcon} alt='setting' className='w-8 h-8' />
            </button>
          </Link>
          <p className='mt-2'>내 글귀</p>
        </div>
        <div className='flex flex-col items-center'>
          <button className='p-4 rounded-full'>
            <img src={myWritingIcon} alt='setting' className='w-8 h-8' />
          </button>
          <p className='mt-2'>내가 쓴 글</p>
        </div>
        <div className='flex flex-col items-center'>
          <Link to='/mypage/friend'>
            <button className='p-4 rounded-full'>
              <img src={friendsIcon} alt='friend' className='w-8 h-8' />
            </button>
          </Link>
          <p className='mt-2'>친구</p>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
