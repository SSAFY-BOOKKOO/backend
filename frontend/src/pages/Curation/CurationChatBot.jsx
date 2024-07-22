import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import ChatBubble from '@components/Curation/ChatBubble';
import Input from '@components/@common/Input';
import Button from '@components/@common/Button';

const CurationChatBot = () => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(scrollToBottom, [messages]);

  const handleSendMessage = e => {
    e.preventDefault();

    // 내용이 입력된 경우에만 전송
    if (inputMessage.trim() !== '') {
      setMessages([
        ...messages,
        {
          text: inputMessage,
          role: 'user',
          time: new Date().toLocaleTimeString(),
        },
      ]);

      setInputMessage('');

      // API 연동 추가

      setTimeout(() => {
        setMessages(msgs => [
          ...msgs,
          {
            text: '챗봇입니당',
            role: 'bot',
            time: new Date().toLocaleTimeString(),
          },
        ]);
      }, 1000);
    }
  };

  return (
    <div className='flex flex-col h-[calc(100vh-60px)] bg-gray-100'>
      <div className='flex-1 overflow-y-auto p-4 scrollbar-none'>
        {messages.map((message, index) => (
          <ChatBubble
            key={index}
            message={message.text}
            role={message.role}
            time={message.time}
          />
        ))}
        <div ref={messagesEndRef} />
      </div>
      <form onSubmit={handleSendMessage} className='bg-white p-4'>
        <div className='mb-4 w-full flex flex-row items-center justify-center'>
          <div className='flex-grow'>
            <Input
              type='text'
              value={inputMessage}
              onChange={e => setInputMessage(e.target.value)}
              placeholder='내용을 입력해주세요'
              customClass='border rounded-l-lg focus:outline-none'
            />
          </div>
          <Button
            type='submit'
            className='ml-2 bg-blue-500 text-white  focus:bg-blue-500'
          >
            등록
          </Button>
        </div>
      </form>
    </div>
  );
};

export default CurationChatBot;
