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

  // 높이 값에 따른 클래스 추가
  const heightClass = {
    short: 'h-56 mt-8',
    medium: 'h-60 mt-4',
    tall: 'h-64 mt-0',
  }[item.height];

  // 두께 값에 따른 인라인 스타일 추가
  const thicknessStyle = {
    1: { width: '100px' },
    2: { width: '150px' },
    3: { width: '200px' },
  }[item.thickness];

  return (
    <div
      ref={node => dragRef(dropRef(node))}
      className={`m-1 p-1 ${heightClass} text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center ${item.color}`}
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
