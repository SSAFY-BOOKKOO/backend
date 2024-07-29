import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import CreateSelectButton from './CreateSelectButton';

const ShelfSelectStep = ({ onClose }) => {
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const handleLibraryChange = library => {
    setBookData(prev => ({ ...prev, library }));
    onClose(); // 서가 선택 후 모달 닫기
  };

  const shelves = [
    { index: 1, value: 'library1', text: '서재1' },
    { index: 2, value: 'library2', text: '서재2' },
    { index: 3, value: 'library3', text: '서재3' },
  ];

  return (
    <div>
      <h2 className='mb-3 text-lg'>서재</h2>
      <CreateSelectButton
        options={shelves}
        selected={bookData.library}
        setSelected={handleLibraryChange}
      />
    </div>
  );
};

export default ShelfSelectStep;
