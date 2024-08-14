import { useEffect, useState } from 'react';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import useModal from '@hooks/useModal';
import { getAladinBookByIsbn } from '@services/Book';
import { useLocation, useParams } from 'react-router-dom';
import { useAtom } from 'jotai';
import { showAlertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';
import Spinner from '@components/@common/Spinner';

const SearchBookDetail = () => {
  const { isOpen, toggleModal } = useModal();
  const location = useLocation();
  const { bookData } = location.state || {};
  const { bookId } = useParams();
  const [book, setBook] = useState([]);
  const [loading, setLoading] = useState(false);
  const [, showAlert] = useAtom(showAlertAtom);

  const handleBookCreateButton = () => {
    if (bookData?.inLibrary) {
      showAlert('이미 등록된 책입니다.', true, () => {});
    } else {
      toggleModal();
    }
  };

  const handleGetBook = async () => {
    setLoading(true);

    try {
      const bookData = await getAladinBookByIsbn(bookId);
      setBook(bookData);
    } catch (error) {
      console.error('error', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    handleGetBook();
  }, [bookId]);

  return (
    <WrapContainer>
      <Alert />
      {loading ? (
        <div className='flex justify-center items-center h-screen'>
          <Spinner />
        </div>
      ) : (
        <div className=''>
          <div className='h-96 w-full flex justify-center my-3'>
            <img
              src={book?.coverImgUrl}
              className='max-h-full max-w-full object-contain rounded-lg'
              alt={book?.title}
            />
          </div>
          <p className='text-overflow-2 text-xl font-semibold'>{book?.title}</p>
          <p className='text-base text-gray-600'>{book?.author}</p>
          <p className='text-base text-gray-600'>
            {book?.publisher} | {book?.publishedAt}
          </p>
          <p className='text-base my-3'>{book?.summary}</p>
          <Button full onClick={handleBookCreateButton}>
            내 서재에 등록
          </Button>
        </div>
      )}
      <BookCreateModal
        isCreateModalOpen={isOpen}
        toggleCreateModal={toggleModal}
        selectedBook={book}
      />
    </WrapContainer>
  );
};

export default SearchBookDetail;
