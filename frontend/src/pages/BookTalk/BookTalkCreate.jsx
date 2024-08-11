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
import { postBookTalks, getBookTalkByBookId } from '@services/BookTalk';
import Alert from '@components/@common/Alert';

const BookTalkCreate = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [isSearched, setIsSearched] = useState(false);
  const [searchParams] = useSearchParams();

  const navigate = useNavigate();

  const [, showAlert] = useAtom(showAlertAtom);

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isLoading } =
    useBookInfiniteScroll(isSearched ? searchTerm : null, null);

  const { ref, inView } = useInView();

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

  const handleBookCreateButton = async book => {
    if (!book.id) {
      showAlert('앗 오류가 발생했습니다. 다시 시도해주세요!', true, () => {});
      return;
    }

    try {
      const booktalkData = await getBookTalkByBookId(book.id);

      if (booktalkData) {
        showAlert(
          '이미 북톡이 존재합니다. 해당 북톡으로 이동하시겠습니까?',
          false,
          () =>
            navigate(`/booktalk/detail/${booktalkData.bookTalkId}`, {
              state: { book: book },
            }),
          () => {}
        );
        return;
      }

      // 북톡이 존재 X
    } catch (error) {
      if (error.response && error.response.status === 404) {
        createNewBookTalk(book);
      } else {
        showAlert('앗 오류가 발생했습니다. 다시 시도해주세요!', true, () => {});
      }
    }
  };

  const createNewBookTalk = book => {
    showAlert(
      '북톡을 생성하시겠습니까?',
      false,
      async () => {
        try {
          const bookTalkResponse = await postBookTalks(book.id);
          navigate(`/booktalk/detail/${bookTalkResponse}`, {
            state: { book: book },
          });
        } catch (error) {
          console.error('Error creating book talk:', error);
          showAlert(
            '앗 오류가 발생했습니다. 다시 시도해주세요!',
            true,
            () => {}
          );
        }
      },
      () => {} // 취소 시 동작
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
            <div key={page.startIndex}>
              {page.data?.map(book => (
                <BookItem
                  key={book.id}
                  book={book}
                  onCreateClick={handleBookCreateButton}
                />
              ))}
            </div>
          ))}
          {isFetchingNextPage && <Spinner infiniteScroll />}
          <div ref={ref}></div>
        </WrapContainer>
      )}
    </WrapContainer>
  );
};

export default BookTalkCreate;
