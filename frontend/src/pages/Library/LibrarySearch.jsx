import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import RadioButton from '@components/@common/RadioButton';
import SearchForm from '@components/Library/Search/SearchForm';
import SearchResultSection from '@components/Library/Search/SearchResultSection';
import { getAladinBooks } from '@services/Book';
import { getMemberInfo } from '@services/Member';
import { getLibrarySearchBooks } from '@services/Library';
import Spinner from '@components/@common/Spinner';
import { useAtomValue } from 'jotai';
import { isLoadingAtom } from '@atoms/loadingAtom';
import { postBookTalkSearch } from '@services/BookTalk';

const LibrarySearch = () => {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const isLoading = useAtomValue(isLoadingAtom);
  const [isSearched, setIsSearched] = useState(false); // 검색여부
  const [searchText, setSearchText] = useState(''); // 검색 내용
  const [selectedTag, setSelectedTag] = useState('Title'); // 검색 카테고리
  const [searchResults, setSearchResults] = useState({
    // 데이터
    library: [],
    bookStore: [],
    bookTalk: [],
  });
  const [nickName, setNickName] = useState('');

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

  useEffect(() => {
    handleGetMemberInfo();
  }, []);

  const handleGetMemberInfo = async () => {
    const data = await getMemberInfo();
    setNickName(data?.nickName);
  };

  const handleSearch = async (text, tag) => {
    setIsSearched(true);

    try {
      const results = await Promise.allSettled([
        getLibrarySearchBooks(text, tag),
        getAladinBooks(text, tag),
        postBookTalkSearch(text, tag),
      ]);

      const [libraryResult, aladinResult, bookTalkResult] = results;

      setSearchResults({
        library:
          libraryResult.status === 'fulfilled' ? libraryResult.value : [],
        bookStore:
          aladinResult.status === 'fulfilled'
            ? aladinResult.value.item || []
            : [],
        bookTalk:
          bookTalkResult.status === 'fulfilled' ? bookTalkResult.value : [],
      });

      setSearchParams({ text, tag });
    } catch (error) {
      console.error('Unexpected error', error);
    }
  };

  const handleSearchSubmit = e => {
    e.preventDefault();
    handleSearch(searchText, selectedTag);
  };

  // 상세보기
  const showDetailPage = (type, book) => {
    if (type === 'booktalk') {
      navigate(`/${type}/detail/${book?.id}`, { state: { book } });
    } else if (type === 'book') {
      navigate(`/${type}/detail/${book?.isbn}`, { state: { book } });
    } else {
      navigate(`/${type}/${book?.libraryId}/detail/${book?.id}`, {
        state: { book, nickname: nickName },
      });
    }
  };

  // 더보기
  const handleSeeMore = type => {
    navigate(`/search/${type}/more?text=${searchText}&tag=${selectedTag}`, {
      state: { nickname: nickName },
    });
  };

  return (
    <WrapContainer className='mt-4'>
      <Spinner />
      <SearchForm
        placeholder='책을 검색하세요'
        searchText={searchText}
        setSearchText={setSearchText}
        onSubmit={handleSearchSubmit}
      />
      <RadioButton
        tags={tags}
        selectedTag={selectedTag}
        setSelectedTag={setSelectedTag}
      />
      {isSearched && !isLoading && (
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
