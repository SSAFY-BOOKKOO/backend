import React, { useEffect } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import Header from './components/Layout/Header';
import BottomTab from './components/Layout/BottomTab';
import Main from './components/Layout/Main';
import ScrollToTop from './components/@common/ScrollToTop';

const App = () => {
  const location = useLocation();
  const isHeader = [
    '/search',
    '/notification',
    '/intro',
    '/mypage/friend/search',
    '/booktalk/create',
  ].includes(location.pathname);
  const isBottomTab = ['/intro'].includes(location.pathname);

  useEffect(() => {
    const setAppHeight = () => {
      const vh = window.innerHeight * 0.01;
      document.documentElement.style.setProperty('--vh', `${vh}px`);
    };

    setAppHeight();
    window.addEventListener('resize', setAppHeight);

    return () => window.removeEventListener('resize', setAppHeight);
  }, []);

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
