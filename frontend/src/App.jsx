import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Header from './components/Layout/Header';
import BottomTab from './components/Layout/BottomTab';
import Main from './components/Layout/Main';

const App = () => {
  const location = useLocation();
  const isHeader = ['/search', '/notification', '/intro'].includes(
    location.pathname
  );
  const isBottomTab = ['/intro'].includes(location.pathname);

  return (
    <Main>
      <div className='flex flex-col h-full'>
        {!isHeader && <Header />}
        <div className='flex-1 overflow-y-auto'>
          <Outlet />
        </div>
        {!isBottomTab && <BottomTab />}
      </div>
    </Main>
  );
};

export default App;
