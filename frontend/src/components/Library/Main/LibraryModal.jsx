import React from 'react';

const LibraryModal = ({
  showModal,
  newLibraryName,
  setNewLibraryName,
  changeLibraryName,
  setShowModal,
}) => {
  if (!showModal) return null;

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-blue-100 p-6 rounded-lg shadow-lg'>
        <h2 className='text-xl font-bold mb-4'>서재명 변경</h2>
        <input
          type='text'
          placeholder='새 서재명'
          value={newLibraryName}
          onChange={e => setNewLibraryName(e.target.value)}
          className='border p-2 mb-4 w-full'
        />
        <div className='flex justify-end'>
          <button
            onClick={changeLibraryName}
            className='bg-blue-500 text-white p-2 rounded-lg mr-2'
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

export default LibraryModal;
