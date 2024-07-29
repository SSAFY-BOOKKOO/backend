import React, { useState } from 'react';
import { AiFillStar } from 'react-icons/ai';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import { CSSTransition, SwitchTransition } from 'react-transition-group';
import { useAtom } from 'jotai';
import Modal from '@components/Library/Detail/Modal/Modal';
import ReviewCom from '@components/Library/Detail/Review/ReviewCom';
import ColorPicker from '@components/Library/BookCreate/ColorPicker';
import ShelfSelectStep from '@components/Library/BookCreate/ShelfSelectStep';
import { PRESET_COLORS } from '@constants/ColorData';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import './LibraryDetail.css'; // CSS 파일 추가

const LibraryDetail = () => {
  const { state } = useLocation();
  const { id } = useParams();
  const [showReview, setShowReview] = useState(false);
  const [showColorPicker, setShowColorPicker] = useState(false);
  const [showShelfSelect, setShowShelfSelect] = useState(false);
  const [bookData, setBookData] = useAtom(bookDataAtom);
  const navigate = useNavigate();
  const maxLength = 100;

  // book 정보 받기
  const { title, author, publisher, summary, cover_img_url } = state.book;

  // 삭제 로직
  const handleDelete = bookId => {
    console.log({ bookId });
    navigate('/library', { state: { deleteBookId: bookId } });
  };

  // 서가 색 변경 로직
  const handleColorChangeClick = () => {
    setShowColorPicker(true);
  };

  const handleColorChange = color => {
    setBookData(prev => ({ ...prev, color }));
    setShowColorPicker(false);
  };

  const handleCloseColorPicker = () => {
    setShowColorPicker(false);
  };

  // 서가 변경 로직
  const handleShelfChangeClick = () => {
    setShowShelfSelect(true);
  };

  const handleCloseShelfSelect = () => {
    setShowShelfSelect(false);
  };

  // 요약 텍스트 길이 조정
  const displaySummary =
    summary.length > maxLength
      ? summary.substring(0, maxLength - 1) + '...'
      : summary;

  return (
    <div className='flex flex-col items-center h-[43rem] mt-4 overflow-hidden scrollbar-none'>
      <SwitchTransition>
        {/* 뒷면 넘어가는 애니메이션 */}
        <CSSTransition
          key={showReview ? 'review' : 'details'}
          addEndListener={(node, done) => {
            node.addEventListener('transitionend', done, false);
          }}
          classNames='fade'
        >
          {showReview ? (
            <ReviewCom
              bookId={id}
              onBackClick={() => setShowReview(false)}
              book={state.book}
            />
          ) : (
            // 책 큰 틀
            <div className='relative bg-zinc-300 rounded-lg w-10/12 max-w-md h-full overflow-auto'>
              <Modal
                bookId={id}
                onDelete={handleDelete}
                onColorChange={handleColorChangeClick}
                onShelfChange={handleShelfChangeClick}
              />
              <div className='flex flex-col items-center p-8 pl-12'>
                <div className='absolute left-6 top-0 bottom-0 shadow-2xl w-1 bg-gray-500 shadow-2xl z-10'></div>
                <img
                  src={cover_img_url}
                  alt={title}
                  className='w-72 h-96 cursor-pointer rounded-lg shadow-xl'
                  onClick={() => setShowReview(true)}
                />
                {/* <button>전환</button> */}
              </div>
              {/* 띠지 부분 */}
              <div className='mt-6 p-4 pl-14 bg-pink-500 rounded-b-md opacity-70 w-full h-[215px]'>
                <h2 className='text-2xl text-black font-bold'>{title}</h2>
                <div className='flex space-x-1'>
                  {Array(5).fill(<AiFillStar className='text-amber-300' />)}
                </div>
                <p className='text-lg text-black'>{author}</p>
                <p className='text-sm text-black'>{publisher}</p>
                <p className='mt-2 text-sm text-black'>{displaySummary}</p>
              </div>
            </div>
          )}
        </CSSTransition>
      </SwitchTransition>

      {/* 색 변경 */}
      {showColorPicker && (
        <div className='fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50'>
          <div className='relative bg-white p-4 rounded-lg'>
            <button
              className='absolute top-2 right-2 text-xl font-bold'
              onClick={handleCloseColorPicker}
            >
              &times;
            </button>
            <ColorPicker
              presetColors={PRESET_COLORS}
              selected={bookData.color}
              onChange={handleColorChange}
            />
          </div>
        </div>
      )}

      {/* 서가 선택 */}
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
