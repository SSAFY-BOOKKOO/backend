import React, { useState, useEffect, useRef } from 'react';
import ChatBubble from '@components/@common/ChatBubble';
import Input from '@components/@common/Input';
import Button from '@components/@common/Button';
import ChatLoading from '@components/Curation/ChatLoading';
import bookkooBookIcon from '@assets/icons/bookkoo_book_icon.png';
import { postChatbot } from '@services/Curation';

const CurationChatBot = () => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(scrollToBottom, [messages]);

  // 자동 안내 문구
  useEffect(() => {
    scrollToBottom();

    setMessages([
      {
        text: '안녕하세요! 저는 책을 추천해주는 북꾸입니다!',
        role: 'assistant',
        time: new Date().toLocaleTimeString(),
        profileImage: bookkooBookIcon,
        showProfile: true,
      },
    ]);
  }, []);

  const handleSendMessage = async e => {
    e.preventDefault();

    if (inputMessage.trim() !== '') {
      const newUserMessage = {
        text: inputMessage,
        role: 'user',
        time: new Date().toLocaleTimeString(),
      };

      setMessages(prevMessages => [...prevMessages, newUserMessage]);
      setInputMessage('');
      setLoading(true);

      try {
        const data = await postChatbot(inputMessage);
        const newBotMessage = {
          text: data.content,
          role: data.role,
          time: new Date().toLocaleTimeString(),
          profileImage: bookkooBookIcon,
          showProfile: true,
        };

        setMessages(prevMessages => [...prevMessages, newBotMessage]);
      } catch (error) {
        console.error('Error posting to chatbot:', error);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div className='flex flex-col min-h-[calc(100vh-130px)] bg-gray-100'>
      <div className='flex-1 overflow-y-auto p-4 scrollbar-none'>
        {messages.map((message, index) => (
          <ChatBubble
            key={index}
            message={message.text}
            role={message.role}
            time={message.time}
            showProfile={message.showProfile}
            profileImage={message.profileImage}
          />
        ))}
        {loading && (
          <div className='flex justify-start mb-4'>
            <div className='flex-shrink-0 w-12 h-12 mr-3 flex items-center justify-center'>
              <img
                src={bookkooBookIcon}
                alt='Profile'
                className='max-w-full max-h-full object-contain rounded-full'
              />
            </div>
            <ChatLoading />
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>
      <form
        onSubmit={handleSendMessage}
        className='bg-white p-4 sticky bottom-0'
      >
        <div className='max-w-md w-full flex flex-row items-center justify-center mx-auto'>
          <div className='flex-grow'>
            <Input
              type='text'
              value={inputMessage}
              onChange={e => setInputMessage(e.target.value)}
              placeholder='내용을 입력해주세요'
              customClass='border rounded-l-lg focus:outline-none'
            />
          </div>
          <Button type='submit' className='ml-2' disabled={loading}>
            등록
          </Button>
        </div>
      </form>
    </div>
  );
};

export default CurationChatBot;
