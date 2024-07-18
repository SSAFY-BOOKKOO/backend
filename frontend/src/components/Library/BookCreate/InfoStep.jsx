import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import StarRating from './StarRating';
import ColorPicker from './ColorPicker';
import { PRESET_COLORS } from '@constants/ColorData';
import { DayPicker } from 'react-day-picker';
import { format } from 'date-fns';
import 'react-day-picker/dist/style.css';
import { useState } from 'react';
import DatePicker from './DatePicker';

const InfoStep = () => {
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const handleStartDateChange = date => {
    setBookData(prev => ({ ...prev, startDate: date }));
  };

  const handleEndDateChange = date => {
    setBookData(prev => ({ ...prev, endDate: date }));
  };

  const handleRatingChange = rating => {
    setBookData(prev => ({ ...prev, rating }));
  };

  const handleColorChange = color => {
    setBookData(prev => ({ ...prev, color }));
  };

  // 달력

  return (
    <div>
      <div className='flex flex-col'>
        {bookData.status !== 'want' && (
          <>
            <label>시작 날짜</label>
            <DatePicker onChange={handleStartDateChange} />
          </>
        )}
        {bookData.status === 'read' && (
          <>
            <h2 className='mb-3 text-lg'>종료 날짜</h2>
            <DatePicker onChange={handleEndDateChange} />
          </>
        )}
      </div>

      {bookData.status !== 'want' && (
        <>
          <h2 className='mb-3 text-lg'>별점</h2>
          <StarRating
            selected={bookData.rating}
            onChange={handleRatingChange}
          />
        </>
      )}

      <h2 className='mb-3 text-lg'>책 색상</h2>
      <ColorPicker
        presetColors={PRESET_COLORS}
        selected={bookData.color}
        onChange={handleColorChange}
      />
    </div>
  );
};

export default InfoStep;
