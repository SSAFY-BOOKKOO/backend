import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import settingIcon from '@assets/icons/setting.png';
import { FaCalendarDays } from 'react-icons/fa6';
import { MdPeopleAlt } from 'react-icons/md';
import { BsChatSquareQuoteFill } from 'react-icons/bs';
import { FaClipboardList } from 'react-icons/fa6';
import profileImgSample from '@assets/images/profile_img_sample.png';
import { authAxiosInstance, axiosInstance } from '@services/axiosInstance';

const MyPage = () => {
  const [member, setMember] = useState(null);

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const response = await authAxiosInstance.get(
          '/members/info?memberId=312c2435-d0b5-4607-808d-fc0e9c51b58d'
        );
        setMember(response.data);
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchMemberInfo();
  }, []);

  if (!member) {
    return <div>Loading...</div>;
  }

  const displayCategories =
    member.categories.length > 4
      ? member.categories.slice(0, 2).concat(['...'])
      : member.categories;

  return (
    <div className='p-4 min-h-[43rem]'>
      <div className='flex items-start justify-between'>
        <div className='flex items-start space-x-8'>
          <img
            src={member.profileImgUrl || profileImgSample}
            alt='profile'
            className='w-32 h-32 rounded-full'
          />
          <div className='flex flex-col'>
            <h2 className='text-2xl font-bold'>{member.nickName}</h2>
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
