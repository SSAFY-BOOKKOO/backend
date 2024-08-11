import React from 'react';

const ChatLoading = () => {
  return (
    <div className='flex items-center space-x-1'>
      <div className='w-2 h-2 bg-green-400 rounded-full animate-bounce'></div>
      <div className='w-2 h-2 bg-pink-500 rounded-full animate-bounce200'></div>
      <div className='w-2 h-2 bg-green-400 rounded-full animate-bounce400'></div>
    </div>
  );
};

export default ChatLoading;
