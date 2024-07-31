import React from 'react';

const PopularBook = ({ book, onClick }) => {
  return (
    <div
      className='text-center w-32 flex-shrink-0 cursor-pointer'
      onClick={onClick}
    >
      <img
        src={book?.cover_img_url}
        alt='Book'
        className='w-32 h-40 mb-2 rounded-lg'
      />
      <h3 className='text-black text-sm text-overflow'>{book?.title}</h3>
      <p className='text-gray-500 text-sm text-overflow'>{book?.author}</p>
    </div>
  );
};

export default PopularBook;
