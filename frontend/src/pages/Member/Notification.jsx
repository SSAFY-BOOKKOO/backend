import React, { useEffect, useCallback } from 'react';
import { useInView } from 'react-intersection-observer';
import useNotificationInfiniteScroll from '@hooks/useNotificationInfiniteScroll';
import Spinner from '@components/@common/Spinner';
import WrapContainer from '@components/Layout/WrapContainer';
import IconButton from '@components/@common/IconButton';
import { IoArrowBack, IoSettingsSharp } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';
import NotificationItem from '@components/Notification/NotificationItem';
import PullToRefresh from '@components/@common/PullToRefresh';

const Notification = () => {
  const navigate = useNavigate();
  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isLoading,
    refetch,
  } = useNotificationInfiniteScroll();
  const { ref, inView } = useInView();

  const handleBack = () => {
    navigate(-1);
  };

  const handleRefresh = useCallback(() => {
    refetch();
  }, [refetch]);

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  const handleEdit = () => {};

  return (
    <WrapContainer>
      <div className='max-w-md mx-auto h-screen flex flex-col'>
        <header className='bg-white flex items-center pb-3 justify-between z-10 p-4'>
          <IconButton onClick={handleBack} icon={IoArrowBack} />
          <h1 className='text-lg font-semibold'>알림</h1>
          <button
            className='cursor-pointer text-base font-semibold'
            onClick={handleEdit}
          >
            편집
          </button>
        </header>
        <div className='flex-'>
          <PullToRefresh onRefresh={handleRefresh}>
            <ul className='bg-white divide-y divide-gray-100'>
              {data?.pages.map((page, pageIndex) =>
                page.map(notification => (
                  <NotificationItem
                    key={`${pageIndex}-${notification.notificationId}`}
                    notification={notification}
                  />
                ))
              )}
            </ul>
            {isFetchingNextPage && <Spinner infiniteScroll />}
            <div ref={ref}></div>
          </PullToRefresh>
        </div>
      </div>
    </WrapContainer>
  );
};

export default Notification;
