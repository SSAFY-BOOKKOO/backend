import React, { useState } from 'react';
import { BiSolidQuoteAltLeft, BiSolidQuoteAltRight } from 'react-icons/bi';
import useModal from '@hooks/useModal';
import SettingsModal from '../../@common/SettingsModal';

const QuoteList = ({ quotes, onQuoteClick, onEditClick, onDeleteClick }) => {
  const [activeIndex, setActiveIndex] = useState(null);
  const { isOpen, toggleModal } = useModal();

  // 글귀 수정
  const handleQuoteUpdate = index => {
    const quote = quotes[index];
    onEditClick(quote); // 수정 input 열기
  };

  // 글귀 삭제
  const handleQuoteDelete = index => {
    const quote = quotes[index];
    onDeleteClick(quote.id); // 글귀 삭제
  };

  const handleSettingsClick = (e, index) => {
    e.stopPropagation();
    setActiveIndex(index);
    toggleModal();
  };

  const actions = index => [
    { label: '글귀 수정', onClick: () => handleQuoteUpdate(index) },
    { label: '글귀 삭제', onClick: () => handleQuoteDelete(index) },
  ];

  return (
    <div>
      {quotes.map((quoteData, index) => (
        <div
          key={index}
          className='bg-pink-50 p-6 rounded-lg shadow mb-4 cursor-pointer border border-pink-200 relative'
          onClick={() => onQuoteClick(index)}
        >
          <SettingsModal
            isOpen={isOpen && activeIndex === index}
            onClose={toggleModal}
            onToggle={e => handleSettingsClick(e, index)}
            actions={actions(index)}
          />
          <div className='flex justify-start'>
            <BiSolidQuoteAltLeft />
          </div>
          <div className='flex flex-col justify-center items-center my-2'>
            <p className='text-lg font-medium text-gray-800'>
              {quoteData.content}
            </p>
          </div>
          <div className='flex justify-end'>
            <BiSolidQuoteAltRight />
          </div>
          <p className='mt-4 text-sm text-gray-600'>{quoteData.source}</p>
        </div>
      ))}
    </div>
  );
};

export default QuoteList;
