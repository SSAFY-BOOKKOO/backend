// src/components/Library/Detail/Modal/Modal.jsx

import React, { useState } from 'react';

const Modal = ({onDelete}) => {
  const [menuVisible, setMenuVisible] = useState(false); // Corrected useState syntax

  const toggleMenu = () => {
    setMenuVisible(!menuVisible); // Corrected setMenuVisible naming
  };

  const handleOptionClick = (option) => {
    console.log(option);

    if (option=='삭제') {
      onDelete();
    }

    setMenuVisible(false); // Corrected setMenuVisible naming
  };

  return (
    <div style={{ position: 'relative' }}>
      {/* 점 3개 */}
      <div
        className="menu-icon"
        onClick={toggleMenu}
        style={{ position: 'absolute', top: 0, right: 0, zIndex: 1, cursor: 'pointer' }}
      >
        &#x22EE;
      </div>

      {/* 선택지 option */}
      {menuVisible && (
        <div
          className="menu"
          style={{
            position: 'absolute',
            top: 20,
            right: 0,
            background: 'white',
            border: '1px solid #ccc',
            borderRadius: '8px',
            zIndex: 0,
          }}
        >
          <div className="menu-item" onClick={() => handleOptionClick('삭제')} style={{ padding: '8px 16px', cursor: 'pointer' }}>
            삭제
          </div>
          <div className="menu-item" onClick={() => handleOptionClick('서재 이동')} style={{ padding: '8px 16px', cursor: 'pointer' }}>
            서재 이동
          </div>
          <div className="menu-item" onClick={() => handleOptionClick('색 변경')} style={{ padding: '8px 16px', cursor: 'pointer' }}>
            색 변경
          </div>
        </div>
      )}
    </div>
  );
};

export default Modal;
