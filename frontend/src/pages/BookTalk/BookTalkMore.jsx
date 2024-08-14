import React, { useState, useEffect, useCallback } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import WrapContainer from '@components/Layout/WrapContainer';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import RadioButton from '@components/@common/RadioButton';
import { useInView } from 'react-intersection-observer';
import useMyBookTalkInfiniteScroll from '@hooks/useMyBookTalkInfiniteScroll';
import Spinner from '@components/@common/Spinner';

const sortOptions = [
  { id: 1, value: 'time', name: '최근 대화순' },
  { id: 2, value: 'chat', name: '채팅순' },
];

const BookTalkMore = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [selectedOption, setSelectedOption] = useState('time');
  const { ref, inView } = useInView();

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, status } =
    useMyBookTalkInfiniteScroll(selectedOption);

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, fetchNextPage, hasNextPage]);

  const handleSortChange = value => {
    setSelectedOption(value);
  };

  const handleBookClick = book => {
    navigate(`/booktalk/detail/${book?.bookTalkId}`, { state: { book } });
  };

  return (
    <WrapContainer className='mt-4'>
      <h1 className='text-2xl font-bold mb-3'>내가 참여한 도서</h1>

      <div className='mb-3 flex'>
        <RadioButton
          tags={sortOptions}
          selectedTag={selectedOption}
          setSelectedTag={handleSortChange}
        />
      </div>
      {status === 'loading' ? (
        <Spinner />
      ) : (
        <>
          {data?.pages[0].data.map((book, index) => (
            <BookTalkItem
              key={`${book?.bookTalkId}-${index}`}
              book={book}
              onClick={() => handleBookClick(book)}
            />
          ))}
          <div ref={ref}>
            {isFetchingNextPage ? <Spinner infiniteScroll /> : null}
          </div>
        </>
      )}
    </WrapContainer>
  );
};

export default BookTalkMore;
