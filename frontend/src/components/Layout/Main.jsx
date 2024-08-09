import React from 'react';

const Main = ({ children }) => (
  <div className='flex flex-col bg-white shadow-md max-w-md mx-auto h-[calc(var(--vh,1vh)*100)]'>
    {children}
  </div>
);

export default Main;
