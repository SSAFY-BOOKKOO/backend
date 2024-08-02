import React from 'react';
import { Link } from 'react-router-dom';
import settingIcon from '@assets/icons/setting.png';
// import statisticsIcon from '@assets/icons/statistics.png';
import { FaCalendarDays } from 'react-icons/fa6';
// import friendsIcon from '@assets/icons/friends.png';
import { MdPeopleAlt } from 'react-icons/md';
// import quoteIcon from '@assets/icons/quote.png';
import { BsChatSquareQuoteFill } from 'react-icons/bs';
// import myWritingIcon from '@assets/icons/my_writing.png';
import { FaClipboardList } from 'react-icons/fa6';
import profile_img_sample from '@assets/images/profile_img_sample.png';

const MyPage = () => {
  const member = {
    nickname: '닉네임',
    introduction: '소개문',
    category: '카테고리',
    profile_img_url: profile_img_sample,
  };

  return (
    <div className='p-4 min-h-[43rem]'>
      <div className='flex items-center justify-between'>
        <div className='flex items-center space-x-8'>
          <img
            src={member.profile_img_url}
            alt='profile'
            className='w-1/2 h-32 rounded-full'
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
              <FaCalendarDays className='w-8 h-8' />
            </button>
          </Link>
          <p className='text-lg'>통계</p>
        </div>
        <div className='flex flex-col items-center'>
          <Link to='/mypage/quote'>
            <button className='p-4 rounded-full'>
              <BsChatSquareQuoteFill className='w-8 h-8' />
            </button>
          </Link>
          <p className='text-lg'>내 글귀</p>
        </div>
        <div className='flex flex-col items-center'>
          <button className='p-4 rounded-full'>
            <FaClipboardList className='w-8 h-8' />
          </button>
          <p className='text-lg'>내가 쓴 글</p>
        </div>
        <div className='flex flex-col items-center'>
          <Link to='/mypage/friend'>
            <button className='p-4 rounded-full'>
              <MdPeopleAlt className='w-8 h-8' />
            </button>
          </Link>
          <p className='text-lg'>친구</p>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
