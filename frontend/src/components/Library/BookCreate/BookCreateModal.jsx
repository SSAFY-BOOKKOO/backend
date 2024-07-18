import { useAtom } from 'jotai';
import { useResetAtom } from 'jotai/utils';
import { modalStepAtom, bookDataAtom } from '@atoms/bookCreateAtom';
import ReadingStatusStep from './ReadingStatusStep';
import InfoStep from './InfoStep';
import ShelfSelectStep from './ShelfSelectStep';
import Button from '../../@common/Button';
import { useEffect, useState } from 'react';

const BookCreateModal = ({ isCreateModalOpen, toggleCreateModal }) => {
  const [step, setStep] = useState(1);
  const [bookData, setBookData] = useAtom(bookDataAtom);

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
      status: 'reading',
      startDate: '',
      endDate: '',
      rating: 0,
      color: '',
      library: 'library1',
    });
  };

  const handleComplete = () => {
    reset();

    // 등록 서버 연동
  };

  if (!isCreateModalOpen) return null;

  return (
    <div className='fixed inset-0 bg-black bg-opacity-50 z-50 flex items-end justify-center'>
      <div className='bg-white w-full rounded-t-3xl p-6 space-y-4 transform transition-transform duration-300 ease-out'>
        <div className='text-center font-semibold text-lg flex flex-row justify-between'>
          책 등록 ({step}/3)
          <div className='flex flex-end'>
            <Button onClick={reset}>닫기</Button>
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
