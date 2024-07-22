import { Outlet } from 'react-router-dom';
import Header from './components/Layout/Header';
import BottomTab from './components/Layout/BottomTab';
import Main from './components/Layout/Main';

const App = () => {
  return (
    <Main>
      <Header />
      <div className='flex-1 overflow-y-auto'>
        <Outlet />
      </div>
      <BottomTab />
    </Main>
  );
};

export default App;
