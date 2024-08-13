import React, { useState } from 'react';
import ColorPicker from '@components/Library/BookCreate/ColorPicker';
import { PRESET_COLORS } from '@constants/ColorData';

const ChangeLibraryColorModal = ({
  showModal,
  currentColor,
  changeLibraryColor,
  setShowModal,
}) => {
  const [selectedColor, setSelectedColor] = useState(currentColor);

  const handleSave = () => {
    changeLibraryColor(selectedColor);
    setShowModal(false);
  };

  if (!showModal) return null;

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-white p-6 rounded-lg shadow-lg w-80 max-w-full'>
        <h2 className='text-xl font-bold mb-4'>서재 색 변경</h2>
        <ColorPicker
          presetColors={PRESET_COLORS}
          selected={selectedColor}
          onChange={setSelectedColor}
        />
        <div className='flex justify-end mt-4'>
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

export default ChangeLibraryColorModal;
