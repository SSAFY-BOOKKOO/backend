import React from 'react';
import { useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const EmptySlot = ({ index, moveBook }) => {
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

  return (
    <div
      ref={dropRef}
      className={`m-1 p-1 h-48 text-center rounded-lg flex items-center justify-center ${
        isOver && canDrop ? 'border-4 border-dashed border-gray-500' : ''
      }`}
      style={{ width: '150px' }}
    />
  );
};

export default EmptySlot;
