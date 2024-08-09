import React, { useState, useEffect } from 'react';
import Input from '@components/@common/Input';
import Button from '@components/@common/Button';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { curationBookAtom } from '@atoms/curationBookAtom';
import { authAxiosInstance } from '../../services/axiosInstance';

const CreateLetter = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();
  const [book] = useAtom(curationBookAtom);

  useEffect(() => {
    console.log('Book object:', book);
    // console.log(`Book Id: ${book.id}`);
  }, [book]);

  const handleTitleChange = e => {
    setTitle(e.target.value);
  };

  const handleContentChange = e => {
    setContent(e.target.value);
  };

  const handleSearchButtonClick = () => {
    navigate('/curation/letter-create/book-search');
  };

  // 전송 로직
  const handleLetterCreate = () => {
    const letter = {
      // bookId: book ? book.id : null,
      bookId: book.id,
      title,
      content,
    };

    authAxiosInstance
      .post('/curations', letter)
      .then(res => {
        // console.log('Letter send successfully:', res);
        navigate('/curation/send');
      })
      .catch(err => {
        console.log('error:', err);
      });
  };

  return (
    <div className='flex flex-col items-center p-4 mx-8'>
      <div className='w-full max-w-md'>
        <div className='bg-gray-100 p-4 rounded-lg mb-4'>
          <div className='flex items-center justify-between'>
            {book ? (
              <div className='flex items-center'>
                <img
                  src={book.coverImgUrl}
                  alt={book.title}
                  className='w-12 h-16 rounded-md shadow-lg'
                />
                <div className='ml-4 text-gray-700 text-ellipsis text-overflow-1'>
                  {book.title}
                </div>
              </div>
            ) : (
              <div className='text-gray-700'>책을 등록해 주세요!</div>
            )}
            <button
              className='px-4 py-2 bg-gray-300 rounded-lg text-gray-700'
              onClick={handleSearchButtonClick}
            >
              검색
            </button>
          </div>
        </div>
        <div className='mb-4'>
          <Input
            placeholder='제목을 입력해 주세요'
            value={title}
            onChange={handleTitleChange}
            className='w-full p-3 bg-white border border-gray-300 rounded-lg text-sm text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-1 focus:ring-blue-200 focus:border-transparent mb-4'
          />
          <textarea
            className='w-full p-3 bg-white border border-gray-300 rounded-lg text-sm text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-1 focus:ring-blue-200 focus:border-transparent mb-4 h-96 resize-none'
            placeholder='내용을 입력해 주세요'
            rows='6'
            value={content}
            onChange={handleContentChange}
          ></textarea>
        </div>
        <Button
          text='레터 전송'
          className='w-full py-3 bg-blue-500 rounded-lg text-gray-950'
          onClick={handleLetterCreate}
        />
      </div>
    </div>
  );
};

export default CreateLetter;
