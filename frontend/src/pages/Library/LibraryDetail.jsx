import React, { useState, useEffect } from 'react';
import { AiFillStar } from 'react-icons/ai';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { CSSTransition, SwitchTransition } from 'react-transition-group';
import { useAtom, useSetAtom } from 'jotai';
import useModal from '@hooks/useModal';
import ReviewCom from '@components/Library/Detail/ReviewCom';
import ColorPicker from '@components/Library/BookCreate/ColorPicker';
import ShelfChange from '@components/Library/Detail/ShelfChange';
import SettingsModal from '@components/@common/SettingsModal';
import { PRESET_COLORS } from '@constants/ColorData';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import { IoBookmarkSharp } from 'react-icons/io5';
import { authAxiosInstance } from '@services/axiosInstance';
import { alertAtom } from '@atoms/alertAtom';
import Spinner from '@components/@common/Spinner';
import './LibraryDetail.css';

const LibraryDetail = () => {
  const { libraryId, bookId } = useParams();
  const location = useLocation();
  const [showReview, setShowReview] = useState(false);
  const [showColorPicker, setShowColorPicker] = useState(false);
  const [showShelfSelect, setShowShelfSelect] = useState(false);
  const [bookData, setBookData] = useAtom(bookDataAtom);
  const [selectedColor, setSelectedColor] = useState(bookData.bookColor);
  const [isLoading, setIsLoading] = useState(true);
  const setAlert = useSetAtom(alertAtom);
  const navigate = useNavigate();
  const maxLength = 80;
  const [book, setBook] = useState(null);
  const { isOpen, closeModal, toggleModal } = useModal();

  useEffect(() => {
    const fetchBookData = async () => {
      try {
        const response = await authAxiosInstance.get(
          `/libraries/${libraryId}/books/${bookId}?nickname=${location.state.nickname}`
        );
        const bookResponse = response.data;
        const book = response.data.book;
        setBook(book);

        setBookData({
          status: bookResponse.status || 'READ',
          startAt: bookResponse.startAt || '',
          endAt: bookResponse.endAt || '',
          rating: bookResponse.rating || 0,
          bookColor: bookResponse.bookColor || '',
          libraryId: libraryId,
        });
        setSelectedColor(bookResponse.bookColor || '');
      } catch (error) {
        console.error('Failed to fetch book data:', error);
      } finally {
        setIsLoading(false); // 데이터 로드가 완료되면 로딩 상태를 해제합니다.
      }
    };
    fetchBookData();
  }, [libraryId, bookId, location.state.nickname]);

  if (isLoading) {
    return (
      <div className='flex items-center justify-center min-h-screen'>
        <Spinner />
      </div>
    );
  }

  if (!book) {
    return <div>Loading...</div>;
  }

  const {
    title = '',
    author = '',
    publisher = '',
    publishedAt = '',
    summary = '',
    coverImgUrl,
  } = book;

  const handleDelete = () => {
    authAxiosInstance
      .delete(`/libraries/${libraryId}/books/${bookId}`, {
        params: { libraryId, bookId },
      })
      .then(res => {
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '책이 성공적으로 삭제되었습니다!',
        });
        navigate('/');
      })
      .catch(err => {
        console.error('Error deleting review:', err);
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '책 삭제에 실패했습니다. 다시 시도해 주세요.',
        });
      });
  };

  const handleColorChangeClick = () => {
    setShowColorPicker(true);
  };

  const handleColorChange = color => {
    setBookData(prev => ({ ...prev, bookColor: color }));
    authAxiosInstance
      .patch(`/libraries/${libraryId}/books/${bookId}`, null, {
        params: { bookColor: color },
      })
      .then(res => {
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '책의 색상이 성공적으로 변경되었습니다!',
        });
      })
      .catch(err => {
        console.error('color change err:', err);
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '책의 색상 변경에 실패했습니다. 다시 시도해 주세요.',
        });
      });
    setShowColorPicker(false);
  };

  const handleCloseColorPicker = () => {
    setShowColorPicker(false);
  };

  const handleShelfChangeClick = () => {
    setShowShelfSelect(true);
  };

  const handleCloseShelfSelect = () => {
    setShowShelfSelect(false);
  };

  const actions = [
    { label: '삭제', onClick: handleDelete },
    { label: '서재 이동', onClick: handleShelfChangeClick },
    { label: '색 변경', onClick: handleColorChangeClick },
  ];

  const displaySummary =
    summary.length > maxLength
      ? summary.substring(0, maxLength - 1) + '...'
      : summary;

  return (
    <div className='flex flex-col items-center h-[43rem] mt-4 overflow-hidden scrollbar-none'>
      <SwitchTransition>
        <CSSTransition
          key={showReview ? 'review' : 'details'}
          addEndListener={(node, done) => {
            node.addEventListener('transitionend', done, false);
          }}
          classNames='fade'
        >
          {showReview ? (
            <ReviewCom
              bookId={bookId}
              onBackClick={() => setShowReview(false)}
              book={book}
            />
          ) : (
            <div className='w-10/12 max-w-md h-full flex flex-col'>
              {/* 1. 회색 영역 */}
              <div className='relative bg-zinc-300 rounded-t-lg w-3/2 max-w-md h-4/5 flex flex-col justify-between overflow-auto'>
                {/* 하드커버 선 */}
                <div className='absolute left-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-30'></div>

                <div>
                  <div className='flex flex-col items-center'>
                    <div className='absolute w-11/12 h-96 pt-6 pr-7 pl-12 cursor-pointer rounded-lg mt-[2rem] z-0 flex justify-center'>
                      <img
                        src={coverImgUrl}
                        alt={title}
                        onClick={() => setShowReview(true)}
                        className=' w-full h-full'
                      />

                      <IoBookmarkSharp className='absolute top-0 right-14 flex items-center justify-center w-24 h-32 text-green-400 z-10' />
                      {bookData.status === 'READ' && (
                        <span
                          className='absolute flex flex-col items-center justify-center right-[5.8rem] top-8 text-black text-mb mb-5 font-bold z-20'
                          style={{
                            writingMode: 'vertical-rl',
                            textOrientation: 'upright',
                            letterSpacing: '-0.1em',
                          }}
                        >
                          읽음
                        </span>
                      )}
                      {bookData.status === 'READING' && (
                        <span
                          className='absolute right-[5.8rem] mt-1 text-black text-md mb-5 font-bold z-20'
                          style={{
                            writingMode: 'vertical-rl',
                            textOrientation: 'upright',
                            letterSpacing: '-0.1em',
                          }}
                        >
                          읽는중
                        </span>
                      )}
                      {bookData.status === 'DIB' && (
                        <span
                          className='absolute flex items-center justify-center right-[5.6rem] top-10 mt-1 text-black text-lg mb-5 font-bold z-20'
                          style={{
                            writingMode: 'vertical-rl',
                            textOrientation: 'upright',
                            letterSpacing: '-0.1em',
                          }}
                        >
                          찜
                        </span>
                      )}
                    </div>
                    <SettingsModal
                      isOpen={isOpen}
                      onClose={closeModal}
                      onToggle={toggleModal}
                      actions={actions}
                      className='z-50'
                    />
                  </div>
                </div>
                <p className='flex items-center justify-center mb-9'>
                  {bookData.startAt
                    ? `${bookData.startAt}~${bookData.endAt}`
                    : ''}
                </p>
              </div>

              {/* 2. 핑크 영역 (띠지) */}

              <div className='relative bg-pink-500 rounded-b-md opacity-70 w-full h-[215px]'>
                <div className='absolute left-6 top-0 bottom-0 w-1 bg-gray-800 z-10'></div>
                <div className='p-4 pl-14 pt-5'>
                  <div className='flex flex-grow'>
                    <h2 className='text-2xl text-black font-bold text-overflow-1'>
                      {title}
                    </h2>
                  </div>
                  <div className='flex space-x-1 mt-1'>
                    {Array(5).fill(<AiFillStar className='text-amber-300' />)}
                  </div>
                  <p className='text-lg text-black text-overflow-1'>{author}</p>
                  <p className='text-sm text-black'>
                    {publisher} | {publishedAt}
                  </p>
                  <p className='mt-2 text-sm text-black'>{displaySummary}</p>
                </div>
              </div>
            </div>
          )}
        </CSSTransition>
      </SwitchTransition>

      {showColorPicker && (
        <div className='fixed inset-0 flex items-center justify-center bg-black bg-opacity-75 z-20'>
          <div className='bg-white p-6 rounded-lg shadow-lg w-80 max-w-full'>
            <h2 className='text-xl font-bold mb-4'>책 색 변경</h2>
            <ColorPicker
              presetColors={PRESET_COLORS}
              selected={selectedColor}
              onChange={setSelectedColor}
            />
            <div className='flex justify-end mt-4'>
              <button
                onClick={() => {
                  handleColorChange(selectedColor);
                }}
                className='bg-green-400 text-white p-2 rounded-lg mr-2'
              >
                확인
              </button>
              <button
                onClick={handleCloseColorPicker}
                className='bg-gray-500 text-white p-2 rounded-lg'
              >
                취소
              </button>
            </div>
          </div>
        </div>
      )}

      {showShelfSelect && (
        <div className='fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50'>
          <div className='relative bg-white p-4 rounded-lg'>
            <button
              className='absolute top-2 right-2 text-xl font-bold'
              onClick={handleCloseShelfSelect}
            >
              &times;
            </button>
            <ShelfChange book={book} onClose={handleCloseShelfSelect} />
          </div>
        </div>
      )}
    </div>
  );
};

export default LibraryDetail;
