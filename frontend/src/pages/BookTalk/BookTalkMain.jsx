import React, { useState } from 'react';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import PopularBook from '@components/BookTalk/PopularBook';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import { books } from '@mocks/BookData';
import { useNavigate } from 'react-router-dom';
import useModal from '@hooks/useModal';
import BookSearch from '@components/Curation/BookSearch';

const BookTalkMain = () => {
  const navigate = useNavigate();
  const { isOpen, toggleModal } = useModal();

  const [participatedBooks, setParticipatedBooks] = useState(books);
  const [popularBooks, setPopularBooks] = useState(books);

  const handleMorePage = () => {
    navigate(`/booktalk/more`);
  };

  const handleDetailPage = id => {
    navigate(`/booktalk/detail/${id}`);
  };

  return (
    <WrapContainer>
      <div className='flex justify-between items-center mb-6'>
        <Button onClick={toggleModal}>채팅방 생성</Button>
      </div>
      <div>
        <h2 className='text-green-400 text-lg font-bold mb-4'>
          ✅ 내가 참여한 도서
        </h2>
        {participatedBooks?.slice(0, 3)?.map((book, index) => (
          <BookTalkItem
            key={index}
            book={book}
            onClick={() => handleDetailPage(book.book_id)}
          />
        ))}
        <div className='flex justify-end text-sm cursor-pointer'>
          <button className='text-gray-500' onClick={handleMorePage}>
            더보기
          </button>
        </div>
      </div>
      <div className='mt-6'>
        <h2 className='text-pink-500 text-lg font-bold mb-4'>
          💥 최근 인기 도서
        </h2>
        <div className='relative'>
          <div className='flex space-x-4 overflow-x-auto scrollbar-none pb-4 pl-4'>
            {popularBooks?.slice(0, 5)?.map((book, index) => (
              <PopularBook
                key={index}
                book={book}
                onClick={() => handleDetailPage(book.book_id)}
              />
            ))}
          </div>
          <div className='absolute top-0 right-0 bottom-0 w-8 from-white pointer-events-none'></div>
        </div>
      </div>
      <BookSearch isOpen={isOpen} onRequestClose={toggleModal} />
    </WrapContainer>
  );
};

export default BookTalkMain;
