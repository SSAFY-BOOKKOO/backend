import React, { useState } from 'react';
import QuoteList from '@components/MyPage/Quote/QuoteList.jsx';
import QuoteInput from '@components/MyPage/Quote/QuoteInput.jsx';
import QuoteDetailModal from '@components/MyPage/Quote/QuoteDetailModal.jsx';
import QuoteCreateModal from '@components/MyPage/Quote/QuoteCreateModal.jsx';
import quotesData from '@mocks/QuoteData';
import useModal from '@hooks/useModal';
import Button from '@components/@common/Button';
import WrapContainer from '@components/Layout/WrapContainer';

const Quote = () => {
  const [quotes, setQuotes] = useState(quotesData);
  const [showInput, setShowInput] = useState(false);
  const [selectedQuoteIndex, setSelectedQuoteIndex] = useState(null);
  const { isOpen, toggleModal } = useModal();

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
    <WrapContainer>
      <div className='flex justify-between items-center mb-4'>
        <h1 className='text-2xl font-bold'>나의 글귀함</h1>
        <div className='flex items-center'>
          <Button onClick={toggleModal}>글귀 만들기</Button>
        </div>
      </div>
      <p className='mb-3'>총 {quotes?.length}개</p>
      <QuoteList quotes={quotes} onQuoteClick={handleQuoteClick} />
      {isOpen && (
        <QuoteCreateModal
          toggleModal={toggleModal}
          setShowInput={setShowInput}
        />
      )}
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
    </WrapContainer>
  );
};

export default Quote;
