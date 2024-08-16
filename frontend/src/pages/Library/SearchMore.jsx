import React, { useEffect, useState } from 'react';
import {
  useParams,
  useSearchParams,
  useNavigate,
  useLocation,
} from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchBookItem from '@components/Library/Search/SearchBookItem';
import SearchLibraryItem from '@components/Library/Search/SearchLibraryItem';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import useBookInfiniteScroll from '@hooks/useBookInfiniteScroll';
import useLibraryInfiniteScroll from '@hooks/useLibraryInfiniteScroll';
import useBookTalkSearchInfiniteScroll from '@hooks/useBookTalkSearchInfiniteScroll';
import { useInView } from 'react-intersection-observer';
import useModal from '@hooks/useModal';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import { getAladinBookByIsbn } from '@services/Book';
import Spinner from '@components/@common/Spinner';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';

const SearchMore = () => {
  const { type } = useParams();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const location = useLocation();
  const searchText = searchParams.get('text');
  const selectedTag = searchParams.get('tag');
  const { isOpen, toggleModal } = useModal();
  const [selectedBook, setSelectedBook] = useState(null);
  const [, showAlert] = useAtom(showAlertAtom);

  const getQuery = () => {
    switch (type) {
      case 'book':
        return useBookInfiniteScroll(searchText, selectedTag);
      case 'library':
        return useLibraryInfiniteScroll(searchText, selectedTag);
      case 'booktalk':
        return useBookTalkSearchInfiniteScroll(searchText, selectedTag);

      default:
        return;
    }
  };

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isLoading } =
    getQuery();
  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const handleBookClick = book => {
    if (type === 'booktalk') {
      navigate(`/${type}/detail/${book?.id}`, { state: { book } });
    } else if (type === 'book') {
      navigate(`/${type}/detail/${book?.isbn}`, { state: { book } });
    } else {
      navigate(`/${type}/${book?.libraryId}/detail/${book?.id}`, {
        state: { book, nickname: location?.state?.nickname },
      });
    }
  };

  const handleBookCreateButton = async book => {
    if (book.inLibrary) {
      showAlert('이미 등록된 책입니다.', true, () => {});
      return;
    } else {
      try {
        const bookData = await getAladinBookByIsbn(book.isbn);

        setSelectedBook(bookData);
      } catch (error) {
        console.error('error', error);
      } finally {
        toggleModal();
      }
    }
  };

  const renderBookItem = book => {
    switch (type) {
      case 'library':
        return (
          <SearchLibraryItem
            key={book.id}
            book={book}
            onClick={() => handleBookClick(book)}
          />
        );
      case 'book':
        return (
          <SearchBookItem
            key={book.isbn}
            book={book}
            onClick={() => handleBookClick(book)}
            onCreateClick={() => handleBookCreateButton(book)}
          />
        );
      case 'booktalk':
        return (
          <BookTalkItem
            key={book.book_id}
            book={book}
            onClick={() => handleBookClick(book)}
          />
        );
      default:
        return null;
    }
  };

  const getTitle = () => {
    const titles = {
      library: '내 서재',
      book: '도서',
      booktalk: '북톡',
    };
    return `${titles[type] || ''} 검색 결과`;
  };

  return (
    <WrapContainer className='mt-4'>
      <Alert />
      {isLoading && <Spinner />}
      <h1 className='text-2xl font-bold mb-4'>{getTitle()}</h1>
      {data?.pages?.map((page, index) => (
        <div key={index}>{page.data?.map(renderBookItem)}</div>
      ))}
      {isFetchingNextPage && <Spinner infiniteScroll />}
      <div ref={ref}></div>
      <BookCreateModal
        isCreateModalOpen={isOpen}
        toggleCreateModal={toggleModal}
        selectedBook={selectedBook}
        setSelectedBook={setSelectedBook}
      />
    </WrapContainer>
  );
};

export default SearchMore;
