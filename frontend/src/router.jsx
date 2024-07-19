import React from 'react';
import { createBrowserRouter } from 'react-router-dom';
import App from './App.jsx';
import LibraryDetail from './pages/LibraryDetail.jsx';
import LibrarySearch from './pages/LibrarySearch.jsx';
import LibrarySearchDetail from './pages/LibrarySearchDetail.jsx';
import LibraryMain from './pages/LibraryMain.jsx';
import Register from './pages/Register.jsx';
import MyPage from './pages/MyPage.jsx';
import Statistics from './pages/Statistics.jsx';
import Friend from './pages/Friend.jsx';
import Notification from './pages/Notification.jsx';
import Profile from './pages/Profile.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: '/library/detail/:id',
        element: <LibraryDetail />,
      },
      {
        path: '/library',
        element: <LibraryMain />,
      },
      {
        path: '/register',
        element: <Register />,
      },
      {
        path: '/library/search',
        element: <LibrarySearch />,
      },
      {
        path: '/library/search/:bookId',
        element: <LibrarySearchDetail />,
      },
      {
        path: '/mypage/friend',
        element: <Friend />,
      },
      {
        path: '/mypage/statistics',
        element: <Statistics />,
      },
      {
        path: '/mypage',
        element: <MyPage />,
      },
      {
        path: '/notification',
        element: <Notification />,
      },
      {
        path: '/mypage/profile',
        element: <Profile />,
      },
    ],
  },
]);

export default router;
