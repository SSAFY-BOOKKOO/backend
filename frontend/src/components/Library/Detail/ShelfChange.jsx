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
    console.log('book', book);
    console.log('bookData', bookData);
    handleLibraryGet();
  }, []);

  // const handleLibraryChange = async selectedLibraryId => {
  //   try {
  //     console.log('Selected Library ID:', selectedLibraryId); // 선택한 서재 ID 확인
  //     console.log('book.id:', book.id);

  //     // bookData 상태 업데이트
  //     setBookData(prev => ({ ...prev, libraryId: selectedLibraryId }));

  //     // 상태 업데이트 후 API 호출
  //     const response = await authAxiosInstance.patch(
  //       `/libraries/${selectedLibraryId}/books/${book.id}`,
  //       null,
  //       {
  //         params: { targetLibraryId: selectedLibraryId },
  //       }
  //     );
  //     console.log('Library change response:', response);

  //     onClose(); // 서재 이동 후 창 닫기
  //     navigate('/'); // 성공적으로 변경 후 메인으로 이동
  //   } catch (err) {
  //     console.error('Error during library change:', err); // 오류 메시지 강제 출력
  //     console.log('Library ID during error:', selectedLibraryId);
  //   }
  // };

  const handleLibraryChange = selectedLibraryId => {
    // bookData 상태 업데이트
    setBookData(prev => ({ ...prev, libraryId: selectedLibraryId }));
    // console.log('libarary Id:', bookData.libraryId);
    // console.log('Selected Library ID:', selectedLibraryId); // 선택한 서재 ID 확인
    // console.log('book.id:', book.id);

    // 상태 업데이트 후 API 호출
    authAxiosInstance
      .patch(`/libraries/${bookData.libraryId}/books/${book.id}`, null, {
        params: { targetLibraryId: selectedLibraryId },
      })
      .then(res => {
        console.log('library change:', res);
        // console.log('Selected Library ID:', selectedLibraryId); // 선택한 서재 ID 확인
      })
      .catch(err => {
        console.error('library change err:', err);
        // console.log('Selected Library ID:', selectedLibraryId); // 선택한 서재 ID 확인
      });
    // console.log('Library change response:', response);

    onClose(); // 서재 이동 후 창 닫기
    navigate('/'); // 성공적으로 변경 후 메인으로 이동
  };

  return (
    <div>
      <h2 className='mb-3 text-lg'>서재</h2>
      <ShelfSelectButton
        options={libraries}
        selected={bookData.libraryId}
        setSelected={handleLibraryChange} // 선택한 서재 ID를 handleLibraryChange에 전달
      />
    </div>
  );
};

export default ShelfChange;