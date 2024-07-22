import React from 'react';
import { useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const EmptySlot = ({ index, moveBook }) => {
  const [, dropRef] = useDrop({
    accept: ItemType,
    drop: draggedItem => {
      moveBook(draggedItem.originalIndex, index);
      draggedItem.originalIndex = index; // Update the dragged item's originalIndex
    },
  });

  return (
    <div
      ref={dropRef}
      className='bg-transparent m-1 p-1 w-20 h-48 sm:w-24 sm:h-64 text-center rounded-lg flex items-center justify-center border-dashed border-2 border-gray-300'
    />
  );
};

export default EmptySlot;
