import React from 'react';
import { NavLink } from 'react-router-dom';
import { FaComments, FaEnvelope, FaUserCircle } from 'react-icons/fa';
import { PiBooksFill } from 'react-icons/pi';

const navItems = [
  { path: '/', component: <PiBooksFill />, label: '나만의 서재' },
  {
    path: '/curation/receive',
    component: <FaEnvelope />,
    label: '큐레이션 레터',
  },
  { path: '/booktalk', component: <FaComments />, label: '북톡' },
  { path: '/mypage', component: <FaUserCircle />, label: '마이페이지' },
];

const BottomTab = () => {
  return (
    <nav className='flex justify-around items-center py-2 bg-white border-t w-full shadow-md'>
      {navItems.map((item, index) => (
        <NavLink
          key={index}
          to={item.path}
          className='flex flex-col items-center cursor-pointer'
        >
          <div className='w-6 h-6 ml-2 text-xl'>{item.component}</div>
          <span className='text-xs'>{item.label}</span>
        </NavLink>
      ))}
    </nav>
  );
};

export default BottomTab;
