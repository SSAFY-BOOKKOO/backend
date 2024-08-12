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
    setBookData(prev => ({ ...prev, startAt: date.target.value }));
  };

  const handleEndDateChange = date => {
    setBookData(prev => ({ ...prev, endAt: date.target.value }));
  };

  const handleRatingChange = rating => {
    setBookData(prev => ({ ...prev, rating }));
  };

  const handleColorChange = bookColor => {
    setBookData(prev => ({ ...prev, bookColor }));
  };

  // 달력

  return (
    <div>
      <div className='flex flex-col'>
        {bookData.status !== 'DIB' && (
          <>
            <h2 className='mb-3 text-lg'>시작 날짜</h2>
            <DatePicker
              onChange={handleStartDateChange}
              endDate={bookData.endAt}
            />
          </>
        )}
        {bookData.status === 'READ' && (
          <>
            <h2 className='my-3 text-lg'>종료 날짜</h2>
            <DatePicker
              onChange={handleEndDateChange}
              startDate={bookData.startAt}
            />
          </>
        )}
      </div>
      {bookData.status === 'READ' && (
        <>
          <h2 className='my-3 text-lg'>별점</h2>
          <StarRating
            selected={bookData.rating}
            onChange={handleRatingChange}
          />
        </>
      )}
      <h2 className='my-3 text-lg'>책 색상</h2>
      <ColorPicker
        presetColors={PRESET_COLORS}
        selected={bookData.bookColor}
        onChange={handleColorChange}
      />
    </div>
  );
};

export default InfoStep;
