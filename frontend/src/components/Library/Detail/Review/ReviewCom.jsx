// src/components/Library/Detail/Review/ReviewCom.jsx
import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import pencilIcon from '@assets/icons/pencil.png';
import Button from '../../../@common/Button';
import refreshIcon from '@assets/icons/refresh.png'; // 새로고침 아이콘 추가

const ReviewCom = ({ onBackClick }) => {
  // 책 제목 받아오기
  const { state } = useLocation();
  const { title } = state.book;
  const [editReview, setEditingReview] = useState(false);
  const [reviewText, setReviewText] = useState('');

  // 리뷰 받기+새로 고침
  const [reviews, setReviews] = useState([
    { nickname: '다람이', text: '한줄평~~~~~' },
    { nickname: '뚱이', text: '한줄평~~~~~' },
    { nickname: '징징이', text: '한줄평~~~~~' },
  ]);

  const handleSaveReview = () => {
    setEditingReview(false);
  };

  const handleReviewRefresh = () => {
    //임의로 새 데이터
    const newReviews = [
      { nickname: '라이언', text: '새로운 한줄평~~~' },
      { nickname: '어피치', text: '새로운 한줄평~~~' },
      { nickname: '무지', text: '새로운 한줄평~~~' },
    ];
    setReviews(newReviews);
  };

  return (
    <div className='flex flex-col items-center p-4 bg-yellow-100 rounded-lg w-10/12 max-w-md h-full'>
      <h1 className='text-2xl font-bold mb-4'>{title}</h1>
      <div
        className='relative bg-gray-200 w-72 p-4 h-48 rounded-lg opacity-70 cursor-pointer'
        onClick={onBackClick}
      >
        {editReview ? (
          <textarea
            className='w-full h-full p-2 border border-gray-400 rounded resize-none'
            // 수정할 때 저장한 글 보이게 하기
            value={reviewText}
            onChange={e => setReviewText(e.target.value)}
          ></textarea>
        ) : (
          <p className='w-full h-full p-2 border border-gray-400 rounded resize-none'>
            {reviewText || '한줄평을 작성해 보세요!'}
          </p>
        )}
        <Button
          text={editReview ? '저장' : ''}
          size='small'
          color='text-black bg-rose-300'
          onClick={e => {
            e.stopPropagation();
            if (editReview) {
              handleSaveReview();
            } else {
              setEditingReview(true);
            }
          }}
          className='absolute top-5 right-5'
        >
          {!editReview && (
            <img
              src={pencilIcon}
              alt='작성'
              style={{ width: '20px', height: '20px' }}
            />
          )}
        </Button>
      </div>
      <div className='flex justify-between items-center w-72 pt-12 pb-4'>
        <h1 className='text-2xl font-bold'>파도 타기</h1>
        <button onClick={handleReviewRefresh}>
          <img
            src={refreshIcon}
            alt='새로고침'
            style={{ width: '24px', height: '24px' }}
          />
        </button>
      </div>
      <div>
        {reviews.map((review, index) => (
          <div key={index} className='mb-2'>
            <div className='bg-gray-200 w-72 p-4 mb-14 h-auto rounded-lg opacity-70'>
              <p className='font-bold'>{review.nickname}</p>
              <p>{review.text}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ReviewCom;
