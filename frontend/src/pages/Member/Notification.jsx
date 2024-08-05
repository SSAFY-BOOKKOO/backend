import React, { useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import useNotificationInfiniteScroll from '@hooks/useNotificationInfiniteScroll';
import Spinner from '@components/@common/Spinner';
import WrapContainer from '@components/Layout/WrapContainer';
import IconButton from '@components/@common/IconButton';
import { IoArrowBack, IoSettingsSharp } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';
import NotificationItem from '@components/Notification/NotificationItem';

const Notification = () => {
  const navigate = useNavigate();
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isLoading } =
    useNotificationInfiniteScroll();
  const { ref, inView } = useInView();

  const handleBack = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  return (
    <WrapContainer>
      <div className='max-w-md mx-auto mt-4'>
        <header className='bg-white flex items-center pb-3 justify-between'>
          <IconButton onClick={handleBack} icon={IoArrowBack} />
          <h1 className='text-lg font-semibold'>알림</h1>
          <IconButton onClick={() => {}} icon={IoSettingsSharp} />
        </header>
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
      </div>
    </WrapContainer>
  );
};

export default Notification;
