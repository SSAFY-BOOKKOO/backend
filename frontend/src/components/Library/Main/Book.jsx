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
    drop: draggedItem => {
      moveBook(draggedItem.originalIndex, index);
    },
  });

  const heightClass = {
    short: 'h-40 mt-8',
    medium: 'h-44 mt-4',
    tall: 'h-48 mt-0',
  }[item.height];

  const thicknessStyle = {
    1: { width: '100px' },
    2: { width: '150px' },
    3: { width: '200px' },
  }[item.thickness];

  return (
    <div
      ref={node => dragRef(dropRef(node))}
      className={`p-1 ${heightClass} text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center ${item.color}`}
      style={{ ...thicknessStyle, opacity: isDragging ? 0.5 : 1 }}
      onClick={() => onBookClick(item)}
    >
      <span className='writing-vertical text-xs sm:text-base'>
        {item.title.length > 10 ? `${item.title.substring(0, 10)}` : item.title}
      </span>
    </div>
  );
};

export default Book;
