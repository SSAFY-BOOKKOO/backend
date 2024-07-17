// src/components/Library/Detail/Review/ReviewCom.jsx

//src/assets/pencil.png
import React, {useState} from 'react';
import { useLocation } from 'react-router-dom';
import pencilIcon from '../../../../assets/pencil.png';



const ReviewCom=()=>{
  // 책 제목 받아오기
  const { state } = useLocation();
  const { title } = state.book;
  const [editReview, setEditingReview]=useState(false)
  const [reviewText, setReviewText]=useState('');

  const handleSaveReview=()=>{
    setEditingReview(false)
  }

  return(
    <div>
      <h1>{title}</h1>
      <div className='bg-gray-200'>
        {editReview ? (
          <textarea
            // 수정할 때 저장한 글 보이게 하기 -> value
            value={reviewText}
            onChange={(e)=>setReviewText(e.target.value)}
          ></textarea>
        ) : (
          <p>{reviewText || '수정 버튼을 누르세요'}</p>
        )}
      </div>
      <button 
        onClick={()=>{
          if (editReview) {
            handleSaveReview()
          } else {
            setEditingReview(true);
          }
        }}>
        {editReview? '저장': <img src={pencilIcon} alt="수정" style={{ width: '20px', height: '20px' }}/>}
      </button>
    </div>
  )
}

export default ReviewCom