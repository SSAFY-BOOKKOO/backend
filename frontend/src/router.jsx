import React from 'react';
import { createBrowserRouter, Navigate } from 'react-router-dom';
import App from './App.jsx';
import LibraryHome from './pages/Library/LibraryHome.jsx';
import LibraryDetail from './pages/Library/LibraryDetail.jsx';
import LibrarySearch from './pages/Library/LibrarySearch.jsx';
import LibrarySearchDetail from './pages/Library/LibrarySearchDetail.jsx';
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
import CurationLetterDetail from './pages/Curation/CurationLetterDetail.jsx';
import BookTalkMain from './pages/BookTalk/BookTalkMain.jsx';
import BookTalkDetail from './pages/BookTalk/BookTalkDetail.jsx';
import Intro from './pages/Member/Intro.jsx';
import PrivateRoute from '@/components/@common/PrivateRoute';
import CurationLetterSend from './pages/Curation/CurationLetterSend.jsx';
import Quote from './pages/Mypage/Quote.jsx';

const isAuthenticated = true; // 로그인 상태를 확인하는 로직 추가 필요

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      { path: 'intro', element: <Intro /> },

      // 커뮤니티 탭 (로그인 여부와 상관없이 접근 가능)

      { path: 'booktalk', element: <BookTalkMain /> },
      { path: 'booktalk/detail/:bookId', element: <BookTalkDetail /> },

      // 인증이 필요한 페이지
      {
        element: (
          <PrivateRoute
            userAuthentication={true}
            isAuthenticated={isAuthenticated}
          />
        ),
        children: [
          { path: '/', element: <LibraryHome /> },

          // library
          { path: 'library', element: <LibraryMain /> },
          { path: 'library/detail/:id', element: <LibraryDetail /> },
          { path: 'search', element: <LibrarySearch /> },
          { path: '/search/:bookId', element: <LibrarySearchDetail /> },

          // curation
          { path: 'curation/receive', element: <CurationReceive /> },
          { path: 'curation/send', element: <CurationSend /> },
          { path: 'curation/store', element: <CurationStore /> },
          { path: 'curation/chatbot', element: <CurationChatBot /> },
          { path: 'curation/letter-create', element: <CurationLetterCreate /> },
          { path: 'curation/letter/:id', element: <CurationLetterDetail /> },
          {
            path: 'curation/letter-create/send',
            element: <CurationLetterSend />,
          },

          // mypage
          { path: 'mypage/friend', element: <Friend /> },
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
    path: 'login',
    element: <Login />,
  },
  {
    path: '*',
    element: <Navigate to='/' />,
  },
]);

export default router;
