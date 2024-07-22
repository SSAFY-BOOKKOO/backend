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
      className={`m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center ${item.color}`}
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

export default Book;
