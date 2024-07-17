// src/pages/LibraryDetail.jsx
import { useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import Modal from '../components/Library/Detail/Modal/Modal';
import ReviewCom from '../components/Library/Detail/Review/ReviewCom';

const LibraryDetail = () => {
  const { state } = useLocation();
  const { id } = useParams();
  const [showReview, setShowReview] = useState(false);

  // book 정보 받기
  const { title, author, publisher, year, summary, cover_url } = state.book;

  //book 정보 출력 
  return (
    <div>
      {showReview ? (
        <ReviewCom
          bookId={id}
          onBackClick={() => setShowReview(false)}
        />
      ) : (
        <div>
          {/* modal: 책 삭제, 서재 이동, 색 변경 */}
          <Modal />

          <div>
            <img 
              src={cover_url} alt={title} 
              style={{ width: '200px', height: '300px', cursor: 'pointer' }} 
              onClick={() => setShowReview(true)}
            />
            <h2>{title}</h2>
            <p>{author}</p>
            <p>{publisher}</p>
            <p>{year}</p>
            <p>{summary}</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default LibraryDetail;
