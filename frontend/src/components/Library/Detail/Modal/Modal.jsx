// src/components/Library/Detail/Modal/Modal.jsx
import React, { useState } from 'react';

const Modal = ({ bookId, onDelete }) => {
  const [menuVisible, setMenuVisible] = useState(false);

  const toggleMenu = () => {
    setMenuVisible(!menuVisible);
  };

  const handleOptionClick = (option) => {
    console.log(option);

    if (option === '삭제') {
      // BookDelete 컴포넌트의 handleDelete 호출
      handleDelete();
    }

    setMenuVisible(false);
  };

  const handleDelete = () => {
    onDelete(bookId);
  };

  return (
    <div className="relative">
      <div
        className="absolute top-0 right-0 z-10 cursor-pointer"
        onClick={toggleMenu}
      >
        &#x22EE;
      </div>

      {menuVisible && (
        <div className="absolute top-5 right-0 bg-white border border-gray-300 rounded-lg z-0">
          <div
            className="px-4 py-2 cursor-pointer hover:bg-gray-100"
            onClick={() => handleOptionClick('삭제')}
          >
            삭제
          </div>
          <div
            className="px-4 py-2 cursor-pointer hover:bg-gray-100"
            onClick={() => handleOptionClick('서재 이동')}
          >
            서재 이동
          </div>
          <div
            className="px-4 py-2 cursor-pointer hover:bg-gray-100"
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
