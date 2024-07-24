// src/components/Curation/Tab.jsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import letterPlus from '../../assets/icons/letterPlus.png';
import ChatbotFloatingButton from './ChatbotFloatingButton';

const Tab = () => {
  return (
    <div className='flex justify-between items-center p-2 border-b-2 border-black'>
      <div className='flex space-x-4 text-md'>
        <NavLink
          to='/curation/receive'
          className={({ isActive }) =>
            `px-2 py-2 ml-4 ${isActive ? 'font-bold text-black' : 'text-gray-500'}`
          }
        >
          받은 레터
        </NavLink>
        <NavLink
          to='/curation/send'
          className={({ isActive }) =>
            `px-1 py-2 ${isActive ? 'font-bold text-black' : 'text-gray-500'}`
          }
        >
          보낸 레터
        </NavLink>
        <NavLink
          to='/curation/store'
          className={({ isActive }) =>
            `px-2 py-2 ${isActive ? 'font-bold text-black' : 'text-gray-500'}`
          }
        >
          보관 레터
        </NavLink>
      </div>
      <NavLink to='/curation/letter-create' className='px-2 py-2 rounded'>
        <img src={letterPlus} alt='Create Letter' className='w-6 h-6' />
      </NavLink>
      <ChatbotFloatingButton />
    </div>
  );
};

export default Tab;
