import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import { books as bookData } from '@mocks/BookData';
import RadioButton from '@components/@common/RadioButton';

const sortOptions = [
  { id: 1, value: 'chat', name: '채팅순' },
  { id: 2, value: 'recent', name: '최근 대화순' },
];

const BookTalkMore = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [books, setBooks] = useState(bookData);
  const [selectedOption, setSelectedOption] = useState('chat');
  const [page, setPage] = useState(1); // 페이지네이션
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true); // 더 있는지?

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const sort = params.get('sort');
    if (sort) {
      setSelectedOption(sort);
    }
    loadBooks(sort);
  }, [location.search]);

  const loadBooks = sort => {
    // API 연동 /api/books?sort=chat
  };

  const handleSortChange = value => {
    navigate(`?sort=${value}`);
  };

  const handleBookClick = book => {
    navigate(`/booktalk/detail/${book.book_id}`, { state: { book } });
  };

  return (
    <WrapContainer className='mt-4'>
      <h1 className='text-2xl font-bold mb-3'>내가 참여한 도서</h1>

      <div className='mb-3 flex '>
        <RadioButton
          tags={sortOptions}
          selectedTag={selectedOption}
          setSelectedTag={setSelectedOption}
        />
      </div>
      {books.map(book => (
        <BookTalkItem
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

export default BookTalkMore;
