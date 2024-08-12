import React, { useState } from 'react';

const ChangeFontStyleModal = ({
  showModal,
  fontName,
  setFontName,
  fontSize,
  setFontSize,
  changeFontStyle,
  setShowModal,
}) => {
  const [localFontName, setLocalFontName] = useState(fontName || '');
  const [localFontSize, setLocalFontSize] = useState(fontSize || '0');

  const handleSave = () => {
    changeFontStyle(localFontName, localFontSize);
    setShowModal(false);
  };

  if (!showModal) return null;

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-pink-100 p-6 rounded-lg shadow-lg w-80 max-w-full'>
        <h2 className='text-xl font-bold mb-4'>폰트 스타일 변경</h2>
        <div className='mb-4'>
          <label className='block mb-2 font-bold'>폰트 이름</label>
          <select
            value={localFontName}
            onChange={e => setLocalFontName(e.target.value)}
            className='border p-2 w-full'
          >
            <option value=''>기본 폰트</option>
            <option value='GyeonggiCheonnyeon'>경기천년체</option>
            <option value='Inklipquid'>잉크립퀴드체</option>
            <option value='JejuDoldam'>제주돌담체</option>
          </select>
        </div>
        <div className='mb-4'>
          <label className='block mb-2 font-bold'>폰트 크기</label>
          <select
            value={localFontSize}
            onChange={e => setLocalFontSize(e.target.value)}
            className='border p-2 w-full'
          >
            <option value='0'>작게</option>
            <option value='1'>보통</option>
            <option value='2'>크게</option>
            <option value='3'>아주 크게</option>
          </select>
        </div>
        <div className='flex justify-end'>
          <button
            onClick={handleSave}
            className='bg-green-400 text-white p-2 rounded-lg mr-2'
          >
            확인
          </button>
          <button
            onClick={() => setShowModal(false)}
            className='bg-gray-500 text-white p-2 rounded-lg'
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default ChangeFontStyleModal;
