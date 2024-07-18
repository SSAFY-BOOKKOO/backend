import React from 'react'; // eslint-disable-line no-unused-vars
import { Outlet } from 'react-router-dom';
import Main from './components/layout/Main';
// import BackButton from './components/@common/Back';
import Header from './components/layout/Header';
import Footer from './components/layout/Footer';

const App = () => {
  return (
    <Main>
      <Header/>
        <Outlet/>
      <Footer/>
    </Main>
  );
};

export default App;
