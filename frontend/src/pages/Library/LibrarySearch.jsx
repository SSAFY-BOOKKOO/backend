import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import RadioButton from '@components/@common/RadioButton';
import { books } from '@mocks/BookData';
import SearchForm from '@components/Library/Search/SearchForm';
import SearchResultSection from '@components/Library/Search/SearchResultSection';
import { getAladinBooks } from '@services/Book';
import { getLibrarySearchBooks } from '@services/Library';

const LibrarySearch = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const [loading, setLoading] = useState(false); // 로딩여부
  const [isSearched, setIsSearched] = useState(false); // 검색여부
  const [searchText, setSearchText] = useState(''); // 검색 내용
  const [selectedTag, setSelectedTag] = useState('Title'); // 검색 카테고리
  const [searchResults, setSearchResults] = useState({
    // 데이터
    library: [],
    bookStore: [],
    bookTalk: [],
  });

  const tags = [
    { id: 1, name: '제목', value: 'Title' },
    { id: 2, name: '지은이', value: 'Author' },
    { id: 3, name: '출판사', value: 'Publisher' },
  ];

  useEffect(() => {
    const queryText = searchParams.get('text');
    const queryTag = searchParams.get('tag') || 'Title';
    if (queryText) {
      setSearchText(queryText);
      setSelectedTag(queryTag);
      handleSearch(queryText, queryTag);
    }
  }, []);

  const handleSearch = async (text, tag) => {
    setIsSearched(true);
    setLoading(true);
    try {
      // API 호출 로직 (현재는 더미 데이터)

      // 내 서재
      const libraryBooksData = await getLibrarySearchBooks(text, tag);
      // 도서
      const aladinBooksData = await getAladinBooks(text, tag);

      // 북톡
      setSearchResults({
        library: libraryBooksData,
        bookStore: aladinBooksData.item || [],
        bookTalk: books,
      });

      setSearchParams({ text, tag }); // 쿼리 스트링에 검색어와 태그 저장
    } catch (error) {
      console.error('error', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearchSubmit = e => {
    e.preventDefault();
    handleSearch(searchText, selectedTag);
  };

  // 상세보기
  const showDetailPage = (type, book) => {
    navigate(`/${type}/detail/${book.book_id}`, { state: { book } });
  };

  // 더보기
  const handleSeeMore = type => {
    navigate(`/search/${type}/more`, { state: { searchText, selectedTag } });
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
      {loading && <div>Loading...</div>}
      {isSearched && (
        <>
          <SearchResultSection
            title='내 서재 검색'
            books={searchResults.library}
            onItemClick={book => showDetailPage('library', book)}
            onSeeMore={() => handleSeeMore('library')}
            type='library'
          />
          <SearchResultSection
            title='도서 검색'
            books={searchResults.bookStore}
            onItemClick={book => showDetailPage('book', book)}
            onSeeMore={() => handleSeeMore('book')}
            type='book'
          />
          <SearchResultSection
            title='북톡 검색'
            books={searchResults.bookTalk}
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
