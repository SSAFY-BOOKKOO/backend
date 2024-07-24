import React from 'react';

const QuoteList = ({ quotes, onQuoteClick }) => {
  return (
    <div>
      {quotes.map((quoteObj, index) => (
        <div
          key={index}
          className='bg-white p-4 rounded-lg shadow mb-4 cursor-pointer'
          onClick={() => onQuoteClick(index)}
        >
          <p>{quoteObj.quote}</p>
          <p className='text-sm text-gray-500'>{quoteObj.info}</p>
        </div>
      ))}
    </div>
  );
};

export default QuoteList;
