import React, { useState, useEffect, useRef } from 'react';
import ChatBubble from '@components/@common/ChatBubble';
import Button from '@components/@common/Button';
import Textarea from '@components/@common/Textarea';
import { comments, talkbook } from '@mocks/BookTalkData';

const BookTalkDetail = () => {
  const [inputMessage, setInputMessage] = useState('');
  const [book, setBook] = useState(talkbook);
  const [commentList, setCommentList] = useState(comments);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(scrollToBottom, [commentList]);

  const handleInputChange = e => {
    const message = e.target.value;
    if (message.length <= 1000) {
      setInputMessage(message);
    }
  };

  const handleSendMessage = () => {
    if (inputMessage.trim() !== '') {
      const newComment = {
        message: inputMessage,
        role: 'user',
        likes: 0,
        profileImage: '',
      };
      setCommentList([...commentList, newComment]);
      setInputMessage('');
    }
  };

  return (
    <div className='flex flex-col min-h-[calc(100vh-121px)]'>
      <div className='flex flex-col items-center'>
        <div className='w-32 min-h-40 my-4 flex items-center'>
          <img
            src={book.cover_img_url}
            alt='Book Cover'
            className='rounded-lg'
          />
        </div>

        <div className='text-center mb-3'>
          <h2 className='text-xl font-semibold'>{book.title}</h2>
          <p className='text-gray-500'>{book.author}</p>
        </div>
      </div>
      <div className='flex-1 overflow-y-auto p-4 scrollbar-none'>
        <div className='space-y-4'>
          {commentList.map((comment, index) => (
            <ChatBubble
              key={index}
              message={comment.message}
              role={comment.role}
              showProfile={comment.role !== 'user'}
              showLikes={true}
              likes={comment.likes}
              profileImage={comment.profileImage}
            />
          ))}
        </div>
      </div>
      <div ref={messagesEndRef} />
      <div className='bg-white p-4 sticky bottom-0'>
        <div className='max-w-md w-full flex flex-row items-center justify-center mx-auto'>
          <div className='flex-grow'>
            <Textarea
              value={inputMessage}
              onChange={handleInputChange}
              placeholder='내용을 입력해주세요(1000자 이내)'
              maxLength={1000}
              customClass='border rounded-l-lg focus:outline-none'
            />
          </div>
          <Button type='button' className='ml-2' onClick={handleSendMessage}>
            등록
          </Button>
        </div>
      </div>
    </div>
  );
};

export default BookTalkDetail;
