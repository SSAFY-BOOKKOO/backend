import React, { useState, useEffect } from 'react';
import Button from '@components/@common/Button';
import { MdOutlineRefresh } from 'react-icons/md';
import { authAxiosInstance } from '@services/axiosInstance';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';

// 모달
const Modal = ({ show, onClose, review }) => {
  if (!show) {
    return null;
  }

  const navigate = useNavigate();

  const handleLibraryNavigation = () => {
    if (!review || !review.member) {
      console.error('Review or member is undefined');
      return;
    }
    const nickname = review.member.nickName;
    navigate('/library', { state: { nickname } });
  };

  return (
    <div className='fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-50'>
      <div className='bg-green-300 p-3 rounded-lg shadow-lg w-2/3 max-w-md'>
        <div className='flex justify-between items-center'>
          <div className='flex items-center'>
            <img
              src={review?.member?.profileImgUrl}
              alt='Profile'
              className='w-11 h-11 rounded-full mr-2'
            />
            <p className='mt-3 font-bold text-xl'>{review?.member?.nickName}</p>
          </div>
          <button onClick={onClose} className='pr-2 mt-2'>
            X
          </button>
        </div>
        <p>{review?.content || '리뷰 내용을 불러올 수 없습니다.'}</p>
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
  const { id, title, author, publisher, summary, coverImgUrl } = book;
  const [editReview, setEditingReview] = useState(false);
  const [reviewText, setReviewText] = useState('');
  const [surfingReviews, setSurfingReviews] = useState([]);
  const [rating, setRating] = useState(5);
  const [reviewId, setReviewId] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [currentReview, setCurrentReview] = useState(null);
  const [reviewContent, setReviewContent] = useState('');
  const [, showAlert] = useAtom(showAlertAtom);
  const titleMaxLength = 10;

  // 파도타기
  // 리뷰 처음 제시
  useEffect(() => {
    fetchReviews();
  }, [id]);

  useEffect(() => {
    handleConfirmReview();
  }, []);

  const fetchReviews = () => {
    const bookId = id;
    authAxiosInstance
      .get(`/books/${bookId}/reviews/surfing`, { params: { bookId } })
      .then(res => {
        setSurfingReviews(Array.isArray(res.data) ? res.data : []);
      })
      .catch(err => {
        console.log(err);
      });
  };

  // 새로고침
  const handleReviewRefresh = () => {
    fetchReviews();
  };

  // 책 정보 보기 버튼
  const handleContainerClick = e => {
    if (editReview) {
      e.stopPropagation();
    } else {
      onBackClick();
    }
  };

  // 한줄평 조회
  const handleConfirmReview = async () => {
    const bookId = id;
    await authAxiosInstance
      .get(`/books/${bookId}/review/me`)
      .then(res => {
        setReviewContent(res.data.content);
        setReviewId(res.data.id);
      })
      .catch(err => {});
  };

  // 내 한줄평 작성
  const handleSaveReview = async () => {
    setEditingReview(false);
    const bookId = id;
    await authAxiosInstance
      .post(`/books/${bookId}/reviews`, { content: reviewContent })
      .then(res => {
        setReviewContent(res.data.content);
        setReviewId(res.data.id);

        showAlert('한줄평이 작성되었습니다.', true, () => {});
      })
      .catch(err => {
        console.log('Error saving review:', err);
      });
  };

  // 내 한줄평 수정
  const handleUpdateReview = async () => {
    await authAxiosInstance
      .put(`/books/${id}/reviews/${reviewId}`, { content: reviewContent })
      .then(res => {
        setReviewContent(res.data.content);
        setReviewId(res.data.id);

        showAlert('한줄평이 수정되었습니다.', true, () => {});
      })
      .catch(err => {
        console.log('Error updating review:', err);
      });
  };

  // 한줄평 삭제 핸들러
  const handleDeleteReview = async () => {
    showAlert(
      '한줄평을 정말 삭제하시겠습니까?',
      false,
      async () => {
        const bookId = id;
        await authAxiosInstance
          .delete(`/books/${bookId}/reviews/${reviewId}`)
          .then(res => {
            setReviewContent('');
            setReviewId(null);
            showAlert('한줄평이 삭제되었습니다.', true, () => {});
          })
          .catch(err => {
            console.error('Error deleting review:', err);
          });
      },
      () => {}
    );
  };
  // 리뷰 더보기
  const handleMoreReview = review => {
    setCurrentReview(review);
    setShowModal(true);
  };

  const displayTitle =
    title.length > titleMaxLength
      ? title.substring(0, titleMaxLength - 1) + '...'
      : title;

  return (
    <div className='relative bg-zinc-300 rounded-lg w-10/12 max-w-md h-full flex flex-col justify-between overflow-auto'>
      <Alert />
      <div className='absolute right-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-10'></div>
      <div className='flex flex-col items-center p-4'>
        <h1 className='text-2xl font-bold m-4 pb-12 w-10/12 h-4 text-center text-overflow-2'>
          {displayTitle}
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
          <div key={index} className='flex items-center pb-0 pr-1 mr-4 w-10/12'>
            <div className='flex justify-between bg-white w-full p-2 mb-4 h-auto rounded-lg opacity-70'>
              <div
                className='flex items-center space-x-3 cursor-pointer'
                onClick={() => handleMoreReview(review)}
              >
                <img
                  src={review.member.profileImgUrl}
                  alt='Profile'
                  className='w-11 h-11 rounded-full mr-2'
                />
                <div>
                  <p className='font-bold'>{review.member.nickName}</p>
                  <p className='text-overflow-2'>{review.content}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className='pl-8 pt-5 bg-pink-500 rounded-b-md opacity-70 w-full h-[215px] flex-shrink-0'>
        <div className='relative bg-white w-10/12 h-44 rounded-lg opacity-70'>
          <textarea
            className='w-full h-44 p-2 bg-white border border-gray-400 rounded resize-none'
            value={reviewContent}
            placeholder='한줄평을 작성해보세요!'
            disabled={!editReview}
            onChange={e => setReviewContent(e.target.value)}
            onClick={e => e.stopPropagation()}
            maxLength='70'
          ></textarea>

          <div className='absolute top-36 right-2 flex space-x-2'>
            <Button
              text={editReview ? '저장' : reviewId ? '수정' : '작성'}
              size='small'
              color='text-black bg-rose-300'
              onClick={async e => {
                e.stopPropagation();
                if (editReview) {
                  reviewId ? handleUpdateReview() : handleSaveReview();
                  setEditingReview(false);
                } else {
                  setEditingReview(true);
                }
              }}
              className='px-3 py-1 rounded-md hover:bg-rose-400 transition duration-150'
            />
            {reviewId && (
              <Button
                text='삭제'
                size='small'
                color='text-black bg-rose-300'
                onClick={e => {
                  e.stopPropagation();
                  handleDeleteReview();
                }}
                className='px-3 py-1 rounded-md hover:bg-rose-400 transition duration-150'
              />
            )}
          </div>
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
