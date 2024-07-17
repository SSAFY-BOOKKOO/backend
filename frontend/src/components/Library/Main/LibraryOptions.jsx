import React from 'react';

const LibraryOptions = ({
  activeLibrary,
  setActiveLibrary,
  libraries,
  showMenu,
  setShowMenu,
  setShowModal,
  setShowCreateModal,
  clearLibrary,
}) => (
  <div className='flex items-center justify-between w-full px-4 relative'>
    <select
      className='p-2 border rounded-lg'
      value={activeLibrary}
      onChange={e => setActiveLibrary(Number(e.target.value))}
    >
      {libraries.map((library, index) => (
        <option key={index} value={index}>
          {library.name}
        </option>
      ))}
    </select>
    <button
      className='p-2 bg-pink-500 text-white rounded-lg ml-2'
      onClick={() => setShowMenu(true)} // + 버튼 클릭 시 더보기 메뉴 표시
    >
      +
    </button>
    {showMenu && (
      <div className='absolute top-full right-0 mt-2 bg-white shadow-lg rounded-lg p-4 z-10 w-64'>
        <button
          className='absolute top-2 right-2 text-gray-400 hover:text-gray-600'
          onClick={() => setShowMenu(false)}
        >
          ✕
        </button>
        <button
          onClick={() => {
            setShowMenu(false);
            setShowModal(true);
          }}
          className='bg-blue-500 text-white p-2 rounded-lg w-full mb-2'
        >
          서재명 변경
        </button>
        <button
          onClick={() => {
            setShowMenu(false);
            clearLibrary();
          }}
          className='bg-red-500 text-white p-2 rounded-lg w-full'
        >
          모든 책 삭제
        </button>
        <button
          onClick={() => {
            setShowMenu(false);
            setShowCreateModal(true);
          }}
          className='bg-green-500 text-white p-2 rounded-lg w-full mt-2'
        >
          서재 생성
        </button>
      </div>
    )}
  </div>
);

export default LibraryOptions;
