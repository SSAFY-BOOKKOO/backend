import React, { useEffect, useState } from 'react';
import { useAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import ShelfSelectButton from './ShelfSelectButton';
import { getLibraryList } from '@services/Library';
import { authAxiosInstance } from '@services/axiosInstance';
import { useNavigate } from 'react-router-dom';

const ShelfChange = ({ book, onClose }) => {
  const [bookData, setBookData] = useAtom(bookDataAtom);
  const [libraries, setLibraries] = useState([]);
  const navigate = useNavigate();

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

  const handleLibraryChange = selectedLibraryId => {
    setBookData(prev => ({ ...prev, libraryId: selectedLibraryId }));

    authAxiosInstance
      .patch(`/libraries/${bookData.libraryId}/books/${book.id}`, null, {
        params: { targetLibraryId: selectedLibraryId },
      })
      .then(res => {})
      .catch(err => {
        console.error('library change err:', err);
      });

    onClose();
    navigate('/');
  };

  return (
    <div>
      <h2 className='mb-3 text-lg'>서재</h2>
      <ShelfSelectButton
        options={libraries}
        selected={bookData.libraryId}
        setSelected={handleLibraryChange}
      />
    </div>
  );
};

export default ShelfChange;
