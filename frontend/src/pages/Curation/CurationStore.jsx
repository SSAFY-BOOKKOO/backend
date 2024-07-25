import React from 'react';
import Tab from '@components/Curation/Tab';
import { useLocation } from 'react-router-dom';

const CurationStore = () => {
  const location = useLocation();
  const { storedLetters } = location.state || { storedLetters: [] };

  return (
    <div className='flex flex-col'>
      <Tab />
      <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
        <h1 className='text-2xl font-bold'>보관함</h1>
        {storedLetters.length > 0 ? (
          storedLetters.map(id => (
            <div key={id} className='p-2 bg-white shadow rounded'>
              <p>Letter ID: {id}</p>
            </div>
          ))
        ) : (
          <p>보관함이 비었습니다.</p>
        )}
      </div>
    </div>
  );
};

export default CurationStore;
