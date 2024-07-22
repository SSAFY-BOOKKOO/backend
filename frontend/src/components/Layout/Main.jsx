import React from 'react';

const Main = ({ children }) => (
  <div className='flex flex-col h-screen bg-white shadow-md max-w-md mx-auto'>
    {children}
  </div>
);

export default Main;
