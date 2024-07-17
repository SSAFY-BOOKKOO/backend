import React from 'react';

const BookShelf = ({ books, dragStart, dragEnter, drop }) => {
  const renderShelf = start => {
    const shelfBooks = books.slice(start, start + 9); // 현재 층의 책 목록 슬라이싱
    const emptySlots = 9 - shelfBooks.length; // 빈 슬롯 계산

    return (
      <div className='flex justify-center mb-4'>
        <div className='flex flex-nowrap justify-center w-full bg-gray-700 p-2 rounded-xl shadow-lg'>
          {shelfBooks.map((item, idx) => (
            <div
              key={start + idx}
              className='bg-blue-500 hover:bg-blue-600 text-white m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center'
              draggable
              onDragStart={e => dragStart(e, start + idx)}
              onDragEnter={e => dragEnter(e, start + idx)}
              onDragEnd={drop}
              onDragOver={e => e.preventDefault()}
            >
              <span className='writing-vertical'>
                {item.length > 10 ? `${item.substring(0, 10)}...` : item}
              </span>
            </div>
          ))}
          {Array.from({ length: emptySlots }).map((_, idx) => (
            <div
              key={`empty-${start + shelfBooks.length + idx}`}
              className='bg-transparent m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg flex items-center justify-center'
            />
          ))}
        </div>
      </div>
    );
  };

  return (
    <div className='p-4 min-h-screen flex flex-col items-center '>
      <div className='p-2 bg-gray-700 rounded-xl shadow-lg w-full max-w-full overflow-x-auto'>
        {renderShelf(0)} {/* 1층 */}
        {renderShelf(9)} {/* 2층 */}
        {renderShelf(18)} {/* 3층 */}
      </div>
    </div>
  );
};

export default BookShelf;
