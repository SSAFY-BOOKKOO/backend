import { createBrowserRouter } from 'react-router-dom';
import App from './App.jsx';
import Library from './components/Library/LibraryMain';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: 'library',
        element: <Library />,
      },
    ],
  },
]);

export default router;
