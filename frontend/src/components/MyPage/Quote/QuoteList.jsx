import React from 'react';
import { BiSolidQuoteAltLeft, BiSolidQuoteAltRight } from 'react-icons/bi';

const QuoteList = ({ quotes, onQuoteClick }) => {
  return (
    <div>
      {quotes.map((quoteObj, index) => (
        <div
          key={index}
          className='bg-pink-50 p-6 rounded-lg shadow mb-4 cursor-pointer border border-pink-200'
          onClick={() => onQuoteClick(index)}
        >
          <div className='flex justify-start'>
            <BiSolidQuoteAltLeft />
          </div>
          <div className='flex flex-col justify-center items-center my-2'>
            <p className='text-lg font-medium text-gray-800'>
              {quoteObj.quote}
            </p>
          </div>
          <div className='flex justify-end'>
            <BiSolidQuoteAltRight />
          </div>
          <p className='mt-4 text-sm text-gray-600'>{quoteObj.info}</p>
        </div>
      ))}
    </div>
  );
};

export default QuoteList;
