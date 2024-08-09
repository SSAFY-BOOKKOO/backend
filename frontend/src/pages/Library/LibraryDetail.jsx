import React, { useState, useEffect } from 'react';
import { AiFillStar } from 'react-icons/ai';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { CSSTransition, SwitchTransition } from 'react-transition-group';
import { useAtom } from 'jotai';
import useModal from '@hooks/useModal';
import ReviewCom from '@components/Library/Detail/ReviewCom';
import ColorPicker from '@components/Library/BookCreate/ColorPicker';
import ShelfSelectStep from '@components/Library/BookCreate/ShelfSelectStep';
import SettingsModal from '@components/@common/SettingsModal';
import { PRESET_COLORS } from '@constants/ColorData';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import { IoBookmarkSharp } from 'react-icons/io5';
import { authAxiosInstance } from '@services/axiosInstance';
import './LibraryDetail.css';

const LibraryDetail = () => {
  const { libraryId, bookId } = useParams();
  const location = useLocation();
  const [showReview, setShowReview] = useState(false);
  const [showColorPicker, setShowColorPicker] = useState(false);
  const [showShelfSelect, setShowShelfSelect] = useState(false);
  const [bookData, setBookData] = useAtom(bookDataAtom); // jotai로 받은 북 데이터
  const navigate = useNavigate();
  const maxLength = 80;
  // const authorMaxLength = 14;
  // const titleMaxLength = 10;
  const [book, setBook] = useState(null); // api로 받은 북데이터
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

        console.log('bookResponse:', bookResponse);
        // console.log(book);
        setBookData({
          status: bookResponse.status || 'READ',
          startAt: bookResponse.startAt || '',
          endAt: bookResponse.endAt || '',
          rating: bookResponse.rating || 0,
          bookColor: bookResponse.bookColor || '',
          libraryId: libraryId,
        });
      } catch (error) {
        console.log(location.state);
        console.error('Failed to fetch book data:', error);
      }
    };
    fetchBookData();
  }, [libraryId, bookId, location.state.nickname]);

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
    navigate('/library', { state: { deleteBookId: bookId } });
  };

  const handleColorChangeClick = () => {
    setShowColorPicker(true);
  };

  const handleColorChange = color => {
    setBookData(prev => ({ ...prev, color }));
    console.log(bookData);
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
            // 앞면
            <div className='w-10/12 max-w-md h-full flex flex-col'>
              {/* 1. 회색 영역 */}
              <div className='relative bg-zinc-300 rounded-t-lg w-3/2 max-w-md h-4/5 flex flex-col justify-between overflow-auto'>
                {/* 하드커버 선 */}
                <div className='absolute left-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-30'></div>

                <div>
                  <div className='flex flex-col items-center'>
                    <div className=' w-11/12 h-96 pt-6 pr-7 pl-12 cursor-pointer rounded-lg mt-[2rem] z-0 flex justify-center'>
                      <img
                        src={coverImgUrl}
                        alt={title}
                        onClick={() => setShowReview(true)}
                        className='relative w-full h-full'
                      />

                      <IoBookmarkSharp className='absolute top-5 right-16 flex items-center justify-center w-16 h-32 text-blue-500 z-10' />
                      {bookData.status === 'READ' && (
                        <span
                          className='absolute flex flex-col items-center justify-center right-[5.5rem] text-black text-xs mb-5 font-bold z-20'
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
                          className='absolute right-[5.5rem] text-black text-xs mb-5 font-bold z-20'
                          style={{
                            writingMode: 'vertical-rl',
                            textOrientation: 'upright',
                            letterSpacing: '-0.23em',
                          }}
                        >
                          읽는중
                        </span>
                      )}
                      {bookData.status === 'DIB' && (
                        <span
                          className='absolute flex items-center justify-center right-[5.5rem] text-black text-xs mb-5 font-bold z-20'
                          style={{
                            writingMode: 'vertical-rl',
                            textOrientation: 'upright',
                            letterSpacing: '-0.23em',
                          }}
                        >
                          찜
                        </span>
                      )}
                    </div>
                    {/* 모달 */}
                    <SettingsModal
                      isOpen={isOpen}
                      onClose={closeModal}
                      onToggle={toggleModal}
                      actions={actions}
                      className='z-50'
                    />
                    {/* 읽은 기간 로직 (수정예정) */}
                    <p className='m-2 pl-3'>
                      {bookData.startAt
                        ? `${bookData.startAt}~${bookData.endAt}`
                        : ''}
                    </p>
                  </div>
                </div>
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
        <div className='fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50'>
          <div className='relative bg-white p-4 rounded-lg'>
            <button
              className='absolute top-0 right-2 text-xl font-bold'
              onClick={handleCloseColorPicker}
            >
              &times;
            </button>
            <ColorPicker
              presetColors={PRESET_COLORS}
              // selected={bookData.color}
              onChange={handleColorChange}
            />
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
            <ShelfSelectStep onClose={handleCloseShelfSelect} />
          </div>
        </div>
      )}
    </div>
  );
};

export default LibraryDetail;
