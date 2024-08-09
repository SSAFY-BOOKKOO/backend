import React, { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { useInView } from 'react-intersection-observer';
import BookItem from '@components/Curation/Search/BookItem';
import useBookInfiniteScroll from '@hooks/useBookInfiniteScroll';
import WrapContainer from '@components/Layout/WrapContainer';
import Spinner from '@components/@common/Spinner';
import SearchForm from '@components/Library/Search/SearchForm';
import { alertAtom, showAlertAtom } from '@atoms/alertAtom';
import { postBookTalks } from '@services/BookTalk';
import Alert from '@components/@common/Alert';
import { postBook } from '@services/Book';

const BookTalkCreate = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [isSearched, setIsSearched] = useState(false);
  const [searchParams] = useSearchParams();

  const navigate = useNavigate();

  const [, setAlert] = useAtom(alertAtom);
  const [, showAlert] = useAtom(showAlertAtom);

  // 무한 스크롤
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isLoading } =
    useBookInfiniteScroll(isSearched ? searchTerm : null, null);

  const { ref, inView } = useInView();
  console.log(isSearched);
  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const handleSearchSubmit = event => {
    event.preventDefault();
    if (searchTerm.trim() !== '') {
      setIsSearched(true);
    }
  };

  const handleBookCreate = async book => {
    try {
      const data = await postBookTalks(book.id);
      navigate(`/booktalk/detail/${data}`, { state: { book: book } });
    } catch (error) {
      showAlert('헉 오류가 발생했습니다! 다시 시도해주세요', true, () => {});
    }
  };

  // 도서 등록 -> 북톡 생성
  const handleBookCreateButton = async book => {
    showAlert(
      '북톡을 생성하시겠습니까?',
      false,
      async () => {
        // 확인
        await handleBookCreate(book);
      },
      () => {}
    );
  };

  //////////////////검색 결과

  const renderBookItem = book => {
    return (
      // BookItem으로 클릭 버튼 넘기기
      <BookItem
        key={book.id}
        book={book}
        onCreateClick={() => handleBookCreateButton(book)}
      />
    );
  };

  return (
    <WrapContainer className='mt-4'>
      <Alert />
      <SearchForm
        placeholder='책 제목/작가명을 검색하세요'
        searchText={searchTerm}
        setSearchText={setSearchTerm}
        onSubmit={handleSearchSubmit}
      />
      {isSearched && (
        <WrapContainer className='mt-4'>
          {isLoading && <Spinner />}
          {data?.pages?.map(page => (
            <div key={page?.startIndex}>{page.data?.map(renderBookItem)}</div>
          ))}
          {isFetchingNextPage && <Spinner infiniteScroll />}
          <div ref={ref}></div>
        </WrapContainer>
      )}
    </WrapContainer>
  );
};

export default BookTalkCreate;
