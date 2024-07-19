import React from 'react';
import { useDrag, useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const Book = ({ item, index, moveBook, onBookClick }) => {
  const [{ isDragging }, dragRef] = useDrag({
    type: ItemType,
    item: { id: item.book_id, originalIndex: index },
    collect: monitor => ({
      isDragging: monitor.isDragging(),
    }),
  });

  const [, dropRef] = useDrop({
    accept: ItemType,
    hover: draggedItem => {
      if (draggedItem.id !== item.book_id) {
        moveBook(draggedItem.originalIndex, index);
        draggedItem.originalIndex = index;
      }
    },
  });

  return (
    <div
      ref={node => dragRef(dropRef(node))}
      className='bg-green-500 hover:bg-blue-600 text-white m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center'
      style={{ opacity: isDragging ? 0.5 : 1 }}
      onClick={() => onBookClick(item)}
    >
      <span className='writing-vertical text-xs sm:text-base'>
        {item.title.length > 10
          ? `${item.title.substring(0, 10)}...`
          : item.title}
      </span>
    </div>
  );
};

const BookShelf = ({ books, moveBook, onBookClick }) => {
  const renderShelf = start => {
    const shelfBooks = books.slice(start, start + 9);
    const emptySlots = 9 - shelfBooks.length;

    return (
      <div className='flex justify-center mb-4'>
        <div className='flex flex-nowrap justify-center w-full bg-yellow-900 p-2 rounded-xl shadow-lg'>
          {shelfBooks.map((item, idx) => (
            <Book
              key={item.book_id}
              item={item}
              index={start + idx}
              moveBook={moveBook}
              onBookClick={onBookClick} // 책 클릭 핸들러 전달
            />
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
    <div className='p-4 min-h-screen flex flex-col items-center'>
      <div className='p-2 bg-yellow-700 rounded-xl shadow-lg w-full max-w-full overflow-x-auto'>
        {books.length >= 0 ? (
          <>
            {renderShelf(0)} {/* 1층 */}
            {renderShelf(9)} {/* 2층 */}
            {renderShelf(18)} {/* 3층 */}
          </>
        ) : (
          <p className='text-center text-gray-500'>서재에 책이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default BookShelf;
