import React from 'react';
import backgroundImage from '@assets/images/quote_background.png'; // 배경 이미지를 가져옵니다.
import IconButton from '@components/@common/IconButton';
import { FaAngleLeft, FaAngleRight } from 'react-icons/fa';
import { IoCloseSharp } from 'react-icons/io5';
import { BiSolidQuoteAltLeft } from 'react-icons/bi';

const QuoteDetailModal = ({
  quoteObj,
  setSelectedQuote,
  onNextQuote,
  onPrevQuote,
}) => {
  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-white rounded-lg shadow-lg w-full max-w-3xl relative mx-2'>
        <img
          src={backgroundImage}
          alt='Background'
          className='w-full h-full object-cover rounded-lg'
        />
        <div className='absolute inset-0 flex flex-col justify-center text-white p-8'>
          <BiSolidQuoteAltLeft className='mb-1' />
          <p className='text-lg font-semibold mb-4'>{quoteObj.quote}</p>
          <p className='text-sm'>{quoteObj.info}</p>
        </div>
        <div className='absolute top-0 right-0 mt-4 mr-4'>
          <IconButton
            onClick={() => setSelectedQuote(null)}
            icon={IoCloseSharp}
            className='bg-transparent text-white p-2 rounded-full text-2xl'
          />
        </div>
        <div className='absolute bottom-0 right-0 mb-4 mr-4'>
          <IconButton
            onClick={onNextQuote}
            icon={FaAngleRight}
            className='bg-transparent text-white p-2 rounded-full text-2xl'
          />
        </div>
        <div className='absolute bottom-0 left-0 mb-4 ml-4'>
          <IconButton
            onClick={onPrevQuote}
            icon={FaAngleLeft}
            className='bg-transparent text-white p-2 rounded-full text-2xl'
          />
        </div>
      </div>
    </div>
  );
};

export default QuoteDetailModal;