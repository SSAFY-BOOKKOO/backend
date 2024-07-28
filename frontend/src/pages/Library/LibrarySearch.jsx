import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import RadioButton from '@components/@common/RadioButton';
import { books } from '@mocks/BookData';
import SearchForm from '@components/Library/Search/SearchForm';
import SearchResultSection from '@components/Library/Search/SearchResultSection';

const LibrarySearch = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const [isSearched, setIsSearched] = useState(false); // 검색여부
  const [searchText, setSearchText] = useState(''); // 검색 내용
  const [libraryBooks, setLibraryBooks] = useState([]); // 내 서재
  const [bookStoreBooks, setBookStoreBooks] = useState([]); // 도서
  const [bookTalkBooks, setBookTalkBooks] = useState([]); // 북톡
  const [selectedTag, setSelectedTag] = useState(''); // 검색 카테고리

  const tags = [
    { id: 1, name: '제목', value: 'title' },
    { id: 2, name: '지은이', value: 'author' },
    { id: 3, name: '출판사', value: 'publisher' },
  ];

  useEffect(() => {
    const queryText = searchParams.get('text');
    if (queryText) {
      setSearchText(queryText);
      handleSearch(queryText);
    }
  }, []);

  const handleSearch = async text => {
    setIsSearched(true);
    try {
      // API 호출 로직 (현재는 더미 데이터)
      setLibraryBooks(books);
      setBookStoreBooks(books);
      setBookTalkBooks(books);
      setSearchParams({ text });
    } catch (error) {
      console.error('error', error);
    }
  };

  const handleSearchSubmit = e => {
    e.preventDefault();
    handleSearch(searchText);
  };

  // 상세보기
  const showDetailPage = (type, book) => {
    navigate(`/${type}/detail/${book.book_id}`, { state: { book } });
  };

  // 더보기
  const handleSeeMore = type => {
    navigate(`/search/${type}/more`, { state: { searchText } });
  };

  return (
    <WrapContainer className='mt-4'>
      <SearchForm
        searchText={searchText}
        setSearchText={setSearchText}
        onSubmit={handleSearchSubmit}
      />
      <RadioButton
        tags={tags}
        selectedTag={selectedTag}
        setSelectedTag={setSelectedTag}
      />
      {isSearched && (
        <>
          <SearchResultSection
            title='내 서재 검색'
            books={libraryBooks}
            onItemClick={book => showDetailPage('library', book)}
            onSeeMore={() => handleSeeMore('library')}
            type='library'
          />
          <SearchResultSection
            title='도서 검색'
            books={bookStoreBooks}
            onItemClick={book => showDetailPage('book', book)}
            onSeeMore={() => handleSeeMore('book')}
            type='book'
          />
          <SearchResultSection
            title='북톡 검색'
            books={bookTalkBooks}
            onItemClick={book => showDetailPage('booktalk', book)}
            onSeeMore={() => handleSeeMore('booktalk')}
            type='booktalk'
          />
        </>
      )}
    </WrapContainer>
  );
};

export default LibrarySearch;
