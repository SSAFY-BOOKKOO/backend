// Header.jsx
import BackButton from "../@common/Back";

const Header = ({ }) => {
  return (
    <div className="flex items-center p-3">
      <BackButton />
      {/* <h1 className="ml-4 text-xl font-medium">{title}</h1> */}
    </div>
  );
};

export default Header;
