import React, { useState } from 'react';
import ParticipatedBook from '@components/BookTalk/ParticipatedBook';
import PopularBook from '@components/BookTalk/PopularBook';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import { IoSearchSharp } from 'react-icons/io5';
import { books } from '@mocks/BookData';
import { useNavigate } from 'react-router-dom';

const BookTalkMain = () => {
  const navigate = useNavigate();

  const [participatedBooks, setParticipatedBooks] = useState(books);
  const [popularBooks, setPopularBooks] = useState(books);

  const handleSearchPage = () => {
    // 검색 페이지 이동?
  };

  const handleDetailPage = id => {
    navigate(`/booktalk/detail/${id}`);
  };

  return (
    <WrapContainer>
      <div className='flex justify-between items-center mb-6'>
        <Button>독서록 생성</Button>
        <button type='submit' onClick={handleSearchPage} className='p-2'>
          <IoSearchSharp className='w-5 h-5' />
        </button>
      </div>
      <div>
        <h2 className='text-green-400 text-lg font-bold mb-4'>
          ✅ 내가 참여한 도서
        </h2>
        {participatedBooks?.slice(0, 3)?.map((book, index) => (
          <ParticipatedBook
            key={index}
            book={book}
            onClick={() => handleDetailPage(book.book_id)}
          />
        ))}
        <div className='flex justify-end text-sm'>
          <button className='text-gray-500'>더보기</button>
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
    </WrapContainer>
  );
};

export default BookTalkMain;
