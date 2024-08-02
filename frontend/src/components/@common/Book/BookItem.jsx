import React, { useEffect } from 'react';
import Button from '../../@common/Button';

const BookItem = ({ book, onClick }) => {
  const titleMaxLength = 10;
  const authorMaxLength = 24;

  const displayTitle =
    book.title.length > titleMaxLength
      ? book.title.substring(0, titleMaxLength - 1) + '...'
      : book.title;

  const displayAuthor =
    book.author.length > authorMaxLength
      ? book.author.substring(0, authorMaxLength - 1) + '...'
      : book.author;

  useEffect(() => {
    console.log('Book object:', book);
  }, [book]);
  return (
    <div
      className='flex  items-center w-10/12 cursor-pointer'
      onClick={onClick}
    >
      <img
        src={book.coverImgUrl}
        alt='Book'
        className='w-24 h-32 mb-6 rounded-lg'
      />
      <div className='flex flex-col ml-4'>
        {/* <div className='flex flex-grow'> */}
        <p className='text-black text-md text-overflow'>{displayTitle}</p>
        <p className='text-black text-xs '>{book.publishedAt}</p>
        {/* </div> */}
        <p className='text-gray-500 text-sm text-overflow'>{displayAuthor}</p>
        <button className='bg-pink-500 rounded-lg w-[3rem] text-sm mt-4'>
          등록
        </button>
      </div>
    </div>
  );
};

export default BookItem;
