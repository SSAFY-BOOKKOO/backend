import React from 'react';
import IconButton from '@components/@common/IconButton';
import { IoSearchSharp } from 'react-icons/io5';
import { FaBell } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import bookkooMainIcon from '@assets/icons/bookkoo_full_icon.png';

const Header = () => {
  const navigate = useNavigate();

  // 검색 페이지 이동
  const handleSearchPageMove = () => {
    navigate('/search');
  };

  // 알림 페이지 이동
  const handleNotificationPageMove = () => {
    navigate('/notification');
  };

  // 메인 페이지 이동
  const handleMainPageMove = () => {
    navigate('/');
  };

  return (
    <header className='flex items-center justify-between p-3 w-full'>
      <span className='w-12' onClick={handleMainPageMove}>
        <img src={bookkooMainIcon} />
      </span>
      <div>
        <IconButton onClick={handleSearchPageMove} icon={IoSearchSharp} />
        <IconButton onClick={handleNotificationPageMove} icon={FaBell} />
      </div>
    </header>
  );
};

export default Header;
