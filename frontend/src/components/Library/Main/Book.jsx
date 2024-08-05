import React from 'react';
import { useDrag, useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const Book = ({ item, index, moveBook, onBookClick }) => {
  const [{ isDragging }, dragRef] = useDrag({
    type: ItemType,
    item: { id: item.book.id, originalIndex: index },
    collect: monitor => ({
      isDragging: monitor.isDragging(),
    }),
  });

  const [{ isOver, canDrop }, dropRef] = useDrop({
    accept: ItemType,
    drop: draggedItem => {
      moveBook(draggedItem.originalIndex, index);
    },
    collect: monitor => ({
      isOver: monitor.isOver(),
      canDrop: monitor.canDrop(),
    }),
  });

  const getTitle = (title, length) => {
    return title.length > length ? `${title.substring(0, length)}` : title;
  };

  const heightClass = {
    short: 'h-40 mt-10',
    medium: 'h-44 mt-6',
    tall: 'h-48 mt-2',
  }[
    item.book.sizeHeight <= 220
      ? 'short'
      : item.book.sizeHeight <= 240
        ? 'medium'
        : 'tall'
  ];

  const titleLength = {
    short: 8,
    medium: 9,
    tall: 10,
  }[
    item.book.sizeHeight <= 210
      ? 'short'
      : item.book.sizeHeight <= 220
        ? 'medium'
        : 'tall'
  ];

  const thicknessStyle = {
    thin: { width: '100px' },
    normal: { width: '150px' },
    thick: { width: '200px' },
  }[
    item.book.itemPage <= 300
      ? 'thin'
      : item.book.itemPage <= 400
        ? 'normal'
        : 'thick'
  ];

  return (
    <div
      ref={node => dragRef(dropRef(node))}
      className={`${heightClass} text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center ${item.bookColor} ${
        isOver && canDrop ? 'border-4 border-dashed border-gray-500' : ''
      }`}
      style={{ ...thicknessStyle, opacity: isDragging ? 0.5 : 1 }}
      onClick={() => onBookClick(item)}
    >
      <span
        className='writing-vertical text-xs sm:text-base'
        style={{ letterSpacing: '-3px' }}
      >
        {getTitle(item.book.title, titleLength)}
      </span>
    </div>
  );
};

export default Book;
