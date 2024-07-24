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
      className='m-1 p-1 h-48 text-center rounded-lg flex items-center justify-center'
      style={{ width: '150px' }}
    />
  );
};

export default EmptySlot;
