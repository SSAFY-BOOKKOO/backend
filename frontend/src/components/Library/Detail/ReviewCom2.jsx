import React, { useState } from 'react';
import { AiFillStar } from 'react-icons/ai';
import Button from '../../@common/Button';
import { MdOutlineRefresh } from 'react-icons/md';
import { CgProfile } from 'react-icons/cg';

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
    <div className='w-10/12 max-w-md h-full flex flex-col'>
      {/* 회색 영역 */}
      <div className='relative bg-zinc-300 rounded-t-lg w-3/2 max-w-md h-full overflow-auto'>
        {/* 하드커버 선 */}
        <div className='absolute right-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-10'></div>
        {/* 회색 영역 */}
        <div className='flex flex-col items-center p-4'>
          <h1 className='text-2xl font-bold m-4 pb-12 w-10/12 h-4 text-center'>
            {title}
          </h1>

          <div className='flex justify-between items-center w-10/12 pb-4 pr-2'>
            <p className='flex items-center text-lg font-bold'>
              <p>추천사</p>
              <button onClick={handleReviewRefresh}>
                <MdOutlineRefresh className='text-2xl text-center' />
              </button>
            </p>
            <div className='flex space-x-0'>
              <button
                className='text-md bg-pink-500 rounded-lg py-1 px-2 mr-1'
                onClick={handleContainerClick}
              >
                책 정보
              </button>
            </div>
          </div>

          {/* 파도 탄 글 */}
          {reviews.map((review, index) => (
            <div
              key={index}
              className='flex items-center pb-2 pr-1 mr-4 w-10/12'
            >
              <div className='flex justify-between bg-white w-full p-2 mb-4 h-auto rounded-lg opacity-70'>
                <div className='flex items-center space-x-3'>
                  <CgProfile className='text-2xl mb-5' />
                  <div>
                    <p className='font-bold'>{review.nickname}</p>
                    <p>{review.text}</p>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* 한줄평 쓰기 - 띠지 영역(핑크) */}
        <div className='mt-12 pl-8 pt-5 bg-pink-500 rounded-b-md opacity-70 w-full h-[215px] relative'>
          <div className='relative bg-white w-10/12 h-44 rounded-lg opacity-70'>
            {editReview ? (
              <textarea
                className='w-full h-44 p-2 bg-white border border-gray-400 rounded resize-none'
                value={reviewText}
                onChange={e => setReviewText(e.target.value)}
                onClick={e => e.stopPropagation()} // textarea 클릭 시 이벤트 전파 막기
              ></textarea>
            ) : (
              <p className='w-full h-44 p-2 pb-4 border border-gray-400 rounded resize-none'>
                {reviewText || '한줄평을 작성해 보세요!'}
              </p>
            )}
            <Button
              text={editReview ? '저장' : '작성'}
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
              className='absolute top-36 right-2'
            ></Button>
          </div>
          {/* 하드커버 선 */}
          <div className='absolute right-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-700 z-10'></div>
        </div>
      </div>
    </div>
  );
};

export default ReviewCom;
