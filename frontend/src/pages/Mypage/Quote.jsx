import React, { useState } from 'react';
import QuoteList from '@components/MyPage/Quote/QuoteList.jsx';
import QuoteInput from '@components/MyPage/Quote/QuoteInput.jsx';
import QuoteDetailModal from '@components/MyPage/Quote/QuoteDetailModal.jsx';
import quotesData from '@mocks/QuoteData';

const Quote = () => {
  const [quotes, setQuotes] = useState(quotesData);
  const [showInput, setShowInput] = useState(false);
  const [selectedQuoteIndex, setSelectedQuoteIndex] = useState(null);

  const addQuote = quoteObj => {
    setQuotes([...quotes, quoteObj]);
  };

  const handleQuoteClick = index => {
    setSelectedQuoteIndex(index);
  };

  const handleNextQuote = () => {
    setSelectedQuoteIndex(prevIndex =>
      prevIndex === quotes.length - 1 ? 0 : prevIndex + 1
    );
  };

  const handlePrevQuote = () => {
    setSelectedQuoteIndex(prevIndex =>
      prevIndex === 0 ? quotes.length - 1 : prevIndex - 1
    );
  };

  return (
    <div className='min-h-screen p-4 bg-gray-100'>
      <h1 className='text-2xl font-bold mb-4'>문장 목록</h1>
      <QuoteList quotes={quotes} onQuoteClick={handleQuoteClick} />
      <div className='mt-8 flex justify-center'>
        <button
          onClick={() => setShowInput(true)}
          className='bg-green-500 text-white p-2 rounded-lg mr-2'
        >
          입력으로 텍스트 등록
        </button>
      </div>
      {showInput && (
        <QuoteInput addQuote={addQuote} setShowInput={setShowInput} />
      )}
      {selectedQuoteIndex !== null && (
        <QuoteDetailModal
          quoteObj={quotes[selectedQuoteIndex]}
          setSelectedQuote={setSelectedQuoteIndex}
          onNextQuote={handleNextQuote}
          onPrevQuote={handlePrevQuote}
        />
      )}
    </div>
  );
};

export default Quote;
