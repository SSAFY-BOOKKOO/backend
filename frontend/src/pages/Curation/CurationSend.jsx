import React from 'react';
import CurationTab from '@components/Curation/CurationTab';
import { useLocation } from 'react-router-dom';

const CurationSend = () => {
  const location = useLocation();
  const { sendLetters } = location.state || { sendLetters: [] };

  return (
    <div className='flex flex-col'>
      <CurationTab />
      <div className='flex flex-col items-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
        {sendLetters.length > 0 ? (
          sendLetters.map(id => (
            <div key={id} className='p-2 bg-white shadow rounded'>
              <p>Letter ID: {id}</p>
            </div>
          ))
        ) : (
          <p className='text-center'>
            보낸 레터가 없습니다. <br /> 지금 바로 작성해 보세요!
          </p>
        )}
      </div>
    </div>
  );
};

export default CurationSend;
