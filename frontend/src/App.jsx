import React from 'react'; // eslint-disable-line no-unused-vars
import { Outlet } from 'react-router-dom';
// import BackButton from './components/@common/Back';
import Header from './components/Layout/Header';
import Footer from './components/Layout/Footer';
import Main from './components/Layout/Main';

const App = () => {
  return (
    <Main>
      <Header />
      <Outlet />
    </Main>
  );
};

export default App;
