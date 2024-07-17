const WrapContainer = ({ children }) => {
  return (
    <div className='max-w-[50rem] mx-auto px-4 box-border w-full'>
      {children}
    </div>
  );
};

export default WrapContainer;
