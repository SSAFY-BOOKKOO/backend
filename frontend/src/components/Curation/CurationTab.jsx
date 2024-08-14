// src/components/Curation/Tab.jsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import ChatbotFloatingButton from './ChatbotFloatingButton';
import { LuMailPlus } from 'react-icons/lu';

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
      <NavLink to='/curation/letter-create'>
        <LuMailPlus className='text-2xl ml-8' />
      </NavLink>
      <ChatbotFloatingButton />
    </div>
  );
};

export default Tab;
