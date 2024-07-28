import React, { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchBookItem from '@components/Library/Search/SearchBookItem';
import SearchLibraryItem from '@components/Library/Search/SearchLibraryItem';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import { books as bookData } from '@mocks/BookData';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import useModal from '@hooks/useModal';

const SearchMore = () => {
  const { type } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const searchText = location.state?.searchText || '';

  const [books, setBooks] = useState(bookData);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);

  const { isOpen, toggleModal } = useModal();

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = () => {
    // API 연동
  };

  const handleBookClick = book => {
    navigate(`/${type}/detail/${book.book_id}`, { state: { book } });
  };

  const renderBookItem = book => {
    switch (type) {
      case 'library':
        return (
          <SearchLibraryItem
            key={book.book_id}
            book={book}
            onClick={() => handleBookClick(book)}
          />
        );
      case 'book':
        return (
          <SearchBookItem
            key={book.book_id}
            book={book}
            onClick={() => handleBookClick(book)}
            onCreateClick={toggleModal}
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

  return (
    <WrapContainer className='mt-4'>
      <h1 className='text-2xl font-bold mb-4'>
        {type === 'library' ? '내 서재' : type === 'book' ? '도서' : '북톡'}{' '}
        검색 결과
      </h1>
      {books.map(renderBookItem)}
      {loading && <p>Loading...</p>}
      {!hasMore && <p>더 이상 결과가 없습니다.</p>}
      {type === 'book' && (
        <BookCreateModal
          isCreateModalOpen={isOpen}
          toggleCreateModal={toggleModal}
        />
      )}
    </WrapContainer>
  );
};

export default SearchMore;
