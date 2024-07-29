import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import useModal from '@hooks/useModal';
import SettingsModal from '@components/@common/SettingsModal';

const CurationLetterDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { letter } = location.state;
  const { isOpen, closeModal, toggleModal } = useModal();

  // 레터 보관
  const handleLetterStore = () => {
    // 연동c
  };

  // 레터 삭제
  const handleLetterDelete = () => {
    // 연동
  };

  const actions = [
    { label: '레터 보관', onClick: handleLetterStore },
    { label: '레터 삭제', onClick: handleLetterDelete },
  ];

  return (
    <div className='flex flex-col items-center justify-start p-4  scrollbar-none'>
      <div className='relative bg-white rounded-lg shadow-lg w-full max-w-md mx-auto mt-32 scrollbar-none'>
        <div className='absolute -top-28 w-full flex justify-center'>
          <img
            src={letter.image}
            alt={letter.title}
            className='w-48 h-64 rounded-md shadow-lg'
          />
        </div>
        {/* 설정 모달 */}
        <div className='relative flex flex-col items-center p-6 pt-32'>
          <SettingsModal
            isOpen={isOpen}
            onClose={closeModal}
            onToggle={toggleModal}
            actions={actions}
          />
        </div>
        <div className='min-h-44 px-6 py-8 text-center scrollbar-none'>
          <h2 className='text-xl font-bold mb-2'>{letter.title}</h2>
          <div className='text-gray-600 mb-4 scrollbar-none'>
            {letter.content}
          </div>
        </div>
        <div className='bg-green-400 px-6 py-3 rounded-b-lg flex justify-between text-sm text-gray-700'>
          <span>{letter.date}</span>
          <span>FROM: {letter.from}</span>
        </div>
      </div>
    </div>
  );
};

export default CurationLetterDetail;
