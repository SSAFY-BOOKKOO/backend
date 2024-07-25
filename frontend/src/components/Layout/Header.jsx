// Header.jsx
import BackButton from '../@common/Back';

const Header = ({}) => {
  return (
    <header className='flex items-center p-3 w-full '>
      <BackButton />
      {/* <h1 className="ml-4 text-xl font-medium">{title}</h1> */}
    </header>
  );
};

export default Header;
