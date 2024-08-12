import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import CreateSelectButton from './CreateSelectButton';
import { useEffect } from 'react';

const ReadingStatusStep = () => {
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const handleStatusChange = status => {
    setBookData(prev => ({ ...prev, status }));
  };

  const readingState = [
    { index: 1, value: 'READ', text: '읽음' },
    { index: 2, value: 'READING', text: '읽는 중' },
    { index: 3, value: 'DIB', text: '찜' },
  ];

  return (
    <div>
      <h2 className='mb-3 text-lg'>읽은 상태</h2>
      <CreateSelectButton
        options={readingState}
        selected={bookData.status}
        setSelected={handleStatusChange}
      />
    </div>
  );
};

export default ReadingStatusStep;
