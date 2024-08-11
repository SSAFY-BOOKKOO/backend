import React from 'react';
import { formatChatTime } from '@utils/formatTime';

const ChatBubble = ({
  message,
  role,
  time,
  nickName,
  showProfile = false,
  showLikes = false,
  likes = 0,
  isMemberLiked = false,
  profileImage,
  customStyle = {},
  profileClick,
  onLikeClick,
}) => {
  const handleLike = e => {
    e.stopPropagation();
    if (onLikeClick) {
      onLikeClick();
    }
  };

  const isUser = role === 'user';

  return (
    <div className={`flex ${isUser ? 'justify-end' : 'justify-start'} mb-4`}>
      {showProfile && !isUser && (
        <div
          className='flex-shrink-0 w-12 h-12 mr-3 flex items-center justify-center cursor-pointer'
          onClick={profileClick}
        >
          <img
            src={profileImage}
            alt='Profile'
            className='max-w-full max-h-full object-contain rounded-full'
          />
        </div>
      )}
      <div
        className={`max-w-[80%] ${isUser ? 'items-end' : 'items-start'} flex flex-col`}
      >
        {!isUser && (
          <div className='text-sm text-gray-600 mb-1'>{nickName}</div>
        )}
        <div
          className={`flex items-end ${isUser ? 'flex-row-reverse' : 'flex-row'}`}
        >
          <div className='relative'>
            <div
              className={`
                absolute top-[13px] ${isUser ? '-right-1' : '-left-1'}
                w-4 h-4 ${isUser ? 'bg-green-400' : 'bg-gray-200'} 
                transform rotate-45
              `}
            />
            <div
              className={`
                ${isUser ? 'bg-green-400 text-white' : 'bg-gray-200 text-gray-800'} 
                p-3 px-5 rounded-2xl break-words overflow-hidden relative
              `}
              style={customStyle.bubble}
            >
              <p className='text-sm whitespace-pre-wrap leading-6'>{message}</p>
            </div>
          </div>
          <span
            className={`text-xs flex-shrink-0 text-gray-500 ${isUser ? 'mr-1' : 'ml-1'} mb-1`}
          >
            {formatChatTime(time)}
          </span>
        </div>
        {showLikes && (
          <div
            className={`flex items-center cursor-pointer mt-1 ${isUser ? 'justify-end' : 'justify-start'}`}
            onClick={handleLike}
          >
            <span
              className={`text-xs ${isMemberLiked ? 'text-red-500' : 'text-gray-500'}`}
            >
              {isMemberLiked ? '❤️' : '🤍'} {likes}
            </span>
          </div>
        )}
      </div>
    </div>
  );
};

export default ChatBubble;
