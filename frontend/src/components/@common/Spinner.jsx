import React from 'react';
import { useAtomValue } from 'jotai';
import { isLoadingAtom } from '@atoms/loadingAtom';

const Spinner = ({ infiniteScroll = false, pullToRefresh = false }) => {
  const isLoading = useAtomValue(isLoadingAtom);

  if (!infiniteScroll && !pullToRefresh && !isLoading) return null;

  const positionClasses =
    infiniteScroll || pullToRefresh
      ? 'w-full py-4 flex justify-center'
      : 'fixed inset-0 flex items-center justify-center bg-black bg-opacity-10 z-50';

  return (
    <div className={positionClasses}>
      <div className='flex space-x-1'>
        <div className='w-3 h-3 bg-green-400 rounded-full animate-bounce'></div>
        <div className='w-3 h-3 bg-pink-500 rounded-full animate-bounce200'></div>
        <div className='w-3 h-3 bg-green-400 rounded-full animate-bounce400'></div>
      </div>
    </div>
  );
};

export default Spinner;
