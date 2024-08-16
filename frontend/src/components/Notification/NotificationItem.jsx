import React, { useState } from 'react';
import { FaRegBell } from 'react-icons/fa6';
import { useNavigate } from 'react-router-dom';
import { formatRelativeTime } from '@utils/formatTime';
import ProfileModal from '@components/@common/ProfileModal';
import useModal from '@hooks/useModal';

const NotificationItem = ({ notification }) => {
  const navigate = useNavigate();
  const { isOpen, toggleModal, closeModal } = useModal();

  const renderNotification = () => {
    switch (notification?.notificationType) {
      case 'follow':
        return (
          <>
            새로운 독서 친구!
            <br />
            {notification?.nickName}님이 회원님을 팔로우했어요.
          </>
        );
      case 'curation':
        return (
          <>
            당신을 위한 책 선물!
            <br />
            {notification?.nickName}님으로부터 큐레이션 레터가 도착했습니다.
          </>
        );
      case 'community':
        return (
          <>
            {notification.title} 북톡방이 곧 사라질 예정입니다.
            <br />
            소중한 대화들을 되돌아보세요.
          </>
        );
      default:
        return `새로운 소식이 있어요! 지금 확인해보세요.`;
    }
  };

  const handlePageMove = e => {
    e.stopPropagation();

    switch (notification?.notificationType) {
      case 'follow':
        toggleModal();
        return;
      case 'curation':
        navigate(`/curation/letter/${notification?.curationId}`);
        return;
      case 'community':
        navigate(`/booktalk/detail/${notification?.communityId}`);
        return;
      default:
        return;
    }
  };

  return (
    <>
      <li
        className='px-4 py-6 flex items-center justify-start'
        onClick={handlePageMove}
      >
        <div className='flex items-center justify-center bg-green-400 rounded-full p-3 mr-4'>
          <FaRegBell className='text-white' />
        </div>
        <div className='flex-1 cursor-pointer'>
          <p className='text-base text-gray-600'>{renderNotification()}</p>
          <p className='text-xs text-gray-400 mt-1'>
            {notification?.createdAt === undefined
              ? ''
              : formatRelativeTime(notification?.createdAt)}
          </p>
        </div>
      </li>
      {notification?.notificationType === 'follow' && (
        <ProfileModal
          isOpen={isOpen}
          onRequestClose={closeModal}
          user={{
            nickName: notification?.nickName,
            memberId: notification?.memberId,
          }}
          profileImgUrl={notification?.profileImgUrl}
        />
      )}
    </>
  );
};

export default NotificationItem;
