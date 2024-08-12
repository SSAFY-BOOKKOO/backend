import React from 'react';
import { formatRelativeTime } from '@utils/formatTime';

const BookTalkItem = ({ book, onClick }) => {
  return (
    <div className='flex items-center mb-4 cursor-pointer' onClick={onClick}>
      <img
        src={book?.coverImgUrl}
        alt='Book'
        className='w-12 h-16 mr-3 rounded-lg'
      />
      <div className='flex-grow w-1/12 mr-2'>
        <h3 className='text-black text-sm text-overflow-2'>{book?.title}</h3>
        <p className='text-gray-500 text-sm text-overflow-1'>{book?.author}</p>
      </div>
      <div className='ml-auto text-right'>
        <p className='text-gray-500 text-xs'>
          {formatRelativeTime(book?.lastChatTime)}
        </p>
        <p className='text-gray-500 text-xs'>✉ {book?.chats || 0}</p>
      </div>
    </div>
  );
};

export default BookTalkItem;
