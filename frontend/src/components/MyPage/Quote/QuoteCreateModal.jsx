import React, { useRef, useEffect } from 'react';

const QuoteCreateModal = ({ toggleModal, setShowInput }) => {
  const modalRef = useRef();

  const handleClickOutside = event => {
    if (modalRef.current && !modalRef.current.contains(event.target)) {
      toggleModal();
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div className='fixed inset-0 bg-black bg-opacity-50 z-50 flex items-end justify-center'>
      <div
        ref={modalRef}
        className='bg-white w-full max-w-md rounded-t-3xl p-6 shadow-lg'
      >
        <button
          onClick={toggleModal}
          className='w-full bg-pink-500 text-white text-center text= py-3 px-4 mb-4 rounded-lg'
        >
          이미지로 텍스트 등록
        </button>
        <button
          onClick={() => {
            setShowInput(true);
            toggleModal();
          }}
          className='w-full bg-green-400 text-white text-center text= py-3 px-4 mb-2 rounded-lg'
        >
          입력으로 텍스트 등록
        </button>
      </div>
    </div>
  );
};

export default QuoteCreateModal;
