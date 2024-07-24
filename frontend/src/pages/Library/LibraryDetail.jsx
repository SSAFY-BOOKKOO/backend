// src/components/Library/Detail/LibraryDetail.jsx
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

  // book 정보 받기
  const { title, author, publisher, summary, cover_img_url } = state.book;

  // 삭제 로직
  const handleDelete = bookId => {
    console.log({ bookId });
    navigate('/library', { state: { deleteBookId: bookId } });
  };

  return (
    // 큰 틀
    <div className='flex flex-col items-center min-h-screen overflow-hidden scrollbar-none'>
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
            // 책 담는 틀
            // 전체 책 담는 틀
            <div className='relative bg-zinc-300 rounded-lg w-10/12 max-w-md h-full min-h-[600px] overflow-auto'>
              {/* modal: 책 삭제, 서재 이동, 색 변경 */}
              <Modal bookId={id} onDelete={handleDelete} />

              {/* css 참고 */}
              <div className='flex flex-col items-center p-8 pl-12'>
                <img
                  src={cover_img_url}
                  alt={title}
                  className='w-72 h-96 cursor-pointer rounded-lg shadow-xl'
                  onClick={() => setShowReview(true)}
                />
              </div>
              {/* 띠지 영역(핑크) */}
              <div className='mt-6 p-4 pl-14 bg-pink-500 rounded-b-md opacity-70 w-full'>
                <h2 className='mt-4 text-2xl text-black font-bold'>{title}</h2>
                <div className='flex items-center space-x-4'>
                  <p className='text-lg text-black'>{author}</p>
                  <p className='text-sm text-black'>{publisher}</p>
                </div>
                <p className='mt-2 text-sm text-black'>{summary}</p>
                <div className='flex space-x-1 pt-3'>
                  {Array(5).fill(<AiFillStar className='text-amber-300' />)}
                </div>

                {/* 하드커버 선 */}
                <div className='absolute left-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl'></div>
              </div>
            </div>
          )}
        </CSSTransition>
      </SwitchTransition>
    </div>
  );
};

export default LibraryDetail;
