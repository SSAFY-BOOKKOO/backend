import { useEffect, useState } from 'react';
import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import CreateSelectButton from './CreateSelectButton';
import { getLibraryList } from '@services/Library';

const ShelfSelectStep = () => {
  const [bookData, setBookData] = useAtom(bookDataAtom);
  const [libraries, setLibraries] = useState([]);

  const handleLibraryGet = async () => {
    try {
      const libraryData = await getLibraryList();
      const shelves = libraryData.map(library => ({
        index: library.id,
        value: library.id,
        text: library.name,
      }));
      setLibraries(shelves);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    handleLibraryGet();
  }, []);

  const handleLibraryChange = libraryId => {
    setBookData(prev => ({ ...prev, libraryId }));
  };

  return (
    <div>
      <h2 className='mb-3 text-lg'>서재</h2>
      <CreateSelectButton
        options={libraries}
        selected={bookData.libraryId}
        setSelected={handleLibraryChange}
      />
    </div>
  );
};

export default ShelfSelectStep;
