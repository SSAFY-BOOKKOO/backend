const Main = ({ children }) => (
  <div className='min-h-screen bg-white shadow-md max-w-[480px] mx-auto md:h-screen md:overflow-y-auto md:bg-gray-100'>
    <div className='md:w-full md:h-full md:bg-white md:shadow-md'>
      {children}
    </div>
  </div>
);

export default Main;
