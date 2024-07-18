import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import StarRating from './StarRating';
import ColorPicker from './ColorPicker';
import { PRESET_COLORS } from '@constants/ColorData';

const InfoStep = () => {
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const handleStartDateChange = e => {
    setBookData(prev => ({ ...prev, startDate: e.target.value }));
  };

  const handleEndDateChange = e => {
    setBookData(prev => ({ ...prev, endDate: e.target.value }));
  };

  const handleRatingChange = rating => {
    setBookData(prev => ({ ...prev, rating }));
  };

  const handleColorChange = color => {
    setBookData(prev => ({ ...prev, color }));
  };

  return (
    <div>
      <div className='flex flex-col'>
        {bookData.status !== 'want' && (
          <>
            <label>시작 날짜</label>
            <input
              type='date'
              value={bookData.startDate}
              onChange={handleStartDateChange}
            />
          </>
        )}
        {bookData.status === 'read' && (
          <>
            <label>종료 날짜</label>
            <input
              type='date'
              value={bookData.endDate}
              onChange={handleEndDateChange}
            />
          </>
        )}
      </div>

      {bookData.status !== 'want' && (
        <>
          <p>별점</p>
          <StarRating
            selected={bookData.rating}
            onChange={handleRatingChange}
          />
        </>
      )}

      <p>책 색상</p>
      <ColorPicker
        presetColors={PRESET_COLORS}
        selected={bookData.color}
        onChange={handleColorChange}
      />
    </div>
  );
};

export default InfoStep;
