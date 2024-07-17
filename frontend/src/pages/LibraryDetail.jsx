// src/pages/LibraryDetail.jsx
import React,{ useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import Modal from '../components/Library/Detail/Modal/Modal';
// import ReviewCom from '../components/Library/Detail/Review/ReviewCom';
import ReviewCom from '../components/Library/Detail/Review/ReviewCom';



const LibraryDetail = () => {
  const { state } = useLocation();
  const { id } = useParams();
  const [showReview, setShowReview] = useState(false);

  // book 정보 받기
  const { title, author, publisher, year, summary, cover_url } = state.book;

  return (
    <div className="flex flex-col items-center min-h-screen">
      {showReview ? (
        <ReviewCom
          bookId={id}
          onBackClick={() => setShowReview(false)}
        />
      ) : (
        <div className="p-4 bg-rose-100 rounded-lg w-10/12 h-screen max-w-md">
          {/* modal: 책 삭제, 서재 이동, 색 변경 */}
          <Modal />

          <div className="flex flex-col items-center p-10">
            <img
              src={cover_url}
              alt={title}
              className="w-48 h-72 cursor-pointer rounded-lg"
              onClick={() => setShowReview(true)}
            />
            <h2 className="mt-4 text-2xl font-bold">{title}</h2>
            <p className="mt-2 text-lg text-gray-700">{author}</p>
            <p className="mt-1 text-sm text-gray-500">{publisher}</p>
            <p className="mt-1 text-sm text-gray-500">{year}</p>
            <p className="mt-2 text-center text-gray-600">{summary}</p>
            <p>별점</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default LibraryDetail;
