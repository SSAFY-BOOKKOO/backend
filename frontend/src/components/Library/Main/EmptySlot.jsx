import React from 'react';
import { useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const EmptySlot = ({ index, moveBook }) => {
  const [, dropRef] = useDrop({
    accept: ItemType,
    drop: draggedItem => {
      moveBook(draggedItem.originalIndex, index);
    },
  });

  return (
    <div
      ref={dropRef}
      className='m-1 p-1 h-48 sm:h-64 text-center rounded-lg flex items-center justify-center'
      style={{ width: '150px' }} // 기본 너비를 설정합니다.
    />
  );
};

export default EmptySlot;
