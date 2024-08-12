import React from 'react';

const DeleteLibraryModal = ({
  showDeleteModal,
  deleteLibrary,
  setShowDeleteModal,
}) => {
  if (!showDeleteModal) return null;

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-pink-100 p-6 rounded-lg shadow-lg w-80 max-w-full'>
        <h2 className='text-xl font-bold mb-4'>서재 삭제</h2>
        <p className='mb-4'>정말로 서재를 삭제하시겠습니까?</p>
        <div className='flex justify-end'>
          <button
            onClick={() => {
              deleteLibrary();
              setShowDeleteModal(false);
            }}
            className='bg-pink-400 text-white p-2 rounded-lg mr-2'
          >
            삭제
          </button>
          <button
            onClick={() => setShowDeleteModal(false)}
            className='bg-gray-500 text-white p-2 rounded-lg'
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default DeleteLibraryModal;
