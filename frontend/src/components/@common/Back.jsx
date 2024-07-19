// BackButton.js

const BackButton = () => {
  return (
    <button
      className='flex items-center text-2xl font-bold focus:outline-none'
      onClick={() => window.history.back()}
    >
      <span>&lt;</span>
    </button>
  );
};

export default BackButton;
