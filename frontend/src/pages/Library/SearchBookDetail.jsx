import { useEffect, useState } from 'react';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import BookCreateModal from '@components/Library/BookCreate/BookCreateModal';
import useModal from '@hooks/useModal';
import { book } from '@mocks/BookData';
import { getAladinBookByIsbn } from '@services/Book';
import { useParams } from 'react-router-dom';

const SearchBookDetail = () => {
  const { isOpen, toggleModal } = useModal();
  const { bookId } = useParams();
  const [book, setBook] = useState([]);

  const handleGetBook = async () => {
    const bookData = await getAladinBookByIsbn(bookId);
    setBook(bookData);
  };

  useEffect(() => {
    handleGetBook();
  }, []);

  return (
    <WrapContainer>
      <div className='h-screen max-h-screen'>
        <div className='h-1/2 w-full flex justify-center my-3'>
          <img
            src={book?.coverImgUrl}
            className='max-h-full max-w-full object-contain rounded-lg'
            alt={book?.title}
          />
        </div>
        <p className='text-overflow text-xl font-semibold'>{book?.title}</p>
        <p className='text-base text-gray-600'>{book?.author}</p>
        <p className='text-base text-gray-600'>
          {book?.publisher} | {book?.publishedAt}
        </p>
        <p className='text-base my-3'>{book?.summary}</p>
        <Button full onClick={toggleModal}>
          내 서재에 등록
        </Button>
      </div>
      <BookCreateModal
        isCreateModalOpen={isOpen}
        toggleCreateModal={toggleModal}
      />
    </WrapContainer>
  );
};

export default SearchBookDetail;
