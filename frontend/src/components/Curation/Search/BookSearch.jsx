import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';
import { BiSearch } from 'react-icons/bi';
import { getAladinBooks } from '@services/Book';
import SearchResultSection from '@components/Curation/Search/SearchResultSection';

const BookSearch = ({ isOpen, onRequestClose, text, tag }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchTag, setSearchTag] = useState(tag || '');
  const [isSearched, setIsSearched] = useState(false);
  const [loading, setLoading] = useState(false);
  const [searchResults, setSearchResults] = useState({
    bookStore: [],
  });

  useEffect(() => {
    if (text && tag) {
      setSearchTerm(text);
      setSearchTag(tag);
      handleSearch(text, tag);
    }
  }, [text, tag]);

  const handleSearch = async (text, tag) => {
    setIsSearched(true);
    setLoading(true);
    try {
      // 도서
      const aladinBooksData = await getAladinBooks(text, tag);
      setSearchResults({
        library: [], // 필요에 따라 업데이트
        bookStore: aladinBooksData.item || [],
      });
    } catch (error) {
      console.error('error', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearchChange = event => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = event => {
    event.preventDefault();
    handleSearch(searchTerm, searchTag);
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
        {/* 검색 결과를 보여주는 섹션 */}
        {console.log(
          'books being passed to SearchResultSection:',
          searchResults.bookStore
        )}
        <SearchResultSection
          title='도서 검색 결과'
          books={searchResults.bookStore}
          type='book'
        />
      </form>
    </Modal>
  );
};

export default BookSearch;
