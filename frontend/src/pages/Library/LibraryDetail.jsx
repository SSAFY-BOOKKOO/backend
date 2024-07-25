import React, { useState } from 'react';
import { AiFillStar } from 'react-icons/ai';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { CSSTransition, SwitchTransition } from 'react-transition-group';
import Modal from '@components/Library/Detail/Modal/Modal';
import ReviewCom from '@components/Library/Detail/Review/ReviewCom';
import './LibraryDetail.css'; // CSS 파일 추가

const LibraryDetail = () => {
  const { state } = useLocation();
  const { id } = useParams();
  const [showReview, setShowReview] = useState(false);
  const navigate = useNavigate();
  const maxLength = 100;

  // book 정보 받기
  const { title, author, publisher, summary, cover_img_url } = state.book;

  // 삭제 로직
  const handleDelete = bookId => {
    console.log({ bookId });
    navigate('/library', { state: { deleteBookId: bookId } });
  };

  // 요약 텍스트 길이 조정
  const displaySummary =
    summary.length > maxLength
      ? summary.substring(0, maxLength - 1) + '...'
      : summary;

  return (
    <div className='flex flex-col items-center h-[43rem] mt-16 overflow-hidden scrollbar-none'>
      <SwitchTransition>
        <CSSTransition
          key={showReview ? 'review' : 'details'}
          addEndListener={(node, done) => {
            node.addEventListener('transitionend', done, false);
          }}
          classNames='fade'
        >
          {showReview ? (
            <ReviewCom
              bookId={id}
              onBackClick={() => setShowReview(false)}
              book={state.book}
            />
          ) : (
            // 책 큰 틀

            <div className='relative  bg-zinc-300 rounded-lg w-10/12 max-w-md h-full overflow-auto '>
              <Modal bookId={id} onDelete={handleDelete} />
              <div className='flex flex-col items-center p-8 pl-12'>
                <img
                  src={cover_img_url}
                  alt={title}
                  className='w-72 h-96 cursor-pointer rounded-lg shadow-xl mr-4'
                  onClick={() => setShowReview(true)}
                />
                {/* <button>전환</button> */}
              </div>
              <div className='absolute left-4 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-20'></div>

              {/* 띠지 부분 */}
              <div className='mt-6 p-4 pl-14 bg-pink-500 rounded-b-md opacity-70 w-full  h-[216px]'>
                <h2 className=' text-2xl text-black font-bold'>{title}</h2>
                <div className='flex space-x-1'>
                  {Array(5).fill(<AiFillStar className='text-amber-300' />)}
                </div>
                <p className='text-lg text-black'>{author}</p>
                <p className='text-sm text-black'>{publisher}</p>
                <p className='mt-2 text-sm text-black'>{displaySummary}</p>
              </div>
            </div>
          )}
        </CSSTransition>
      </SwitchTransition>
    </div>
  );
};

export default LibraryDetail;
