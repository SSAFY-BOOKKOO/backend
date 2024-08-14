import React from 'react';
import { createBrowserRouter, Navigate } from 'react-router-dom';
import App from './App.jsx';
import LibraryDetail from './pages/Library/LibraryDetail.jsx';
import LibrarySearch from './pages/Library/LibrarySearch.jsx';
import SearchBookDetail from './pages/Library/SearchBookDetail.jsx';
import LibraryMain from './pages/Library/LibraryMain.jsx';
import Register from './pages/Member/Register.jsx';
import MyPage from './pages/Mypage/MyPage.jsx';
import Statistics from './pages/Mypage/Statistics.jsx';
import Friend from './pages/Mypage/Friend.jsx';
import Notification from './pages/Member/Notification.jsx';
import Profile from './pages/Mypage/Profile.jsx';
import CurationReceive from './pages/Curation/CurationReceive.jsx';
import CurationSend from './pages/Curation/CurationSend.jsx';
import CurationStore from './pages/Curation/CurationStore.jsx';
import Login from './pages/Member/Login.jsx';
import CurationChatBot from './pages/Curation/CurationChatBot.jsx';
import CurationLetterCreate from './pages/Curation/CurationLetterCreate.jsx';
import CurationSearchBook from './pages/Curation/CurationSearchBook.jsx';
import CurationLetterDetail from './pages/Curation/CurationLetterDetail.jsx';
import BookTalkMain from './pages/BookTalk/BookTalkMain.jsx';
import BookTalkDetail from './pages/BookTalk/BookTalkDetail.jsx';
import Intro from './pages/Member/Intro.jsx';
import PrivateRoute from '@components/@common/PrivateRoute';
import Quote from './pages/Mypage/Quote.jsx';
import SearchMore from './pages/Library/SearchMore.jsx';
import BookTalkMore from './pages/BookTalk/BookTalkMore.jsx';
import PasswordFind from './pages/Member/PasswordFind.jsx';
import LibraryOthers from './pages/Library/LibraryOthers.jsx';
import SocialLoginCallback from './components/Login/SocialLoginCallback.jsx';
import BookTalkCreate from './pages/BookTalk/BookTalkCreate.jsx';
import FriendSearch from './pages/Mypage/FriendSearch.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { path: 'intro', element: <Intro /> },

      // 인증이 필요한 페이지
      {
        element: <PrivateRoute />,
        children: [
          // library
          { path: '', element: <LibraryMain /> },
          { path: 'library', element: <LibraryOthers /> },

          // book in library detail
          {
            path: 'library/:libraryId/detail/:bookId',
            element: <LibraryDetail />,
          },

          // search
          { path: 'search', element: <LibrarySearch /> },

          // search more
          { path: 'search/:type/more', element: <SearchMore /> },

          // book detail
          { path: 'book/detail/:bookId', element: <SearchBookDetail /> },

          // curation
          { path: 'curation/receive', element: <CurationReceive /> },
          { path: 'curation/send', element: <CurationSend /> },
          { path: 'curation/store', element: <CurationStore /> },
          { path: 'curation/chatbot', element: <CurationChatBot /> },
          { path: 'curation/letter-create', element: <CurationLetterCreate /> },
          {
            path: 'curation/letter-create/book-search',
            element: <CurationSearchBook />,
          },

          { path: 'curation/letter/:id', element: <CurationLetterDetail /> },

          //booktalk
          { path: 'booktalk', element: <BookTalkMain /> },
          { path: 'booktalk/detail/:bookTalkId', element: <BookTalkDetail /> },
          { path: 'booktalk/more', element: <BookTalkMore /> },
          { path: 'booktalk/create', element: <BookTalkCreate /> },

          // mypage
          { path: 'mypage/friend', element: <Friend /> },
          { path: 'mypage/friend/search', element: <FriendSearch /> },
          { path: 'mypage/statistics', element: <Statistics /> },
          { path: 'mypage/profile', element: <Profile /> },
          { path: 'mypage/quote', element: <Quote /> },
          { path: 'mypage', element: <MyPage /> },

          { path: 'notification', element: <Notification /> },
        ],
      },
    ],
  },
  {
    path: 'register',
    element: <Register />,
  },
  {
    path: 'find-password',
    element: <PasswordFind />,
  },
  {
    path: 'login',
    element: <Login />,
  },
  {
    path: 'auth/callback',
    element: <SocialLoginCallback />,
  },
  {
    path: '*',
    element: <Navigate to='/' />,
  },
]);

export default router;
