import { createBrowserRouter } from 'react-router-dom';
import App from './App';
import LibraryMain from './pages/LibraryMain';
import Register from './pages/Register';
import Friend from './pages/Friend';
import MyPage from './pages/MyPage';
import Statistics from './pages/Statistics';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: 'library',
        element: <LibraryMain />,
      },
      {
        path: 'register',
        element: <Register />,
      },
      {
        path: 'mypage',
        element: <MyPage />,
      },
      {
        path: 'mypage/friend',
        element: <Friend />,
      },
      {
        path: 'mypage/statistics',
        element: <Statistics />,
      },
    ],
  },
]);

export default router;
