import React, { useState } from 'react';

const QuoteInput = ({ addQuote, setShowInput }) => {
  const [quote, setQuote] = useState('');
  const [info, setInfo] = useState('');

  const handleSubmit = e => {
    e.preventDefault();
    if (quote.trim()) {
      addQuote({ quote, info });
      setQuote('');
      setInfo('');
      setShowInput(false);
    }
  };

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-white p-6 rounded-lg shadow-lg w-full max-w-md'>
        <h2 className='text-xl font-bold mb-4'>문장 입력</h2>
        <form onSubmit={handleSubmit}>
          <textarea
            placeholder='여기에 문장을 입력해주세요'
            value={quote}
            onChange={e => setQuote(e.target.value)}
            className='border p-2 mb-4 w-full h-32'
          />
          <input
            type='text'
            placeholder='페이지 및 기타 정보 (예: p.29)'
            value={info}
            onChange={e => setInfo(e.target.value)}
            className='border p-2 mb-4 w-full'
          />
          <div className='flex justify-end'>
            <button
              type='submit'
              className='bg-green-500 text-white p-2 rounded-lg mr-2'
            >
              완료
            </button>
            <button
              onClick={() => setShowInput(false)}
              className='bg-gray-500 text-white p-2 rounded-lg'
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default QuoteInput;
