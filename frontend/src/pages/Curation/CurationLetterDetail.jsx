import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { CiMenuKebab } from 'react-icons/ci';

const CurationLetterDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { letter } = location.state;
  const [menuOpen, setMenuOpen] = useState(false);

  const handleMenuToggle = () => {
    setMenuOpen(!menuOpen);
  };

  // 레터 보관
  const handleLetterStore = () => {
    // 연동

    setMenuOpen(!menuOpen);
  };

  // 레터 삭제
  const handleLetterDelete = () => {
    // 연동

    setMenuOpen(!menuOpen);
  };

  return (
    <div className='flex flex-col items-center justify-center min-h-[calc(100vh-121px)] p-4 bg-gray-100 scrollbar-none'>
      <div className='relative bg-white rounded-lg shadow-lg w-full max-w-md mx-auto mt-32 scrollbar-none'>
        <div className='absolute -top-28 w-full flex justify-center'>
          <img
            src={letter.image}
            alt={letter.title}
            className='w-48 h-64 rounded-md shadow-lg'
          />
        </div>
        <div className='relative flex flex-col items-center p-6 pt-32'>
          <div className='absolute top-2 right-2'>
            <button className='p-2' onClick={handleMenuToggle}>
              <div className='absolute top-1 right-3 z-10 cursor-pointer text-4xl'>
                &#x22EE;
              </div>
            </button>
            {menuOpen && (
              <div className='absolute right-0 mt-1 w-24 bg-white border rounded-lg shadow-lg'>
                <button
                  className='block px-4 py-2 text-gray-800 hover:bg-gray-200 w-full text-center'
                  onClick={handleLetterStore}
                >
                  레터 보관
                </button>
                <button
                  className='block px-4 py-2 text-gray-800 hover:bg-gray-200 w-full text-center'
                  onClick={handleLetterDelete}
                >
                  레터 삭제
                </button>
              </div>
            )}
          </div>
        </div>
        <div className='px-6 py-8 text-center scrollbar-none'>
          <h2 className='text-xl font-bold mb-2'>{letter.title}</h2>
          <div className='text-gray-600 mb-4 scrollbar-none'>
            {letter.content}
          </div>
        </div>
        <div className='bg-gray-200 px-6 py-3 rounded-b-lg flex justify-between text-sm text-gray-700'>
          <span>{letter.date}</span>
          <span>FROM: {letter.from}</span>
        </div>
      </div>
    </div>
  );
};

export default CurationLetterDetail;
