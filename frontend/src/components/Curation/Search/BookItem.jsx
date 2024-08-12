import React, { useState, useEffect } from 'react';
import Button from '../../@common/Button';
import { useNavigate } from 'react-router-dom';
import { authAxiosInstance } from '@services/axiosInstance';
const BookItem = ({ book, onClick, onCreateClick }) => {
  // const [idBook, setBook] = useState(book);
  const titleMaxLength = 10;
  const authorMaxLength = 24;
  const navigate = useNavigate();

  const displayTitle =
    book.title.length > titleMaxLength
      ? book.title.substring(0, titleMaxLength - 1) + '...'
      : book.title;

  const displayAuthor =
    book.author.length > authorMaxLength
      ? book.author.substring(0, authorMaxLength - 1) + '...'
      : book.author;

  const navigateToCreate = ({ book }) => {
    navigate('/curation/letter-create', {
      state: {
        image: `${book.coverImgUrl}`,
        title: { title: `${book.title}` },
      },
    });
  };

  // const handleButtonClick = e => {
  //   e.stopPropagation();
  // axios 요청

  // const bookData = {
  //   coverImgUrl: book.coverImgUrl,
  //   author: book.author,
  //   publisher: book.publisher,
  //   summary: '...',
  //   title: book.title,
  //   isbn: book.isbn,
  //   itemPage: 412,
  //   sizeDepth: 20,
  //   sizeHeight: 230,
  //   sizeWidth: 150,
  //   publishedAt: book.publishedAt,
  //   category: {
  //     id: 1,
  //     name: 'string',
  //   },
  // };

  // authAxiosInstance
  //   .post('/books', bookData)
  //   .then(res => {
  //     // ////////////////////////이 부분 수정됨
  //     console.log('ID 받아라!!:', res.data.id);
  //     book.id = res.data.id;
  //     console.log('book 객체!!:', book);
  //   })
  //   .catch(err => {
  //     console.log('error:', err);
  //   });

  //   onCreateClick();
  //   navigateToCreate();
  //   console.log('눌렀음');
  // };

  const handleButtonClick = e => {
    e.stopPropagation();
    // axios 요청

    const bookData = {
      coverImgUrl: book.coverImgUrl,
      author: book.author,
      publisher: book.publisher,
      summary: '...',
      title: book.title,
      isbn: book.isbn,
      itemPage: 412,
      sizeDepth: 20,
      sizeHeight: 230,
      sizeWidth: 150,
      publishedAt: book.publishedAt,
      category: {
        id: 1,
        name: 'string',
      },
    };

    authAxiosInstance
      .post('/books/isbn', bookData)
      .then(res => {
        console.log('ID 받아라!!:', res.data.id);
        book.id = res.data.id;

        console.log('book 객체!!:', book);

        // const updatedBook = { ...book, id: book.id };
        // onCreateClick(updatedBook);
        // navigateToCreate();
      })
      .catch(err => {
        console.log('error:', err);
      });
    onCreateClick();
    navigateToCreate();
    console.log('눌렀음');
  };

  return (
    <div
      className='flex items-start space-x-4 p-3 mb-2 bg-white cursor-pointer'
      onClick={handleButtonClick}
    >
      <div className='w-36 h-36 flex'>
        <img
          className='object-contain'
          src={book?.coverImgUrl}
          alt={book?.title}
        />
      </div>

      <div className='flex flex-col justify-between h-36 w-full'>
        <div className='flex flex-col space-y-1 overflow-hidden'>
          {/* <p>{book}</p> */}
          <p className='text-overflow text-lg font-semibold'>{displayTitle}</p>
          <p className='text-sm text-gray-600'>{displayAuthor}</p>
          <p className='text-sm text-gray-600'>
            {book?.publisher} | {book?.publishedAt}
          </p>
        </div>
        <Button className='w-14 mt-2' size='small' onClick={handleButtonClick}>
          등록
        </Button>
      </div>
    </div>
  );
};

export default BookItem;
