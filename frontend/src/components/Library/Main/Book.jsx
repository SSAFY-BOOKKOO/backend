import React from 'react';
import { useDrag, useDrop } from 'react-dnd';

const ItemType = 'BOOK';

const Book = ({
  item,
  index,
  moveBook,
  onBookClick,
  viewOnly,
  libraryStyleDto,
}) => {
  const [{ isDragging }, dragRef] = useDrag({
    type: ItemType,
    item: { bookOrder: item.bookOrder, originalIndex: index },
    collect: monitor => ({
      isDragging: monitor.isDragging(),
    }),
    canDrag: !viewOnly,
  });

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

  const fontSizeClass =
    {
      0: 'text-xs',
      1: 'text-sm',
      2: 'text-base',
      3: 'text-lg',
    }[libraryStyleDto?.fontSize] || 'text-xs';

  const fontStyle = {
    ...(libraryStyleDto?.fontName
      ? { fontFamily: libraryStyleDto.fontName }
      : {}),
  };

  return (
    <div
      ref={viewOnly ? null : node => dragRef(dropRef(node))}
      className={`${heightClass} touch-none text-center rounded-lg cursor-pointer shadow-md flex items-center justify-center ${item.bookColor} ${
        isOver && canDrop ? 'border-4 border-dashed border-gray-500' : ''
      }`}
      style={{ ...thicknessStyle, opacity: isDragging ? 0.5 : 1 }}
      onClick={() => onBookClick(item)}
    >
      <span
        className={`writing-vertical ${fontSizeClass} line-clamp-1`}
        style={{ ...fontStyle }}
      >
        {getTitle(item.book.title)}
      </span>
    </div>
  );
};

export default Book;
