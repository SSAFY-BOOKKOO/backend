import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaCalendarDays, FaClipboardList } from 'react-icons/fa6';
import { MdPeopleAlt } from 'react-icons/md';
import { BsChatSquareQuoteFill } from 'react-icons/bs';
import profileImgSample from '@assets/images/profile_img_sample.png';
import settingIcon from '@assets/icons/setting.png';
import { authAxiosInstance } from '@services/axiosInstance';
import IconButton from '@components/@common/IconButton';
import ProfileModal from '@components/@common/ProfileModal.jsx';
import { postCategories } from '@services/Book';
import Button from '@components/@common/Button';
import Spinner from '@components/@common/Spinner';

const MyPage = () => {
  const [member, setMember] = useState(null);
  const [categories, setCategories] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const memberResponse = await authAxiosInstance.get('/members/info');
        setMember(memberResponse.data);

        const categoriesResponse = await postCategories();
        setCategories(categoriesResponse);
      } catch (error) {
        console.error(error);
      } finally {
        setIsLoading(false); // Set loading to false after data is fetched
      }
    };

    fetchMemberInfo();
  }, []);

  if (isLoading) {
    return (
      <div className='flex items-center justify-center min-h-screen'>
        <Spinner /> {/* Use the Spinner component while loading */}
      </div>
    );
  }

  if (!member) {
    return <div>Error loading data.</div>;
  }

  const getCategoryName = categoryId => {
    const category = categories.find(cat => cat.id === categoryId);
    return category ? category.name : '';
  };

  const displayCategories =
    member.categories.length > 4
      ? member.categories.slice(0, 2).concat(['...'])
      : member.categories;

  const handleProfileClick = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleLogout = async () => {
    try {
      await authAxiosInstance.post('/auth/logout');
    } catch (error) {
      console.error('Logout failed:', error);
    } finally {
      localStorage.removeItem('ACCESS_TOKEN');
      navigate('/login');
    }
  };

  return (
    <div className='relative p-4 min-h-[43rem] pb-20'>
      <div className='flex items-start justify-between mb-8'>
        <div className='flex items-start space-x-8 w-full'>
          <img
            src={member.profileImgUrl || profileImgSample}
            alt='profile'
            className='w-32 h-32 rounded-full cursor-pointer'
            onClick={handleProfileClick}
          />
          <div className='flex flex-col flex-grow space-y-4'>
            <div className='flex items-center justify-between'>
              <h2 className='text-xl font-bold'>{member.nickName}</h2>
              <Link to='/mypage/profile'>
                <IconButton
                  icon={() => (
                    <img src={settingIcon} alt='setting' className='w-6 h-6' />
                  )}
                />
              </Link>
            </div>
            <p className='text-md'>{member.introduction}</p>
            <div className='flex flex-wrap mt-2'>
              {displayCategories.map((category, index) => (
                <span
                  key={index}
                  className='mr-2 mb-2 px-2 py-1 border rounded-lg text-gray-700 bg-pink-100'
                >
                  {category === '...' ? '...' : getCategoryName(category)}
                </span>
              ))}
            </div>
          </div>
        </div>
      </div>
      <hr className='my-4' />
      <div className='flex justify-around text-center mb-8'>
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
      <Button
        text='로그아웃'
        color='text-white bg-pink-500 active:bg-pink-600'
        size='small'
        full={false}
        className='absolute up-4 right-4'
        onClick={handleLogout}
      />
      <ProfileModal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        profileImgUrl={member.profileImgUrl || profileImgSample}
        nickname={member.nickName}
        introduction={member.introduction}
      />
    </div>
  );
};

export default MyPage;
