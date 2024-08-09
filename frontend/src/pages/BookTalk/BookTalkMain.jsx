import React, { useEffect, useState } from 'react';
import BookTalkItem from '@components/@common/Book/BookTalkItem';
import PopularBook from '@components/BookTalk/PopularBook';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import Spinner from '@components/@common/Spinner';
import { useNavigate } from 'react-router-dom';
import BookSearch from '@components/Curation/Search/BookSearch';
import { getMyBookTalk, getPopularBookTalk } from '../../services/BookTalk';

const BookTalkMain = () => {
  const navigate = useNavigate();

  const [participatedBooks, setParticipatedBooks] = useState([]);
  const [popularBooks, setPopularBooks] = useState([]);

  const handleMorePageMove = () => {
    navigate(`/booktalk/more`);
  };

  const handleDetailPageMove = book => {
    navigate(`/booktalk/detail/${book?.bookTalkId}`, { state: { book: book } });
  };

  const handleCreatePageMove = () => {
    navigate(`/booktalk/create`);
  };

  const handlePopularBooks = async () => {
    const data = await getPopularBookTalk();
    setPopularBooks(data);
  };

  const handleParticipatedBooks = async () => {
    const data = await getMyBookTalk();
    setParticipatedBooks(data);
  };

  useEffect(() => {
    Promise.all([handlePopularBooks(), handleParticipatedBooks()]);
  }, []);

  return (
    <WrapContainer>
      <Spinner />
      <div className='flex justify-between items-center mb-6'>
        <Button onClick={handleCreatePageMove} size='small'>
          채팅방 생성
        </Button>
      </div>
      <div>
        <h2 className='text-green-400 text-lg font-bold mb-4'>
          ✅ 내가 참여한 도서
        </h2>
        {participatedBooks?.slice(0, 3)?.map((book, index) => (
          <BookTalkItem
            key={index}
            book={book}
            onClick={() => handleDetailPageMove(book)}
          />
        ))}
        <div className='flex justify-end text-sm cursor-pointer'>
          <button className='text-gray-500' onClick={handleMorePageMove}>
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
                onClick={() => handleDetailPageMove(book)}
              />
            ))}
          </div>
          <div className='absolute top-0 right-0 bottom-0 w-8 from-white pointer-events-none'></div>
        </div>
      </div>
    </WrapContainer>
  );
};

export default BookTalkMain;
