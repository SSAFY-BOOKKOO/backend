import React from 'react';
import { Link } from 'react-router-dom';
import settingIcon from '@assets/icons/setting.png';
import { FaCalendarDays } from 'react-icons/fa6';
import { MdPeopleAlt } from 'react-icons/md';
import { BsChatSquareQuoteFill } from 'react-icons/bs';
import { FaClipboardList } from 'react-icons/fa6';
import profileImgSample from '@assets/images/profile_img_sample.png';

const MyPage = () => {
  const member = {
    nickname: '하츄핑',
    introduction:
      '소캐치! 티니핑 시리즈의 메인 티니핑 및 메인 로열핑으로 로미와 더불어 양대 주인공이자 메인 마스코트 캐릭터.',
    categories: ['추리/스릴러', '로맨스', '인문학', '철학'],
    profile_img_url: profileImgSample,
  };

  const displayCategories =
    member.categories.length > 2
      ? member.categories.slice(0, 2).concat(['...'])
      : member.categories;

  return (
    <div className='p-4 min-h-[43rem]'>
      <div className='flex items-start justify-between'>
        <div className='flex items-start space-x-8'>
          <img
            src={member.profile_img_url}
            alt='profile'
            className='w-32 h-32 rounded-full'
          />
          <div className='flex flex-col'>
            <h2 className='text-2xl font-bold'>{member.nickname}</h2>
            <p className='text-md'>{member.introduction}</p>
            <div className='flex flex-wrap mt-2'>
              {displayCategories.map((category, index) => (
                <span
                  key={index}
                  className='mr-2 mb-2 px-2 py-1 border rounded-lg text-gray-700 bg-gray-100'
                >
                  {category}
                </span>
              ))}
            </div>
          </div>
        </div>
        <div className='flex-none'>
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
