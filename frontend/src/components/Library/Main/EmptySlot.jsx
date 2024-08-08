import React from 'react';
import { useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const EmptySlot = ({ index, moveBook, viewOnly }) => {
  const [{ isOver, canDrop }, dropRef] = useDrop({
    accept: ItemType,
    drop: draggedItem => {
      if (moveBook) {
        moveBook(draggedItem.originalIndex, index);
      }
    },
    collect: monitor => ({
      isOver: monitor.isOver(),
      canDrop: monitor.canDrop(),
    }),
    canDrop: () => !viewOnly,
  });

  return (
    <div
      ref={viewOnly ? null : dropRef}
      className={`m-1 p-1 h-48 text-center rounded-lg flex items-center justify-center ${
        isOver && canDrop ? 'border-4 border-dashed border-gray-500' : ''
      }`}
      style={{ width: '150px' }}
    />
  );
};

export default EmptySlot;
