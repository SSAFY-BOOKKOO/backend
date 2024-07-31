import React from 'react';

const BookItem = ({ book, onClick }) => {
  return (
    <div
      className='text-center flex flex-col items-center w-28 cursor-pointer mr-4'
      onClick={onClick}
    >
      <img
        src={book?.cover_img_url}
        alt='Book'
        className='w-24 h-32 mb-2 rounded-lg'
      />
      <h3 className='text-black text-sm text-overflow'>{book?.title}</h3>
      <p className='text-gray-500 text-sm text-overflow'>{book?.author}</p>
    </div>
  );
};

export default BookItem;
