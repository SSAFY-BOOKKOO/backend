import React from 'react'; // eslint-disable-line no-unused-vars
import { Outlet } from 'react-router-dom';
// import BackButton from './components/@common/Back';
import Header from './components/layout/Header';
import Footer from './components/layout/Footer';
import Main from './components/Layout/Main';

const App = () => {
  return (
    <Main>
      <Header/>
        <Outlet/>
    </Main>
  );
};

export default App;
