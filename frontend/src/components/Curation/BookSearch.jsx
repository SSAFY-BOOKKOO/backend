import React, { useState } from 'react';
import Modal from 'react-modal';
import { BiSearch } from 'react-icons/bi';

const BookSearch = ({ isOpen, onRequestClose }) => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = event => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = event => {
    event.preventDefault();
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onRequestClose}
      className='flex items-center justify-center'
      overlayClassName='fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center'
    >
      <form
        onSubmit={handleSearchSubmit}
        className='bg-white rounded-lg p-6 shadow-lg w-80'
      >
        <div className='flex items-center'>
          <input
            type='text'
            placeholder='책 제목/작가명으로 검색'
            value={searchTerm}
            onChange={handleSearchChange}
            className='flex-1 p-2 border border-gray-300 rounded-md'
          />
          <button
            type='submit'
            className='ml-2 p-2 bg-transparent border-none cursor-pointer'
          >
            <BiSearch />
          </button>
        </div>
      </form>
    </Modal>
  );
};

export default BookSearch;
