import { useAtom } from 'jotai';
import { useResetAtom } from 'jotai/utils';
import { modalStepAtom, bookDataAtom } from '@atoms/bookCreateAtom';
import ReadingStatusStep from './ReadingStatusStep';
import InfoStep from './InfoStep';
import ShelfSelectStep from './ShelfSelectStep';
import Button from '../../@common/Button';
import { useEffect, useState } from 'react';
import { showAlertAtom } from '@atoms/alertAtom';
import IconButton from '@components/@common/IconButton';
import { IoCloseSharp } from 'react-icons/io5';
import Alert from '../../@common/Alert';

const BookCreateModal = ({ isCreateModalOpen, toggleCreateModal }) => {
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
    toggleCreateModal();
    setStep(1);
    setBookData({
      status: 'read',
      startDate: '',
      endDate: '',
      rating: 0,
      color: '',
      library_id: 1,
      library: 'library1',
    });
  };

  const handleShowAlert = () => {
    showAlert(
      '빈칸을 입력해주세요.',
      false,
      () => {
        console.log('확인');
        reset();
      },
      () => {
        console.log('취소');
        reset();
      }
    );
  };

  const handleComplete = () => {
    // 유효성 검사
    if (bookData.color === '') {
      handleShowAlert();
    } else {
      reset();
    }

    // 등록 서버 연동
    reset();
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
