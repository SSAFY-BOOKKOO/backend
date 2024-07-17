import { useState } from 'react';
import WrapContainer from '../components/Layout/WrapContainer';
import Button from '../components/@common/Button';
import BookCreateModal from '../components/Library/BookCreate/BookCreateModal';
import useModal from '../hooks/useModal';
import { book } from '../mocks/BookData';

const LibrarySearchDetail = () => {
  const { isOpen, toggleModal } = useModal();

  return (
    <WrapContainer>
      <div className='h-screen max-h-screen'>
        <div className='h-3/5 w-full flex'>
          <img
            src={book.cover_img_url}
            className='max-h-full max-w-full object-contain'
            alt={book.title}
          />
        </div>
        <p className='text-overflow text-lg font-semibold'>{book.title}</p>
        <p className='text-sm text-gray-600'>{book.author}</p>
        <p className='text-sm text-gray-600'>
          {book.publisher} | {book.published_at}
        </p>
        <p className='text-sm my-3'>{book.summary}</p>
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

export default LibrarySearchDetail;
