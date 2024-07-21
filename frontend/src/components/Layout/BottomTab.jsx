import React from 'react';
import { NavLink } from 'react-router-dom';

import community from '@assets/icons/community.png';
import curation from '@assets/icons/curation.png';
import library from '@assets/icons/library.png';
import myPage from '@assets/icons/mypage.png';

const navItems = [
  { path: '/library', icon: library, label: '나만의 서재' },
  { path: '/curation/receive', icon: curation, label: '큐레이션 레터' },
  { path: '/booktalk', icon: community, label: '북톡' },
  { path: '/mypage', icon: myPage, label: '마이페이지' },
];

const BottomTab = () => {
  return (
    <div className='flex justify-around items-center py-2 bg-white border-t fixed bottom-0 w-full max-w-[480px]'>
      {' '}
      {navItems.map((item, index) => (
        <NavLink
          key={index}
          to={item.path}
          className='flex flex-col items-center cursor-pointer'
          activeClassName='text-blue-500'
        >
          <img src={item.icon} alt={item.label} className='w-6 h-6 mb-2' />
          <span className='text-xs'>{item.label}</span>
        </NavLink>
      ))}
    </div>
  );
};

export default BottomTab;
