import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';

const ScrollToTop = ({ children }) => {
  const { pathname } = useLocation();
  const scrollRef = useRef(null);

  useEffect(() => {
    if (!pathname.includes('/booktalk/detail')) {
      if (scrollRef.current) {
        scrollRef.current.scrollTop = 0;
      }
    }
  }, [pathname]);

  return (
    <div ref={scrollRef} className='flex-1 overflow-y-auto overflow-x-hidden'>
      {children}
    </div>
  );
};

export default ScrollToTop;
