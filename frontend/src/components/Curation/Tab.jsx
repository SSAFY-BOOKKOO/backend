// src/components/Curation/Tab.jsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import letterPlus from '../../assets/icons/letterPlus.png'

const Tab = () => {
  return (

    <div className="flex justify-around p-4 mx-8 border-b-2 border-black">
      <NavLink
        to="/curation/receive"
        className={({ isActive }) =>
          `px-4 py-2 rounded ${isActive ? 'font-bold text-lg' : ''}`
        }
      >
        받은 레터
      </NavLink>
      <NavLink
        to="/curation/send"
        className={({ isActive }) =>
          `px-4 py-2 rounded ${isActive ? 'font-bold text-lg' : ''}`
        }
      >
        보낸 레터
      </NavLink>
      <NavLink
        to="/curation/store"
        className={({ isActive }) =>
          `px-4 py-2 rounded ${isActive ? ' font-bold text-lg' : ''}`
        }
      >
        보관 레터
      </NavLink>
      <NavLink
        to="/curation/letter-create"
        className="px-4 py-2 rounded"
      >
        <img src={letterPlus} alt="Create Letter" className="w-6 h-6" />
      </NavLink>
    </div>

  );
};

export default Tab;


