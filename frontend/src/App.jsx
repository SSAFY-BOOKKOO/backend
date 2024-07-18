import React from 'react'; // eslint-disable-line no-unused-vars
import { Outlet } from 'react-router-dom';
import Main from './components/Layout/Main';

const App = () => {
  return (
    <Main>
      <Outlet />
    </Main>
  );
};

export default App;
