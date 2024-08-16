import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import Book from './Book';
import EmptySlot from './EmptySlot';
import { showAlertAtom } from '@atoms/alertAtom';

const getSecondaryColorClass = primaryColorClass => {
  const colorMap = {
    200: '400',
    400: '200',
    600: '200',
  };

  const primaryClassSuffix = primaryColorClass.match(/-(\d{3})$/)?.[1];
  const secondaryClassSuffix = colorMap[primaryClassSuffix] || '600';

  return primaryColorClass.replace(primaryClassSuffix, secondaryClassSuffix);
};

const BookShelf = ({
  books,
  moveBook,
  onBookClick,
  viewOnly,
  libraryStyleDto,
}) => {
  const navigate = useNavigate();
  const [, showAlert] = useAtom(showAlertAtom);
  const totalSlots = 21; // 3층에 7개의 슬롯


  const handleShelfClick = () => {
    if (!viewOnly && books.length === 0) {
      navigate('/search');
    }
  };

  let primaryColorClass = libraryStyleDto?.libraryColor;

  if (!primaryColorClass || primaryColorClass === '#FFFFFF') {
    primaryColorClass = 'bg-[#a27045]';
  }

  const secondaryColorClass =
    primaryColorClass === 'bg-[#a27045]'
      ? 'bg-[#d2a679]'
      : getSecondaryColorClass(primaryColorClass);

  const allSlots = Array.from({ length: totalSlots }, (_, index) => {
    const book = books.find(book => book.bookOrder === index + 1);
    return book && book.book ? (
      <Book
        key={book.book.id}
        item={book}
        index={index}
        moveBook={moveBook}
        onBookClick={onBookClick}
        viewOnly={viewOnly}
        libraryStyleDto={libraryStyleDto}
      />
    ) : (
      <EmptySlot
        key={`empty-${index}`}
        index={index}
        moveBook={moveBook}
        viewOnly={viewOnly}
      />
    );
  });

  const renderShelf = (start, end, colorClass) => (
    <div className='flex justify-center mb-2'>
      <div
        className={`flex flex-nowrap px-1 justify-center w-full rounded-xl shadow-lg ${colorClass}`}
      >
        {allSlots.slice(start, end)}
      </div>
    </div>
  );

  return (
    <div className='p-4 flex flex-col items-center'>
      <div
        className={`rounded-xl shadow-lg w-full max-w-full overflow-x-auto p-2 ${secondaryColorClass}`}
        onClick={handleShelfClick}
      >
        {renderShelf(0, 7, primaryColorClass)} {/* 1층 */}
        {renderShelf(7, 14, primaryColorClass)} {/* 2층 */}
        {renderShelf(14, 21, primaryColorClass)} {/* 3층 */}
      </div>
    </div>
  );
};

export default BookShelf;
