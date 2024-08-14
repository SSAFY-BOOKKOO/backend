import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { BiSearch } from 'react-icons/bi';
import { getAladinBooks } from '@services/Book';
import SearchResultSection from '@components/Curation/Search/SearchResultSection';

const BookSearch = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchTag, setSearchTag] = useState('');
  const [isSearched, setIsSearched] = useState(false);
  const [loading, setLoading] = useState(false);
  const [searchResults, setSearchResults] = useState({
    library: [],
    bookStore: [],
  });

  const [searchParams] = useSearchParams();

  useEffect(() => {
    const text = searchParams.get('text') || '';
    const tag = searchParams.get('tag') || '';
    if (text && tag) {
      setSearchTerm(text);
      setSearchTag(tag);
      handleSearch(text, tag);
    }
  }, [searchParams]);

  const handleSearch = async (text, tag) => {
    setIsSearched(true);
    setLoading(true);
    try {
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
    <div className='flex flex-col items-center p-4'>
      <form
        onSubmit={handleSearchSubmit}
        className='bg-white rounded-lg p-6 w-full max-w-md'
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
            <BiSearch className='text-2xl' />
          </button>
        </div>
      </form>
      {isSearched && (
        <div>
          {/* SearchBook -> SearchResultSection ->  commond=-book-bookItem*/}
          <SearchResultSection
            title='도서 검색 결과'
            books={searchResults.bookStore}
            // onItemClick={handleItemClick}
            // onSeeMore={handleSeeMore}
            type='book'
          />
        </div>
      )}
    </div>
  );
};

export default BookSearch;
