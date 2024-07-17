// src/pages/LibraryDetail.jsx
import React,{ useState } from 'react';

import { useLocation, useParams, useNavigate } from 'react-router-dom';
import Modal from '../components/Library/Detail/Modal/Modal';
import ReviewCom from '../components/Library/Detail/Review/ReviewCom';



const LibraryDetail = () => {
  const { state } = useLocation();
  const { id } = useParams();
  const [showReview, setShowReview] = useState(false);
  const navigate = useNavigate();


  // book 정보 받기
  const { title, author, publisher, year, summary, cover_url } = state.book;
  
  // 삭제 로직
  const handleDelete = (bookId) => {
    console.log({bookId})
    navigate('/', { state: { deleteBookId: bookId } });
  };

  return (
    <div className="flex flex-col items-center min-h-screen">
      {showReview ? (
        <ReviewCom
          bookId={id}
          onBackClick={() => setShowReview(false)}
        />
      ) : (
        <div className="p-4 bg-gray-200 rounded-lg w-10/12 h-screen max-w-md">
          {/* modal: 책 삭제, 서재 이동, 색 변경 */}
          <Modal bookId={id} onDelete={handleDelete}/>

          <div className=" flex flex-col items-center p-10">
            <img
              src={cover_url}
              alt={title}
              className="w-72 h-96 cursor-pointer rounded-md"
              onClick={() => setShowReview(true)}
            />
            <div className='mt-6 p-4 bg-white rounded-md '>
              <h2 className="mt-4 text-2xl font-bold">{title}</h2>
              <p className="mt-2 text-lg text-gray-700">{author}</p>
              <p className="mt-1 text-sm text-gray-500">{publisher}</p>
              <p className="mt-2 text-sm text-gray-500">{summary}</p>
              <p className="m-2">별점</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default LibraryDetail;
