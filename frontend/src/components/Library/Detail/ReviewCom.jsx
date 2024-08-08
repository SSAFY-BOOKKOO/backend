import React, { useState, useEffect } from 'react';
import { AiFillStar } from 'react-icons/ai';
import Button from '../../@common/Button';
import { MdOutlineRefresh } from 'react-icons/md';
import { CgProfile } from 'react-icons/cg';
import { authAxiosInstance } from '@services/axiosInstance';
import { useNavigate } from 'react-router-dom';

// 모달
const Modal = ({ show, onClose, review }) => {
  if (!show) {
    return null;
  }

  const navigate = useNavigate();

  const handleLibraryNavigation = () => {
    const nickname = review.member.nickName;
    navigate('/library', { state: { nickname } });
  };

  console.log('Modal Review:', review);

  return (
    <div className='fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-50'>
      <div className='bg-green-300 p-3 rounded-lg shadow-lg w-2/3 max-w-md'>
        <div className='flex justify-between items-center'>
          <div className='flex items-center'>
            <img
              src={review?.member?.profilImgUrl}
              alt='Profile'
              className='w-11 h-11 rounded-full mr-2'
            />
            <p className='mt-3 font-bold text-xl'>{review?.member?.nickName}</p>
          </div>
          <button onClick={onClose} className='pr-2 mt-2'>
            X
          </button>
        </div>
        <p>{review?.content}</p>
        <div className='flex justify-center items-center h-full'>
          <button
            onClick={handleLibraryNavigation}
            className='bg-pink-500 mt-4 p-3 rounded-lg text-center text-md font-bold'
          >
            서재 구경하러 가기
          </button>
        </div>
      </div>
    </div>
  );
};

const ReviewCom = ({ onBackClick, book }) => {
  const { id, title } = book;
  const [editReview, setEditingReview] = useState(false);
  const [reviewText, setReviewText] = useState('');
  const [surfingReviews, setSurfingReviews] = useState([]);
  const [rating, setRating] = useState(5); // Default rating, adjust as necessary
  const [reviewId, setReviewId] = useState(null); // Store the review ID
  const [showModal, setShowModal] = useState(false);
  const [currentReview, setCurrentReview] = useState(null);

  useEffect(() => {
    console.log(surfingReviews);
  }, [surfingReviews]);

  // 리뷰 처음 제시
  useEffect(() => {
    const bookId = id;
    authAxiosInstance
      .get(`/books/${bookId}/reviews/surfing`, { params: { bookId } })
      .then(res => {
        setSurfingReviews(Array.isArray(res.data) ? res.data : []);
        console.log(res.data);
      })
      .catch(err => {
        console.log(err);
      });
  }, [id]);

  // 새로고침
  const handleReviewRefresh = () => {
    const bookId = id;
    authAxiosInstance
      .get(`/books/${bookId}/reviews/surfing`, { params: { bookId } })
      .then(res => {
        setSurfingReviews(Array.isArray(res.data.book) ? res.data.book : []);
        console.log(res);
      })
      .catch(err => {
        console.log(err);
        console.log(bookId);
      });
  };

  // 책 정보 보기 버튼
  const handleContainerClick = e => {
    if (editReview) {
      e.stopPropagation();
    } else {
      onBackClick();
    }
  };

  // 내 한줄평 작성
  const handleSaveReview = () => {
    if (reviewText.length > 70) {
      alert('70자 이내로 작성해 주세요!');
      return;
    }

    setEditingReview(false);
    const bookId = id;
    authAxiosInstance
      .post(`/books/${bookId}/reviews`, { content: reviewText, rating: rating })
      .then(res => {
        console.log('리뷰 아이디: ', res.data.id);
        // reviewId 잘 들어감
        book.reviewId = res.data.id;
        console.log('book 객체!!:', book);
      })
      .catch(err => {
        console.log(err);
        console.log(bookId);
      });
  };

  // 리뷰 더보기
  const handleMoreReview = review => {
    console.log('Selected Review:', review); // review 객체 전체를 출력
    setCurrentReview(review);
    setShowModal(true);
    // const bookId = review.bookId;
    // const reviewId = review.id;
    // authAxiosInstance
    //   .get(`/books/${bookId}/reviews/${reviewId}`)
    //   .then(res => {
    //     console.log('Review Data:', res.data);
    //     setCurrentReview(res.data);
    //   })
    //   .catch(err => {
    //     console.log(err);
    //     console.log(bookId);
    //     console.log(id);
    //   });
  };

  return (
    <div className='relative bg-zinc-300 rounded-lg w-10/12 max-w-md h-full flex flex-col justify-between overflow-auto'>
      <div className='absolute right-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-10'></div>
      <div className='flex flex-col items-center p-4'>
        <h1 className='text-2xl font-bold m-4 pb-12 w-10/12 h-4 text-center text-overflow-1'>
          {title}
        </h1>

        <div className='flex justify-between items-center w-10/12 pb-4 pr-2'>
          <p className='flex items-center text-lg font-bold'>
            <span>추천사</span>
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

        {surfingReviews.map((review, index) => (
          <div key={index} className='flex items-center pb-2 pr-1 mr-4 w-10/12'>
            <div className='flex justify-between bg-white w-full p-2 mb-1 h-auto rounded-lg opacity-70'>
              <div
                className='flex items-center space-x-3 cursor-pointer'
                onClick={() => handleMoreReview(review)}
              >
                <img
                  src={review.member.profilImgUrl}
                  alt='Profile'
                  className='w-11 h-11 rounded-full mr-2'
                />
                <div>
                  <p className='font-bold text-overflow-1'>
                    {review.member.nickName}
                  </p>
                  <p className='text-overflow-2'>{review.content}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className='pl-8 pt-5 bg-pink-500 rounded-b-md opacity-70 w-full h-[215px] flex-shrink-0'>
        <div className='relative bg-white w-10/12 h-44 rounded-lg opacity-70'>
          {editReview ? (
            <textarea
              className='w-full h-44 p-2 bg-white border border-gray-400 rounded resize-none'
              value={reviewText}
              onChange={e => setReviewText(e.target.value)}
              onClick={e => e.stopPropagation()}
              maxLength={70}
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
              e.stopPropagation();
              if (editReview) {
                handleSaveReview();
              } else {
                setEditingReview(true);
              }
            }}
            className='absolute top-36 right-2'
          ></Button>
        </div>
        <div className='absolute right-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-700 z-10'></div>
      </div>

      <Modal
        show={showModal}
        onClose={() => setShowModal(false)}
        review={currentReview}
      />
    </div>
  );
};

export default ReviewCom;
