// src/components/Library/Detail/Review/ReviewCom.jsx
import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import pencilIcon from '../../../../assets/pencil.png';
import Button from '../../../@common/Button';

const ReviewCom = () => {
  // 책 제목 받아오기
  const { state } = useLocation();
  const { title } = state.book;
  const [editReview, setEditingReview] = useState(false);
  const [reviewText, setReviewText] = useState('');

  const handleSaveReview = () => {
    setEditingReview(false);
  };

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-2xl font-bold mb-4">{title}</h1>
      <div className="relative bg-gray-200 w-96 p-4 h-48 rounded-lg">
        {editReview ? (
          <textarea
            className="w-full h-full p-2 border border-gray-400 rounded resize-none"
            // 수정할 때 저장한 글 보이게 하기
            value={reviewText}
            onChange={(e) => setReviewText(e.target.value)}
          ></textarea>
        ) : (
          <p>{reviewText || '한줄평을 작성해 보세요!'}</p>
        )}
        {/* <button
          onClick={() => {
            if (editReview) {
              handleSaveReview();
            } else {
              setEditingReview(true);
            }
          }}
          className="absolute top-2 right-2"
        >
          {editReview ? <Button/> : <img src={pencilIcon} alt="작성" style={{ width: '20px', height: '20px' }} />}
        </button> */}
         <Button
          text={editReview ? '저장' : '작성'}
          size="small"
          color="text-white bg-blue-500 active:bg-blue-600"
          onClick={() => {
            if (editReview) {
              handleSaveReview();
            } else {
              setEditingReview(true);
            }
          }}
          className="absolute top-2 right-2"
        >
          {!editReview && <img src={pencilIcon} alt="작성" style={{ width: '20px', height: '20px' }} />}
        </Button>
      </div>
      <p className="pt-20">파도 타기</p>
    </div>
  );
};

export default ReviewCom;
