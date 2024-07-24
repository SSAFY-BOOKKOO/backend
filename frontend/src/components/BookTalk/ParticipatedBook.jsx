import React from 'react';

const ParticipatedBook = ({ book, onClick }) => {
  return (
    <div className='flex items-center mb-4 cursor-pointer' onClick={onClick}>
      <img
        src={book.cover_img_url}
        alt='Book'
        className='w-12 h-16 mr-4 rounded-lg'
      />
      <div>
        <h3 className='text-black text-sm'>{book.title}</h3>
        <p className='text-gray-500 text-sm'>{book.author}</p>
        <div className='flex space-x-2 mt-1'>
          {book?.tags?.map((tag, index) => (
            <span
              key={index}
              className='bg-gray-200 text-gray-600 text-xs px-2 py-1 rounded'
            >
              {tag}
            </span>
          ))}
        </div>
      </div>
      <div className='ml-auto text-right'>
        <p className='text-gray-500 text-xs'>{book.time}</p>
        <p className='text-gray-500 text-xs'>댓글 수 {book.comments}</p>
      </div>
    </div>
  );
};

export default ParticipatedBook;
