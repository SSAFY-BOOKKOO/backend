import React, { useState } from 'react';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { CSSTransition, SwitchTransition } from 'react-transition-group';
import Modal from '../components/Library/Detail/Modal/Modal';
import ReviewCom from '../components/Library/Detail/Review/ReviewCom';
import './LibraryDetail.css'; // CSS 파일 추가

const LibraryDetail = () => {
  const { state } = useLocation();
  const { id } = useParams();
  const [showReview, setShowReview] = useState(false);
  const navigate = useNavigate();

  // book 정보 받기
  const { title, author, publisher, year, summary, cover_url } = state.book;
  
  // 삭제 로직
  const handleDelete = (bookId) => {
    console.log({ bookId });
    navigate('/', { state: { deleteBookId: bookId } });
  };

  return (
    // 큰 틀
    <div className="flex flex-col items-center min-h-screen overflow-hidden">
      <SwitchTransition>
        <CSSTransition
          key={showReview ? 'review' : 'details'}
          addEndListener={(node, done) => {
            node.addEventListener('transitionend', done, false);
          }}
          classNames="fade"
        >
          {showReview ? (
              <ReviewCom
                bookId={id}
                onBackClick={() => setShowReview(false)
                }
              />
          ) : (
            // 책 담는 틀
            <div className="p-4 bg-yellow-100 rounded-lg w-10/12 max-w-md h-full overflow-auto">
              {/* modal: 책 삭제, 서재 이동, 색 변경 */}
              <Modal bookId={id} onDelete={handleDelete} />

              <div className="flex flex-col items-center p-10">
                <img
                  src={cover_url}
                  alt={title}
                  className="w-72 h-96 cursor-pointer rounded-md"
                  onClick={() => setShowReview(true)}
                />
                <div className="mt-6 p-4 bg-white rounded-md opacity-70	">
                  <h2 className="mt-4 text-2xl font-bold">{title}</h2>
                  <div className="flex items-center space-x-4">
                    <p className="text-lg text-gray-700">{author}</p>
                    <p className="text-sm text-gray-500">{publisher}</p>
                  </div>
                  <p className="mt-2 text-sm text-gray-500">{summary}</p>
                  <p className="mt-2">별점 영역</p>
                </div>
              </div>
            </div>
          )}
        </CSSTransition>
      </SwitchTransition>
    </div>
  );
};

export default LibraryDetail;
