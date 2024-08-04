import React, { useEffect, useState } from 'react';
import { useParams, useSearchParams, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchBookItem from '@components/Library/Search/SearchBookItem';
import SearchLibraryItem from '@components/Library/Search/SearchLibraryItem';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import useBookInfiniteScroll from '@hooks/useBookInfiniteScroll';
import useLibraryInfiniteScroll from '@hooks/useLibraryInfiniteScroll';
import { useInView } from 'react-intersection-observer';
import useModal from '@hooks/useModal';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import { getAladinBookByIsbn } from '@services/Book';

const SearchMore = () => {
  const { type } = useParams();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const searchText = searchParams.get('text');
  const selectedTag = searchParams.get('tag');
  const { isOpen, toggleModal } = useModal();
  const [selectedBook, setSelectedBook] = useState(null);

  const getQuery = () => {
    switch (type) {
      case 'book':
        return useBookInfiniteScroll(searchText, selectedTag);
      case 'library':
        return useLibraryInfiniteScroll(searchText, selectedTag);
      case 'booktalk':
        return {
          data: null,
          fetchNextPage: () => {},
          hasNextPage: false,
          isFetchingNextPage: false,
        };
      default:
        return;
    }
  };

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } = getQuery();
  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
      console.log(hasNextPage, data?.pages?.length, data);
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const handleBookClick = book => {
    navigate(`/${type}/detail/${book.isbn}`, { state: { book } });
  };

  const handleBookCreateButton = async book => {
    try {
      const bookData = await getAladinBookByIsbn(book.isbn);
      setSelectedBook(bookData);
    } catch (error) {
      console.error('error', error);
    }

    toggleModal();
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
      <h1 className='text-2xl font-bold mb-4'>{getTitle()}</h1>
      {data?.pages?.map((page, index) => (
        <div key={index}>{page.data?.map(renderBookItem)}</div>
      ))}
      {isFetchingNextPage && <p className='text-center'>로딩중...</p>}
      <div ref={ref}></div>
      <BookCreateModal
        isCreateModalOpen={isOpen}
        toggleCreateModal={toggleModal}
        selectedBook={selectedBook}
      />
    </WrapContainer>
  );
};

export default SearchMore;
