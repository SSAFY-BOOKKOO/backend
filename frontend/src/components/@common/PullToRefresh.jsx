import React, { useState, useRef, useCallback, useEffect } from 'react';
import Spinner from '@components/@common/Spinner';

const PullToRefresh = ({ onRefresh, children }) => {
  const [isPulling, setIsPulling] = useState(false);
  const [pullDistance, setPullDistance] = useState(0);
  const contentRef = useRef(null);
  const startY = useRef(0);

  const handleTouchStart = useCallback(e => {
    startY.current = e.touches[0].clientY;
  }, []);

  const handleTouchMove = useCallback(e => {
    const currentY = e.touches[0].clientY;
    const diff = currentY - startY.current;

    if (diff > 0 && contentRef.current.scrollTop === 0) {
      setIsPulling(true);
      setPullDistance(diff);
    }
  }, []);

  const handleTouchEnd = useCallback(() => {
    if (isPulling && pullDistance > 70) {
      onRefresh();
    }
    setIsPulling(false);
    setPullDistance(0);
  }, [isPulling, pullDistance, onRefresh]);

  useEffect(() => {
    const currentRef = contentRef.current;
    const options = { passive: false };

    const touchMoveHandler = e => {
      if (isPulling) {
        e.preventDefault();
      }
    };

    currentRef.addEventListener('touchmove', touchMoveHandler, options);

    return () => {
      currentRef.removeEventListener('touchmove', touchMoveHandler, options);
    };
  }, [isPulling]);

  return (
    <div
      ref={contentRef}
      onTouchStart={handleTouchStart}
      onTouchMove={handleTouchMove}
      onTouchEnd={handleTouchEnd}
      style={{ overscrollBehavior: 'contain' }}
    >
      {isPulling && (
        <div
          className='text-center py-2'
          style={{ transform: `translateY(${pullDistance / 2}px)` }}
        >
          <Spinner pullToRefresh={true} />
        </div>
      )}
      {children}
    </div>
  );
};

export default PullToRefresh;
