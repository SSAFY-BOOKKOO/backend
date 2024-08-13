import React, { useState, useEffect } from 'react';
import Input from '../../@common/Input';
import Textarea from '../../@common/Textarea';
import Button from '../../@common/Button';

const QuoteInput = ({
  addQuote,
  setShowInput,
  initialQuote,
  initialSource,
  isEdit,
  onClose,
  quoteId,
}) => {
  const [content, setContent] = useState(initialQuote || '');
  const [source, setSource] = useState(initialSource || '');

  useEffect(() => {
    setContent(initialQuote || '');
    setSource(initialSource || '');
  }, [initialQuote, initialSource]);

  const handleSubmit = e => {
    e.preventDefault();
    if (isEdit) {
      addQuote({ quoteId, content, source });
    } else {
      addQuote({ content, source });
    }
  };

  const resetForm = () => {
    setContent('');
    setSource('');
    setShowInput(false);
  };

  const handleClose = () => {
    resetForm();
    if (onClose) onClose();
  };

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-white p-6 rounded-lg shadow-lg w-11/12 max-w-md h-3/5 flex flex-col'>
        <h2 className='text-xl font-bold mb-4'>나만의 글귀 입력</h2>
        <form onSubmit={handleSubmit} className='flex flex-col flex-grow'>
          <div className='flex-grow overflow-auto'>
            <Textarea
              placeholder='여기에 문장을 입력해주세요'
              value={content}
              onChange={e => setContent(e.target.value)}
              maxLength={200}
              customClass='h-full mb-2'
            />
            <Input
              type='text'
              placeholder='페이지 및 기타 정보 (예: p.29)'
              value={source}
              onChange={e => setSource(e.target.value)}
            />
          </div>
          <div className='flex justify-end mt-auto pt-3'>
            <Button type='submit' className='mr-3'>
              {isEdit ? '수정' : '등록'}
            </Button>
            <Button
              onClick={handleClose}
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
