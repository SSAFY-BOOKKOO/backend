import React, { useState } from 'react';
import Input from '../../@common/Input';
import Textarea from '../../@common/Textarea';
import Button from '../../@common/Button';

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
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20 '>
      <div className='bg-white p-6 rounded-lg shadow-lg w-11/12 max-w-md'>
        <h2 className='text-xl font-bold mb-4'>글귀 입력</h2>
        <form onSubmit={handleSubmit}>
          <Textarea
            placeholder='여기에 문장을 입력해주세요'
            value={quote}
            onChange={e => setQuote(e.target.value)}
            maxLength={200}
          />
          <Input
            type='text'
            placeholder='페이지 및 기타 정보 (예: p.29)'
            value={info}
            onChange={e => setInfo(e.target.value)}
          />
          <div className='flex justify-end mt-3'>
            <Button type='submit' className='mr-3'>
              완료
            </Button>
            <Button
              onClick={() => setShowInput(false)}
              color='bg-gray-100 text-gray-700 hover:bg-gray-200'
            >
              취소
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default QuoteInput;
