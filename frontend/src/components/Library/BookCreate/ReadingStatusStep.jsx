import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import CreateSelectButton from './CreateSelectButton';

const ReadingStatusStep = () => {
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const handleStatusChange = status => {
    setBookData(prev => ({ ...prev, status }));
  };

  const readingState = [
    { index: 1, value: 'reading', text: '읽는 중' },
    { index: 2, value: 'read', text: '읽음' },
    { index: 3, value: 'want', text: '찜' },
  ];

  return (
    <div>
      <h2>읽은 상태</h2>
      <CreateSelectButton
        options={readingState}
        selected={bookData.status}
        setSelected={handleStatusChange}
      />
    </div>
  );
};

export default ReadingStatusStep;
