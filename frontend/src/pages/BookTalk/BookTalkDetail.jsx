import React, { useState, useEffect, useRef, useCallback } from 'react';
import ChatBubble from '@components/@common/ChatBubble';
import Button from '@components/@common/Button';
import Textarea from '@components/@common/Textarea';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import TopDownButton from '@components/@common/TopDownButton';
import { getBookTalkChats } from '@services/BookTalk';
import { postBookTalkChat, postBookTalkEnter } from '../../services/BookTalk';
import { useLocation, useParams } from 'react-router-dom';

const BookTalkDetail = () => {
  const location = useLocation();
  const params = useParams();
  const { bookTalkId } = useParams();
  const { book } = location.state;
  const userNickName = localStorage.getItem('USER_NICKNAME');
  const [inputMessage, setInputMessage] = useState('');
  const [messageList, setMessageList] = useState([]);
  const messagesEndRef = useRef(null);

  const [connected, setConnected] = useState(false);
  const client = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  // 스크롤 아래로
  useEffect(scrollToBottom, [messageList]);

  // 채팅 연결
  useEffect(() => {
    connect();
    getChats();

    return () => disconnect();
  }, []);

  // 메세지 추가
  const addMessage = useCallback(newMessage => {
    setMessageList(prevList => [...prevList, newMessage]);
  }, []);

  // 메세지 작성
  const handleInputChange = e => {
    const message = e.target.value;
    if (message.length <= 1000) {
      setInputMessage(message);
    }
  };

  // 메세지 전송
  const handleSendMessage = async () => {
    if (inputMessage.trim() !== '') {
      await postBookTalkChat(bookTalkId, inputMessage);
      setInputMessage('');
    }
  };

  // 채팅방 연결
  const connect = () => {
    client.current = new Client({
      webSocketFactory: () =>
        new SockJS('https://api.i11a506.ssafy.io/booktalks/connect'),
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    client.current.onConnect = async frame => {
      setConnected(true);
      await postBookTalkEnter(bookTalkId);

      client.current.subscribe(`/booktalks/sub/chat/${bookTalkId}`, message => {
        console.log(JSON.stringify(JSON.parse(message.body)));

        // 채팅 가져오기
        const newMessage = JSON.parse(message.body);
        addMessage(newMessage);
      });
    };

    client.current.onStompError = frame => {};

    client.current.activate();
  };

  // 채팅 가져오기
  const getChats = async () => {
    const data = await getBookTalkChats(bookTalkId);
    console.log(data);

    setMessageList(data.reverse());
  };

  // 채팅방 연결 제거
  const disconnect = () => {
    if (client.current !== null) {
      client.current.deactivate();
    }

    setConnected(false);
  };

  return (
    <div className='flex flex-col min-h-[calc(100vh-121px)] w-full'>
      <div className='flex justify-center w-full'>
        <div className='flex flex-col items-center w-3/4 max-w-md'>
          <div className='w-32 min-h-40 my-4 flex justify-center'>
            <img
              src={book?.coverImgUrl}
              alt='Book Cover'
              className='rounded-lg'
            />
          </div>

          <div className='text-center mb-3'>
            <h2 className='text-xl font-semibold'>{book.title}</h2>
            <p className='text-gray-500'>{book.author}</p>
          </div>
        </div>
      </div>
      <div className='flex-1 overflow-y-auto p-4 scrollbar-none flex flex-col-reverse'>
        <div className='space-y-4'>
          {messageList.map((message, index) => (
            <ChatBubble
              key={index}
              message={message?.message}
              role={message?.nickName === userNickName ? 'user' : 'other'}
              showProfile={message?.role !== 'user'}
              showLikes={true}
              likes={message?.likes}
              profileImage={message?.profileImage}
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
