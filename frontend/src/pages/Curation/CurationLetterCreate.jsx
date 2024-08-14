import React, { useState, useEffect } from 'react';
import Input from '@components/@common/Input';
import Button from '@components/@common/Button';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { curationBookAtom } from '@atoms/curationBookAtom';
import { authAxiosInstance } from '@services/axiosInstance';
import { BsEnvelopeHeart } from 'react-icons/bs';
import Alert from '@components/@common/Alert';
import { showAlertAtom } from '@atoms/alertAtom';

const CreateLetter = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();
  const [book, setBook] = useAtom(curationBookAtom);
  const [titleLength, setTitleLength] = useState(0);
  const [, showAlert] = useAtom(showAlertAtom);

  useEffect(() => {}, [book]);

  const handleTitleChange = e => {
    const newTitle = e.target.value;
    if (newTitle.length <= 20) {
      // 20자 제한
      setTitle(newTitle);
      setTitleLength(newTitle.length);
    }
  };

  const handleContentChange = e => {
    setContent(e.target.value);
  };

  const handleSearchButtonClick = () => {
    navigate('/curation/letter-create/book-search');
  };

  // 전송 로직
  const handleLetterCreate = async () => {
    const letter = {
      bookId: book ? book.id : null,
      title,
      content,
    };

    try {
      await authAxiosInstance.post('/curations', letter);
      showAlert('레터가 성공적으로 전송되었습니다.', true, () => {
        navigate('/curation/send');
      });
      setBook(null); // 책 정보 초기화
    } catch (err) {
      console.error('error:', err);
      showAlert('레터 전송에 실패했습니다. 다시 시도해 주세요.', true);
    }
  };

  return (
    <div className='flex flex-col items-center p-4 mx-8'>
      <Alert />
      <div className='w-full max-w-md'>
        <div className='bg-gray-100 p-4 rounded-lg mb-4'>
          <div className='flex items-center justify-between'>
            {book ? (
              <div className='flex items-center'>
                <img
                  src={book.coverImgUrl}
                  alt={<BsEnvelopeHeart />}
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
            className='w-full p-3 bg-white border border-gray-300 rounded-lg text-sm text-gray-700 placeholder-gray-400 focus:outline-none focus:ring-1 focus:ring-blue-200 focus:border-transparent mb-1'
          />
          <p className='text-right text-gray-500 text-sm mb-4'>
            {titleLength}/20
          </p>{' '}
          {/* 글자 수 표시 */}
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
