import React from 'react';
import { useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const EmptySlot = ({ index, moveBook }) => {
  const [, dropRef] = useDrop({
    accept: ItemType,
    drop: draggedItem => {
      moveBook(draggedItem.originalIndex, index);
      draggedItem.originalIndex = index;
    },
  });

  return (
    <div
      ref={dropRef}
      className='m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg flex items-center justify-center'
    />
  );
};

export default EmptySlot;
