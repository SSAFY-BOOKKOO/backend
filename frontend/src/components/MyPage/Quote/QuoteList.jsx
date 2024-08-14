import React, { useState } from 'react';
import { BiSolidQuoteAltLeft, BiSolidQuoteAltRight } from 'react-icons/bi';
import useModal from '@hooks/useModal';
import SettingsModal from '@components/@common/SettingsModal';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';

const QuoteList = ({ quotes, onQuoteClick, onEditClick, onDeleteClick }) => {
  const [activeIndex, setActiveIndex] = useState(null);
  const { isOpen, toggleModal } = useModal();

  const [, showAlert] = useAtom(showAlertAtom);

  const handleQuoteUpdate = index => {
    const quote = quotes[index];
    onEditClick(quote);
  };

  const handleQuoteDelete = index => {
    const quote = quotes[index];

    showAlert(
      '정말 글귀를 삭제하시겠습니까?',
      false,
      () => {
        onDeleteClick(quote.quoteId);
      },
      () => {}
    );
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
      <Alert />
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
