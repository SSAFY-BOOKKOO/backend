import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import CreateSelectButton from './CreateSelectButton';

const ShelfSelectStep = () => {
  const [bookData, setBookData] = useAtom(bookDataAtom);

  const handleLibraryChange = libraryId => {
    setBookData(prev => ({ ...prev, libraryId }));
  };

  const shelves = [
    { index: 1, value: '1', text: '서재1' },
    { index: 2, value: '2', text: '서재2' },
    { index: 3, value: '3', text: '서재3' },
  ];

  return (
    <div>
      <h2 className='mb-3 text-lg'>서재</h2>
      <CreateSelectButton
        options={shelves}
        selected={bookData.libraryId}
        setSelected={handleLibraryChange}
      />
    </div>
  );
};

export default ShelfSelectStep;
