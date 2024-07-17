import { createBrowserRouter } from 'react-router-dom';
import App from './App.jsx';
import LibraryHome from './pages/LibraryHome.jsx'
// import LibraryDetail from './pages/LibraryDetail.jsx';
import LibraryDetail2 from './pages/LibraryDetail2.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children:[
      {
        path:'/',
        element: <LibraryHome/>
      },
      {
        path:'/detail/:id',
        element:<LibraryDetail2/>

      }
    ]
  },
]);

export default router;
