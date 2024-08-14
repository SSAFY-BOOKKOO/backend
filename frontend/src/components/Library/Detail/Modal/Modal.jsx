// src/components/Library/Detail/Modal/Modal.jsx
import React, { useState } from 'react';
import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';

const Modal = ({ bookId, onDelete, onColorChange, onShelfChange }) => {
  const [menuVisible, setMenuVisible] = useState(false);
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const toggleMenu = () => {
    setMenuVisible(!menuVisible);
  };

  const handleOptionClick = option => {
    if (option === '삭제') {
      handleDelete();
    } else if (option === '색 변경') {
      setMenuVisible(false);
      onColorChange();
    } else if (option === '서재 이동') {
      setMenuVisible(false);
      onShelfChange();
    }

    setMenuVisible(false);
  };

  const handleDelete = () => {
    onDelete(bookId);
  };

  return (
    <div className='relative'>
      <div
        className='absolute top-2 right-4 z-10 cursor-pointer text-4xl'
        onClick={toggleMenu}
      >
        &#x22EE;
      </div>

      {menuVisible && (
        <div className='absolute top-10 right-4 bg-white border border-gray-300 rounded-lg z-0'>
          <div
            className='px-4 py-2 cursor-pointer hover:bg-gray-100'
            onClick={() => handleOptionClick('삭제')}
          >
            삭제
          </div>
          <div
            className='px-4 py-2 cursor-pointer hover:bg-gray-100'
            onClick={() => handleOptionClick('서재 이동')}
          >
            서재 이동
          </div>
          <div
            className='px-4 py-2 cursor-pointer hover:bg-gray-100'
            onClick={() => handleOptionClick('색 변경')}
          >
            색 변경
          </div>
        </div>
      )}
    </div>
  );
};

export default Modal;
