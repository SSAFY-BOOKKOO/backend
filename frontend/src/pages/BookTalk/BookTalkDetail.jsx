import React, { useState, useEffect, useRef } from 'react';
import ChatBubble from '@components/@common/ChatBubble';
import Button from '@components/@common/Button';
import Textarea from '@components/@common/Textarea';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useLocation, useParams } from 'react-router-dom';
import { useInView } from 'react-intersection-observer';
import useModal from '@hooks/useModal';
import ProfileModal from '@components/@common/ProfileModal';
import useBookTalkChatsInfiniteScroll from '@hooks/useBookTalkChatsInfiniteScroll';
import Spinner from '@components/@common/Spinner';
import { postBookTalkChat, postBookTalkEnter } from '@services/BookTalk';
import { QueryClient } from '@tanstack/react-query';

const BookTalkDetail = () => {
  const location = useLocation();
  const { bookTalkId } = useParams();
  const { book } = location.state;
  const memberId = localStorage.getItem('MEMBER_ID');
  const [inputMessage, setInputMessage] = useState('');
  const [connected, setConnected] = useState(false);
  const [messageList, setMessageList] = useState([]);
  const client = useRef(null);
  const chatContainerRef = useRef(null);
  const messagesEndRef = useRef(null);
  const [initialLoading, setInitialLoading] = useState(true);

  const { ref, inView } = useInView();
  const { isOpen, toggleModal, closeModal } = useModal();
  const [modalUser, setModalUser] = useState(null);

  const queryClient = new QueryClient();
  const { data, fetchNextPage, isFetchingNextPage, hasNextPage } =
    useBookTalkChatsInfiniteScroll(bookTalkId);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView();
  };

  useEffect(() => {
    connect();
    return () => {
      disconnect();
      queryClient.removeQueries({ queryKey: ['bookTalkChats', bookTalkId] });
    };
  }, []);

  useEffect(() => {
    if (data?.pages) {
      setMessageList(data.pages.flatMap(page => page));
    }
  }, [data]);

  useEffect(() => {
    if (initialLoading && messageList.length > 0) {
      scrollToBottom();
      setInitialLoading(false);
    }
  }, [messageList, initialLoading]);

  useEffect(() => {
    if (inView && hasNextPage && !isFetchingNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage, isFetchingNextPage]);

  const handleInputChange = e => {
    const message = e.target.value;
    if (message.length <= 1000) {
      setInputMessage(message);
    }
  };

  const handleSendMessage = async () => {
    if (inputMessage.trim() !== '') {
      await postBookTalkChat(bookTalkId, inputMessage);
      setInputMessage('');
    }

    scrollToBottom();
  };

  const connect = () => {
    client.current = new Client({
      webSocketFactory: () =>
        new SockJS('https://api.i11a506.ssafy.io/booktalks/connect'),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    client.current.onConnect = async () => {
      setConnected(true);
      await postBookTalkEnter(bookTalkId);

      client.current.subscribe(`/booktalks/sub/chat/${bookTalkId}`, message => {
        const newMessage = JSON.parse(message.body);

        queryClient.setQueryData(['bookTalkChats', bookTalkId], old => {
          if (!old) return old;

          const newPages = [...old.pages];
          newPages[0] = [newMessage, ...newPages[0]];

          return {
            ...old,
            pages: newPages,
          };
        });

        setMessageList(prevList => [newMessage, ...prevList]);
      });
    };

    client.current.activate();
  };

  const disconnect = () => {
    if (client.current !== null) {
      client.current.deactivate();
    }
    setConnected(false);
  };

  const handleProfileClick = user => {
    setModalUser(user);
    toggleModal();
  };

  const renderMessages = () => {
    if (!messageList.length) return null;

    return messageList.map((message, index) => (
      <ChatBubble
        key={message?.messageId}
        message={message?.message}
        role={message.memberId === memberId ? 'user' : 'other'}
        showProfile={message?.memberId !== memberId}
        showLikes={true}
        likes={message?.likes}
        profileImage={message?.profileImgUrl}
        profileClick={() =>
          handleProfileClick({
            nickName: message.nickName,
            profileImgUrl: message.profileImgUrl,
          })
        }
      />
    ));
  };

  return (
    <div className='flex flex-col min-h-[calc(100vh-121px)] w-full'>
      <ProfileModal
        isOpen={isOpen}
        onRequestClose={closeModal}
        nickname={modalUser?.nickName}
        profileImgUrl={modalUser?.profileImgUrl}
      />
      <div className='sticky top-0 z-10 bg-white shadow p-4'>
        <div className='flex items-center justify-between'>
          <div className='flex items-center'>
            <img
              src={book?.coverImgUrl}
              alt='book_cover'
              className='w-16 h-20 rounded-lg mr-4'
            />
            <div>
              <h2 className='text-lg font-semibold text-overflow-2'>
                {book.title}
              </h2>
              <p className='text-gray-500 text-overflow-1'>{book.author}</p>
            </div>
          </div>
        </div>
      </div>
      {isFetchingNextPage && <Spinner infiniteScroll />}
      <div ref={ref}></div>
      <div
        ref={chatContainerRef}
        className='flex-1 overflow-y-auto p-4 scrollbar-none flex flex-col-reverse'
      >
        {renderMessages()}
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
