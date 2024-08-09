import React from 'react';

const CreateLibraryModal = ({
  showCreateModal,
  createLibraryName,
  setCreateLibraryName,
  createLibrary,
  setShowCreateModal,
}) => {
  if (!showCreateModal) return null;

  const handleCreateLibrary = () => {
    createLibrary();
    setShowCreateModal(false);
    setCreateLibraryName('');
  };

  return (
    <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
      <div className='bg-pink-100 p-6 rounded-lg shadow-lg w-80 max-w-full'>
        <h2 className='text-xl font-bold mb-4'>새 서재 생성</h2>
        <input
          type='text'
          placeholder='서재 이름'
          value={createLibraryName}
          onChange={e => setCreateLibraryName(e.target.value)}
          className='border p-2 mb-4 w-full'
        />
        <div className='flex justify-end'>
          <button
            onClick={handleCreateLibrary}
            className='bg-green-400 text-white p-2 rounded-lg mr-2'
          >
            확인
          </button>
          <button
            onClick={() => setShowCreateModal(false)}
            className='bg-gray-500 text-white p-2 rounded-lg'
          >
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default CreateLibraryModal;
