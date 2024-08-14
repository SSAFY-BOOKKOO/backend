import React, { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { BiSearch } from 'react-icons/bi';
import { useInView } from 'react-intersection-observer';
import BookItem from '@components/Curation/Search/BookItem';
import useBookInfiniteScroll from '@hooks/useBookInfiniteScroll';
import WrapContainer from '@components/Layout/WrapContainer';
import useModal from '@hooks/useModal';
import { useSetAtom } from 'jotai';
import { curationBookAtom } from '@atoms/curationBookAtom';

const BookSearch = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [isSearched, setIsSearched] = useState(false);
  const [searchParams] = useSearchParams();
  const [selectedBook, setSelectedBook] = useState(null);
  const setBook = useSetAtom(curationBookAtom);

  const text = searchParams.get('text') || '';
  const navigate = useNavigate();

  useEffect(() => {
    if (text) {
      setSearchTerm(text);
      setIsSearched(true);
    }
  }, [text]);

  // 무한 스크롤
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useBookInfiniteScroll(searchTerm, null);

  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const handleSearchChange = event => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = event => {
    event.preventDefault();
    setIsSearched(true);
  };

  // 도서 등록
  const handleBookCreateButton = book => {
    setSelectedBook(book);
    setBook(book);
    navigate('/curation/letter-create');
  };

  // 검색 결과

  const renderBookItem = book => {
    return (
      <BookItem
        key={book.id}
        book={book}
        onCreateClick={() => handleBookCreateButton(book)}
      />
    );
  };

  return (
    <div className='flex flex-col items-center p-4'>
      <form
        onSubmit={handleSearchSubmit}
        className='bg-white rounded-lg p-6 w-full max-w-md'
      >
        <div className='flex items-center pl-2'>
          <input
            type='text'
            placeholder='책 제목/작가명으로 검색'
            value={searchTerm}
            onChange={handleSearchChange}
            className='flex-1 p-2 border border-gray-300 rounded-md'
          />
          <button
            type='submit'
            className='ml-2 p-2 bg-transparent cursor-pointer'
          >
            <BiSearch className='text-2xl' />
          </button>
        </div>
      </form>

      {isSearched && (
        <WrapContainer className='mt-4'>
          {data?.pages?.map((page, index) => (
            <div key={index}>{page.data?.map(renderBookItem)}</div>
          ))}
          {isFetchingNextPage && <p className='text-center'>로딩중...</p>}
          {!hasNextPage && data?.pages?.length > 0 && (
            <p className='text-center'>더 이상 결과가 없습니다.</p>
          )}
          <div ref={ref}></div>
        </WrapContainer>
      )}
    </div>
  );
};

export default BookSearch;
