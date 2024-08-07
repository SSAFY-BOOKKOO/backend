import React from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Header from './components/Layout/Header';
import BottomTab from './components/Layout/BottomTab';
import Main from './components/Layout/Main';
import ScrollToTop from './components/@common/ScrollToTop';

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
        <ScrollToTop>
          <Outlet />
        </ScrollToTop>
        {!isBottomTab && <BottomTab />}
      </div>
    </Main>
  );
};

export default App;
