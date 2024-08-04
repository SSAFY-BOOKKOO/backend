import React from 'react';

const SearchLibraryItem = ({ book, onClick }) => {
  return (
    <div
      className='flex items-start space-x-4 p-3 mb-2 bg-white cursor-pointer'
      onClick={onClick}
    >
      <div className='w-36 h-36 flex '>
        <img
          className='object-contain rounded-lg'
          src={book.coverImgUrl}
          alt={book.title}
        />
      </div>

      <div className='flex flex-col justify-between h-36 w-full'>
        <div className='flex flex-col space-y-1 overflow-hidden'>
          <p
            className='text-lg font-semibold text-overflow-2'
            dangerouslySetInnerHTML={{ __html: book?.title }}
          ></p>
          <p className='text-sm text-gray-600'>{book.author}</p>
          <p className='text-sm text-gray-600'>
            {book.publisher} | {book?.publishedAt}
          </p>
        </div>
      </div>
    </div>
  );
};

export default SearchLibraryItem;
