import React, { useState, useEffect } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import BookSearchListItem from '@components/Library/Search/BookSearchListItem';
import { books as bookData } from '@mocks/BookData';

const SearchMoreDetail = () => {
  const { type } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const searchText = location.state?.searchText || '';

  const [books, setBooks] = useState(bookData);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    loadBooks();
  }, []);

  const loadBooks = () => {
    // API 연동
  };

  const handleBookClick = book => {
    // 책 상세 페이지로 이동하는 로직
    console.log(type);
    navigate(`/${type}/detail/${book.book_id}`, { state: { book } });
  };

  return (
    <WrapContainer className='mt-4'>
      <h1 className='text-2xl font-bold mb-4'>
        {type === 'library' ? '내 서재' : type === 'book' ? '도서' : '북톡'}{' '}
        검색 결과
      </h1>
      {books.map(book => (
        <BookSearchListItem
          key={book.book_id}
          book={book}
          onClick={() => handleBookClick(book)}
        />
      ))}
      {loading && <p>Loading...</p>}
      {!hasMore && <p>더 이상 결과가 없습니다.</p>}
    </WrapContainer>
  );
};

export default SearchMoreDetail;
