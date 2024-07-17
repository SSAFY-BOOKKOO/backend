import { createBrowserRouter } from 'react-router-dom';
import App from './App.jsx';
import LibraryMain from './pages/LibraryMain.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: 'library',
        element: <LibraryMain />,
      },
    ],
  },
]);

export default router;
