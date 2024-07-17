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
      <div className='bg-gray-200 w-96 p-4 h-48'>
        {editReview ? (
          <textarea
            className="w-full h-full p-2 border border-gray-400 rounded resize-none"
            // 수정할 때 저장한 글 보이게 하기 
            value={reviewText}
            onChange={(e)=>setReviewText(e.target.value)}
          ></textarea>
        ) : (
          <p>{reviewText || '한줄평을 작성해 보세요!'}</p>
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
        {editReview? '저장': <img src={pencilIcon} alt="작성" style={{ width: '20px', height: '20px' }}/>}
      </button>
        
      <p className='pt-20'>파도 타기</p>
    </div>
  )
}

export default ReviewCom