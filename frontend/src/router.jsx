import React from 'react';
import { createBrowserRouter, Navigate } from 'react-router-dom';
import App from './App.jsx';
import LibraryHome from './pages/LibraryHome.jsx';
import LibraryDetail from './pages/LibraryDetail.jsx';
import LibrarySearch from './pages/LibrarySearch.jsx';
import LibrarySearchDetail from './pages/LibrarySearchDetail.jsx';
import LibraryMain from './pages/LibraryMain.jsx';
import Register from './pages/Register.jsx';
import CurationReceive from './pages/CurationReceive.jsx';
import CurationSend from './pages/CurationSend.jsx';
import CurationStore from './pages/CurationStore.jsx';
import Login from './pages/Login.jsx';
import CurationChatBot from './pages/CurationChatBot.jsx';
import CurationLetterCreate from './pages/CurationLetterCreate.jsx';
import CurationLetterDetail from './pages/CurationLetterDetail.jsx';
import BookTalk from './pages/BookTalk.jsx';
import Intro from './pages/Intro.jsx';
import PrivateRoute from '@/components/@common/PrivateRoute';

const isAuthenticated = true; // 로그인 상태를 확인하는 로직 추가 필요

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
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
          { path: '/detail/:id', element: <LibraryDetail /> },
          { path: 'library', element: <LibraryMain /> },
          { path: 'library/search', element: <LibrarySearch /> },
          { path: 'library/search/:bookId', element: <LibrarySearchDetail /> },
          { path: 'curation/receive', element: <CurationReceive /> },
          { path: 'curation/send', element: <CurationSend /> },
          { path: 'curation/store', element: <CurationStore /> },
          { path: 'curation/chatbot', element: <CurationChatBot /> },
          { path: 'curation/letter-create', element: <CurationLetterCreate /> },
          { path: '/curation/letter/:id', element: <CurationLetterDetail /> },
          { path: '/booktalk', element: <BookTalk /> },
        ],
      },
    ],
  },
  { path: 'register', element: <Register /> },
  { path: 'login', element: <Login /> },
  { path: 'intro', element: <Intro /> },
  { path: '*', element: <Navigate to='/' /> },
]);

export default router;
