// src/components/Library/Detail/Review/ReviewCom.jsx
import { useState } from 'react';

const ReviewCom = ({ bookId, currentUserId = 1, onBackClick }) => {
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState('');
  const [editingReviewId, setEditingReviewId] = useState(null);
  const [editingReviewText, setEditingReviewText] = useState('');

  const handleAddReview = () => {
    if (newReview.trim()) {
      const newReviewObj = {
        id: reviews.length + 1,
        text: newReview,
        userId: currentUserId,
      };
      setReviews([...reviews, newReviewObj]);
      setNewReview('');
    }
  };

  const handleEditReview = (id, text) => {
    setReviews(reviews.map((review) => (review.id === id ? { ...review, text } : review)));
    setEditingReviewId(null);
    setEditingReviewText('');
  };

  const handleDeleteReview = (id) => {
    setReviews(reviews.filter((review) => review.id !== id));
  };

  const canEditOrDelete = (review) => review.userId === currentUserId;

  return (
    <div>
      <h1>한줄평</h1>
      {reviews.map((review) => (
        <div key={review.id}>
          {editingReviewId === review.id ? (
            <div>
              <input
                type="text"
                value={editingReviewText}
                onChange={(e) => setEditingReviewText(e.target.value)}
              />
              <button onClick={() => handleEditReview(review.id, editingReviewText)}>수정 완료</button>
            </div>
          ) : (
            <div>
              <p>{review.text}</p>
              {canEditOrDelete(review) && (
                <>
                  <button onClick={() => { setEditingReviewId(review.id); setEditingReviewText(review.text); }}>수정</button>
                  <button onClick={() => handleDeleteReview(review.id)}>삭제</button>
                </>
              )}
            </div>
          )}
        </div>
      ))}
      <div>
        <input
          type="text"
          value={newReview}
          onChange={(e) => setNewReview(e.target.value)}
          placeholder="한줄평 작성"
        />
        <button onClick={handleAddReview}>추가</button>
      </div>
      <button onClick={onBackClick}>책 정보 보기</button>
    </div>
  );
};

export default ReviewCom;
