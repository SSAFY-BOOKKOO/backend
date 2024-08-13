import React, { useRef, useState } from 'react';
import backgroundImage from '@assets/images/quote_background.png';
import IconButton from '@components/@common/IconButton';
import { FaAngleLeft, FaAngleRight } from 'react-icons/fa';
import { IoCloseSharp } from 'react-icons/io5';
import { BiSolidQuoteAltLeft } from 'react-icons/bi';
import CaptureButton from '../../Library/Main/CaptureButton';

const QuoteDetailModal = ({
  guoteData,
  setSelectedQuote,
  onNextQuote,
  onPrevQuote,
}) => {
  const ref = useRef(null);
  const [isCapturing, setIsCapturing] = useState(false);

  const handleCapture = capturing => {
    setIsCapturing(capturing);
  };

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div
        ref={ref}
        className='bg-white rounded-lg shadow-lg w-11/12 relative mx-2 h-3/5'
      >
        <img
          src={backgroundImage}
          alt='Background'
          className='w-full h-full object-cover object-top rounded-lg'
        />
        <div className='absolute inset-0 flex flex-col justify-center text-black p-8'>
          <BiSolidQuoteAltLeft className='mb-1' />
          <p className='text-lg font-semibold mb-4'>{guoteData?.content}</p>
          <p className='text-sm'>{guoteData?.source}</p>
        </div>
        {!isCapturing && (
          <div>
            <div className='absolute top-0 right-0 mt-4 mr-4 z-10'>
              <IconButton
                onClick={() => setSelectedQuote(null)}
                icon={IoCloseSharp}
                className='bg-transparent text-black p-2 rounded-full text-2xl'
              />
            </div>
            <div className='absolute bottom-0 right-0 mb-4 mr-4 z-10'>
              <IconButton
                onClick={onNextQuote}
                icon={FaAngleRight}
                className='bg-transparent text-black p-2 rounded-full text-2xl'
              />
            </div>
            <div className='absolute inset-0 flex justify-center items-end mb-4'>
              <CaptureButton
                targetRef={ref}
                filename='quote'
                onCapture={handleCapture}
              />
            </div>
            <div className='absolute bottom-0 left-0 mb-4 ml-4 z-10'>
              <IconButton
                onClick={onPrevQuote}
                icon={FaAngleLeft}
                className='bg-transparent text-black p-2 rounded-full text-2xl'
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default QuoteDetailModal;
