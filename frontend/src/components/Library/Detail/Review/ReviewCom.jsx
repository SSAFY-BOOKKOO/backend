import React, { useState } from 'react';
import { AiFillStar } from 'react-icons/ai';
import pencilIcon from '@assets/icons/pencil.png';
import Button from '../../../@common/Button';
import refreshIcon from '@assets/icons/refresh.png'; // 새로고침 아이콘 추가

const ReviewCom = ({ onBackClick, book }) => {
  const { title, author, publisher, summary, cover_img_url } = book;
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
    // 임의로 새 데이터
    const newReviews = [
      { nickname: '라이언', text: '새로운 한줄평~~~' },
      { nickname: '어피치', text: '새로운 한줄평~~~' },
      { nickname: '무지', text: '새로운 한줄평~~~' },
    ];
    setReviews(newReviews);
  };

  const handleContainerClick = e => {
    if (editReview) {
      e.stopPropagation(); // editReview 상태에서는 클릭 이벤트 막기
    } else {
      onBackClick();
    }
  };

  return (
    // 전체 담는 틀
    <div className='relative bg-zinc-300 rounded-lg w-10/12 max-w-md h-full overflow-auto'>
      {/* 하드커버 선 */}
      <div className='absolute right-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-10'></div>

      <div className='flex flex-col items-center p-4 pr-4 '>
        <h1 className='text-3xl font-bold m-4 pb-4'>{title}</h1>
        {/* 회색 영역 */}
        <div className='flex justify-between items-center w-72 pb-4'>
          <h3 className='text-lg font-bold'>추천사</h3>
          <button onClick={handleReviewRefresh}>
            <img src={refreshIcon} alt='새로고침' className='h-6 w-6' />
          </button>
        </div>

        {/* 파도 탄 글 */}
        {reviews.map((review, index) => (
          <div key={index} className='mb-2 pr-5'>
            <div className='bg-white w-72 p-2  mb-4 h-auto rounded-lg opacity-70'>
              <p className='font-bold'>{review.nickname}</p>
              <p>{review.text}</p>
            </div>
          </div>
        ))}
      </div>

      {/* 한줄평 쓰기 - 띠지 영역(핑크) */}
      <div className='mt-12 pl-8 pt-5 bg-pink-500 rounded-b-md opacity-70 w-full  h-[215px]'>
        <div
          className='relative bg-white w-72 h-44 rounded-lg opacity-70 cursor-pointer'
          onClick={handleContainerClick}
        >
          {editReview ? (
            <textarea
              className='w-72 h-44 p-2 bg-white border border-gray-400 rounded resize-none'
              value={reviewText}
              onChange={e => setReviewText(e.target.value)}
              onClick={e => e.stopPropagation()} // textarea 클릭 시 이벤트 전파 막기
            ></textarea>
          ) : (
            <p className='w-72 h-44 p-2 border border-gray-400 rounded resize-none'>
              {reviewText || '한줄평을 작성해 보세요!'}
            </p>
          )}
          <Button
            text={editReview ? '저장' : ''}
            size='small'
            color='text-black bg-rose-300'
            onClick={e => {
              e.stopPropagation(); // 버튼 클릭 시 이벤트 전파 막기
              if (editReview) {
                handleSaveReview();
              } else {
                setEditingReview(true);
              }
            }}
            className='absolute top-2 right-2'
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
        {/* 하드커버 선 */}

        {/* <div className='absolute shadow-2xl	right-6 top-0 bottom-0 w-1 bg-gray-500'></div> */}
      </div>
    </div>
  );
};

export default ReviewCom;
