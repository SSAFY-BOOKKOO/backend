import React, { useEffect, useState } from 'react';
import { useAtom, useSetAtom } from 'jotai';
import { bookDataAtom } from '@atoms/bookCreateAtom';
import ShelfSelectButton from './ShelfSelectButton';
import { getLibraryList } from '@services/Library';
import { authAxiosInstance } from '@services/axiosInstance';
import { useNavigate } from 'react-router-dom';
import { alertAtom } from '@atoms/alertAtom';

const ShelfChange = ({ book, onClose }) => {
  const [bookData, setBookData] = useAtom(bookDataAtom);
  const [libraries, setLibraries] = useState([]);
  const setAlert = useSetAtom(alertAtom);
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

  const handleLibraryChange = async selectedLibraryId => {
    try {
      setBookData(prev => ({ ...prev, libraryId: selectedLibraryId }));

      await authAxiosInstance.patch(
        `/libraries/${bookData.libraryId}/books/${book.id}`,
        null,
        {
          params: { targetLibraryId: selectedLibraryId },
        }
      );

      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '책의 서재가 성공적으로 변경되었습니다!',
      });

      onClose();
      navigate('/');
    } catch (err) {
      console.error('library change err:', err);
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '책의 서재 변경에 실패했습니다. 다시 시도해 주세요.',
      });
    }
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
