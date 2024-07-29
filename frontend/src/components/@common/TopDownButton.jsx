import React from 'react';
import { FaArrowUp, FaArrowDown } from 'react-icons/fa';
import IconButton from './IconButton';

const TopDownButton = () => {
  const scrollToTop = () => {
    const chatContainer = document.querySelector('.overflow-y-auto');
    if (chatContainer) {
      chatContainer.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  const scrollToBottom = () => {
    const chatContainer = document.querySelector('.overflow-y-auto');
    if (chatContainer) {
      chatContainer.scrollTo({
        top: chatContainer.scrollHeight,
        behavior: 'smooth',
      });
    }
  };
  return (
    <div className='fixed top-1/2 right-4 z-50 flex flex-col items-center space-y-2'>
      <IconButton
        onClick={scrollToTop}
        icon={FaArrowUp}
        className='p-2 bg-white bg-opacity-50 rounded-full shadow-lg'
      />
      <IconButton
        onClick={scrollToBottom}
        icon={FaArrowDown}
        className='p-2 bg-white bg-opacity-50 rounded-full shadow-lg'
      />
    </div>
  );
};

export default TopDownButton;
