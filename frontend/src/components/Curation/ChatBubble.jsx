import React from 'react';

const ChatBubble = ({ message, role, time }) => {
  return (
    <div
      className={`flex ${role === 'user' ? 'justify-end' : 'justify-start'} mb-4`}
    >
      <div className={`max-w-[60%] ${role === 'user' ? 'order-1' : 'order-2'}`}>
        <div className='relative'>
          <div
            className={`absolute ${role === 'user' ? 'right-0' : 'left-0'} top-5 transform ${role === 'user' ? 'translate-x-1/2' : '-translate-x-1/2'} -translate-y-1/2`}
          >
            <div
              className={`w-4 h-4 ${role === 'user' ? 'bg-blue-500' : 'bg-gray-200'} rotate-45 transform origin-center ${role === 'user' ? 'mr-2' : 'ml-2'} rounded-sm`}
            ></div>
          </div>
          <div
            className={`
              ${
                role === 'user'
                  ? 'bg-blue-500 text-white'
                  : 'bg-gray-200 text-gray-800'
              } 
              p-3 px-5 rounded-2xl
              break-words overflow-hidden
              mt-2 
            `}
          >
            <p className='text-sm whitespace-pre-wrap'>{message}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatBubble;
