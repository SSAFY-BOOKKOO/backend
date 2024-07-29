import React from 'react';

const ChatBubble = ({
  message,
  role,
  time,
  showProfile = false,
  showLikes = false,
  likes = 0,
  profileImage,
  customStyle = {},
}) => {
  const handleLike = () => {
    // 좋아요 api 연동
  };

  return (
    <div
      className={`flex ${role === 'user' ? 'justify-end' : 'justify-start'} mb-4`}
    >
      {showProfile && role !== 'user' && (
        <div className='mr-2'>
          <img
            src={profileImage}
            alt='Profile'
            className='w-8 h-8 rounded-full'
          />
        </div>
      )}
      <div className={`max-w-[80%] ${role === 'user' ? 'order-1' : 'order-2'}`}>
        <div className='relative'>
          <div
            className={`absolute ${role === 'user' ? 'right-0' : 'left-0'} top-5 transform ${role === 'user' ? 'translate-x-1/2' : '-translate-x-1/2'} -translate-y-1/2`}
          >
            <div
              className={`w-4 h-4 ${role === 'user' ? 'bg-green-400' : 'bg-gray-200'} rotate-45 transform origin-center ${role === 'user' ? 'mr-2' : 'ml-2'} rounded-sm`}
              style={customStyle.arrow}
            ></div>
          </div>
          <div
            className={`
              ${role === 'user' ? 'bg-green-400 text-white' : 'bg-gray-200 text-gray-800'} 
              p-3 px-5 rounded-2xl break-words overflow-hidden mt-2
            `}
            style={customStyle.bubble}
          >
            <p className='text-sm whitespace-pre-wrap leading-6'>{message}</p>
          </div>
          {showLikes && (
            <div className='text-right' onClick={handleLike}>
              <span className='text-xs text-gray-500'>❤️ {likes}</span>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ChatBubble;
