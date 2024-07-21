import { Outlet } from 'react-router-dom';
import Header from './components/Layout/Header';
import BottomTab from './components/Layout/BottomTab';
import Main from './components/Layout/Main';

const App = () => {
  return (
    <Main>
      <Header />
      <Outlet />
      <BottomTab />
    </Main>
  );
};

export default App;
