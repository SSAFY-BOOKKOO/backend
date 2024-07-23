import React from 'react';
import Book from './Book';
import EmptySlot from './EmptySlot';
import './BookShelf.css';

const BookShelf = ({ books, moveBook, onBookClick }) => {
  const totalSlots = 21; // 3층에 7개의 슬롯
  const allSlots = Array.from({ length: totalSlots }, (_, index) => {
    const book = books.find(book => book.slot_id === index);
    return book ? (
      <Book
        key={book.book_id}
        item={book}
        index={index}
        moveBook={moveBook}
        onBookClick={onBookClick}
      />
    ) : (
      <EmptySlot key={`empty-${index}`} index={index} moveBook={moveBook} />
    );
  });

  const renderShelf = (start, end) => (
    <div className='flex justify-center mb-4'>
      <div className='flex flex-nowrap justify-center w-full p-2 rounded-xl shadow-lg shelf-bg'>
        {allSlots.slice(start, end)}
      </div>
    </div>
  );

  return (
    <div className='p-4 min-h-screen flex flex-col items-center'>
      <div className='shelf-container rounded-xl shadow-lg w-full max-w-full overflow-x-auto'>
        {books.length >= 0 ? (
          <>
            {renderShelf(0, 7)} {/* 1층 */}
            {renderShelf(7, 14)} {/* 2층 */}
            {renderShelf(14, 21)} {/* 3층 */}
          </>
        ) : (
          <p className='text-center text-gray-500'>서재에 책이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default BookShelf;
