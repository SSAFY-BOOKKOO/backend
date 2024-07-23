import React from 'react';
import backgroundImage from '@assets/quote_background.png'; // 배경 이미지를 가져옵니다.

const QuoteDetailModal = ({
  quoteObj,
  setSelectedQuote,
  onNextQuote,
  onPrevQuote,
}) => {
  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-white rounded-lg shadow-lg w-full max-w-3xl relative'>
        <img
          src={backgroundImage}
          alt='Background'
          className='w-full h-full object-cover rounded-lg'
        />
        <div className='absolute inset-0 flex flex-col justify-center text-white p-8'>
          <p>"</p>
          <p className='text-lg font-semibold mb-4'>{quoteObj.quote}</p>
          <p className='text-sm'>{quoteObj.info}</p>
        </div>
        <div className='absolute top-0 right-0 mt-4 mr-4'>
          <button
            onClick={() => setSelectedQuote(null)}
            className='bg-transparent text-white p-2 rounded-full text-2xl'
          >
            &times;
          </button>
        </div>
        <div className='absolute bottom-0 right-0 mb-4 mr-4'>
          <button
            onClick={onNextQuote}
            className='bg-transparent text-white p-2 rounded-full text-2xl'
          >
            &gt;
          </button>
        </div>
        <div className='absolute bottom-0 left-0 mb-4 ml-4'>
          <button
            onClick={onPrevQuote}
            className='bg-transparent text-white p-2 rounded-full text-2xl'
          >
            &lt;
          </button>
        </div>
      </div>
    </div>
  );
};

export default QuoteDetailModal;
