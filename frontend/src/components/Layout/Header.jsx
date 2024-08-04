import React from 'react';
import IconButton from '@components/@common/IconButton';
import { IoSearchSharp } from 'react-icons/io5';
import { FaBell } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

const Header = ({}) => {
  const navigate = useNavigate();

  // 검색 페이지 이동
  const handleSearchPage = () => {
    navigate('/search');
  };

  // 알림 페이지 이동
  const handleNotificationPage = () => {
    navigate('/notification');
  };

  return (
    <header className='flex items-center justify-between p-4 w-full'>
      <span>북꾸북꾸</span>
      <div>
        <IconButton onClick={handleSearchPage} icon={IoSearchSharp} />
        <IconButton onClick={handleNotificationPage} icon={FaBell} />
      </div>
    </header>
  );
};

export default Header;
