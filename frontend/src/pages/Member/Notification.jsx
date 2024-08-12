import React, { useEffect, useCallback, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import useNotificationInfiniteScroll from '@hooks/useNotificationInfiniteScroll';
import Spinner from '@components/@common/Spinner';
import WrapContainer from '@components/Layout/WrapContainer';
import IconButton from '@components/@common/IconButton';
import { IoArrowBack } from 'react-icons/io5';
import { FaTrashCan } from 'react-icons/fa6';
import { useNavigate } from 'react-router-dom';
import NotificationItem from '@components/Notification/NotificationItem';
import PullToRefresh from '@components/@common/PullToRefresh';
import { deleteNotification } from '@services/Member';

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

  const [isDeleting, setIsDeleting] = useState(false);
  const [selectedNotifications, setSelectedNotifications] = useState([]);

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

  const handleSelectNotification = notification => {
    if (selectedNotifications.includes(notification)) {
      setSelectedNotifications(
        selectedNotifications.filter(selected => selected !== notification)
      );
    } else {
      setSelectedNotifications([...selectedNotifications, notification]);
    }
  };

  const handleDeleteSelected = () => {
    if (selectedNotifications.length === 0) {
      return;
    }

    const deleteRequests = selectedNotifications.map(notification =>
      deleteNotification(notification.notificationId)
    );

    Promise.all(deleteRequests)
      .then(responses => {
        refetch();
        setSelectedNotifications([]);
        setIsDeleting(false);
      })
      .catch(err => {
        console.log('Error deleting notifications:', err);
      });
  };

  return (
    <WrapContainer>
      <div className='max-w-md mx-auto flex flex-col'>
        <header className='bg-white flex items-center pb-3 justify-between z-10 p-4'>
          <IconButton onClick={handleBack} icon={IoArrowBack} />
          <h1 className='text-lg font-semibold'>알림</h1>
          <IconButton
            onClick={() => setIsDeleting(!isDeleting)}
            icon={FaTrashCan}
          />
        </header>
        {isDeleting && (
          <div className='flex justify-center py-2 px-4'>
            <button
              className='bg-pink-500 text-white px-4 p-2 w-full rounded-lg'
              onClick={handleDeleteSelected}
            >
              선택한 알림 삭제
            </button>
          </div>
        )}
        <div className='flex-1'>
          <PullToRefresh onRefresh={handleRefresh}>
            <ul className='bg-white divide-y divide-gray-100'>
              {data?.pages.map((page, pageIndex) =>
                page.map(notification => (
                  <div
                    key={`${pageIndex}-${notification.notificationId}`}
                    className='flex items-center'
                  >
                    {isDeleting && (
                      <div className='flex items-center justify-center bg-white pl-4 pr-1'>
                        <input
                          type='checkbox'
                          className='accent-pink-500 form-checkbox h-6 w-6'
                          checked={selectedNotifications.includes(notification)}
                          onChange={() =>
                            handleSelectNotification(notification)
                          }
                        />
                      </div>
                    )}
                    <NotificationItem
                      notification={notification}
                      isDeleting={isDeleting}
                    />
                  </div>
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
