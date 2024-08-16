import { useAtom } from 'jotai';
import { modalStepAtom, bookDataAtom } from '@atoms/bookCreateAtom';
import ReadingStatusStep from './ReadingStatusStep';
import InfoStep from './InfoStep';
import ShelfSelectStep from './ShelfSelectStep';
import Button from '@components/@common/Button';
import { useEffect, useState } from 'react';
import { showAlertAtom } from '@atoms/alertAtom';
import IconButton from '@components/@common/IconButton';
import { IoCloseSharp } from 'react-icons/io5';
import Alert from '@components/@common/Alert';
import { postLibraryBook } from '@services/Library';

const BookCreateModal = ({
  isCreateModalOpen,
  toggleCreateModal,
  selectedBook,
}) => {
  const [step, setStep] = useState(1);
  const [bookData, setBookData] = useAtom(bookDataAtom);
  const [, showAlert] = useAtom(showAlertAtom);

  const handleNext = () => {
    if (step < 3) setStep(step + 1);
  };

  const handlePrevious = () => {
    if (step > 1) setStep(step - 1);
  };

  const reset = () => {
    setStep(1);
    setBookData({
      status: 'READ',
      startAt: '',
      endAt: '',
      rating: 0,
      bookColor: '',
      libraryId: 0,
    });

    toggleCreateModal();
  };

  const validateBookData = bookData => {
    if (bookData.status === 'READ') {
      if (
        !bookData.startAt ||
        !bookData.endAt ||
        !bookData.rating ||
        !bookData.bookColor ||
        bookData.libraryId === 0
      ) {
        return false;
      }
    } else if (bookData.status === 'READING') {
      if (
        !bookData.startAt ||
        !bookData.bookColor ||
        bookData.libraryId === 0
      ) {
        return false;
      }
    } else if (bookData.status === 'DIB') {
      if (!bookData.bookColor || bookData.libraryId === 0) {
        return false;
      }
    }
    return true;
  };

  const handleComplete = async () => {
    // 유효성 검사
    if (!validateBookData(bookData)) {
      showAlert('빈칸을 입력해주세요.', true, () => {
        // 확인
      });
      return;
    }
    const bodyData = {
      bookColor: bookData.bookColor,
      startAt: bookData.startAt,
      endAt: bookData.endAt,
      status: bookData.status,
      rating: bookData.rating,
      bookDto: selectedBook,
    };

    try {
      const data = await postLibraryBook(bookData.libraryId, bodyData);
      showAlert('책이 성공적으로 등록되었습니다.', true, () => {
        reset();
      });
    } catch (error) {
      if (error.response && error.response.status === 400) {
        showAlert('오류가 발생했습니다. 다시 시도해주세요.', true, () => {});
      } else {
        // 그 외의 에러
        showAlert('오류가 발생했습니다. 다시 시도해주세요.', true, () => {});
      }
    }
  };

  if (!isCreateModalOpen) return null;

  return (
    <div className='fixed inset-0 bg-black bg-opacity-50 z-50 flex items-end justify-center'>
      <Alert />
      <div className='bg-white w-full max-w-md rounded-t-3xl p-6 space-y-4 transform transition-transform duration-300 ease-out'>
        <div className='text-center font-semibold text-lg flex flex-row justify-between items-center'>
          책 등록 ({step}/3)
          <div className='flex flex-end'>
            <IconButton onClick={reset} icon={IoCloseSharp} />
          </div>
        </div>

        <div className='h-2/3 max-h-80 overflow-y-auto scrollbar-none'>
          {step === 1 && <ReadingStatusStep />}
          {step === 2 && <InfoStep />}
          {step === 3 && <ShelfSelectStep />}
        </div>
        <div className='flex justify-between pt-4'>
          {step > 1 ? (
            <Button onClick={handlePrevious}>이전</Button>
          ) : (
            <div></div>
          )}
          {step < 3 ? (
            <Button onClick={handleNext}>다음</Button>
          ) : (
            <Button onClick={handleComplete}>완료</Button>
          )}
        </div>
      </div>
    </div>
  );
};

export default BookCreateModal;
