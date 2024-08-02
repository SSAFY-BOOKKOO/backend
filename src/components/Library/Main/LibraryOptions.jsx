import React, { useState } from 'react';
import Button from '../../@common/Button';
import DeleteLibraryModal from './DeleteLibraryModal';

const LibraryOptions = ({
  activeLibrary,
  setActiveLibrary,
  libraries,
  showMenu,
  setShowMenu,
  setShowModal,
  setShowCreateModal,
  deleteLibrary,
}) => {
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const handleDeleteLibrary = () => {
    if (libraries.length <= 1) {
      alert('서재가 하나만 남아 있어 삭제할 수 없습니다.');
    } else {
      setShowDeleteModal(true);
    }
  };

  return (
    <div className='flex items-center justify-between w-full px-4 relative'>
      <div className='relative w-64'>
        <select
          className='block w-full p-2 border rounded-lg bg-white text-black text-center appearance-none'
          style={{ textAlignLast: 'center' }}
          value={activeLibrary}
          onChange={e => setActiveLibrary(Number(e.target.value))}
        >
          {libraries.map((library, index) => (
            <option key={index} value={index} className='text-center'>
              {library.name}
            </option>
          ))}
        </select>
      </div>
      <Button
        text='+'
        color='text-white bg-green-400 active:bg-pink-400'
        size='medium'
        onClick={() => setShowMenu(true)}
      />
      {showMenu && (
        <div className='absolute top-full right-0 mt-2 bg-white shadow-lg rounded-lg p-4 z-10 w-64'>
          <Button
            text='✕'
            color='text-gray-400 bg-transparent hover:text-gray-600'
            size='small'
            onClick={() => setShowMenu(false)}
            className='absolute top-1 right-1'
          />
          <div className='mt-4'>
            <Button
              text='서재명 변경'
              color='text-white bg-blue-500 active:bg-blue-600'
              size='medium'
              onClick={() => {
                setShowMenu(false);
                setShowModal(true);
              }}
              className='w-full mb-2'
            />
            <Button
              text='서재 삭제'
              color='text-white bg-red-500 active:bg-red-600'
              size='medium'
              onClick={handleDeleteLibrary}
              className='w-full'
            />
            <Button
              text='서재 생성'
              color='text-white bg-green-400 active:bg-green-600'
              size='medium'
              onClick={() => {
                setShowMenu(false);
                setShowCreateModal(true);
              }}
              className='w-full mt-2'
            />
          </div>
        </div>
      )}
      <DeleteLibraryModal
        showDeleteModal={showDeleteModal}
        deleteLibrary={deleteLibrary}
        setShowDeleteModal={setShowDeleteModal}
      />
    </div>
  );
};

export default LibraryOptions;
