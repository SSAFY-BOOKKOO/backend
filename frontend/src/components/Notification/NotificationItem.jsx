import React from 'react';
import { FaRegBell } from 'react-icons/fa6';

const NotificationItem = ({ notification }) => {
  const renderNotification = () => {
    switch (notification?.notificationType) {
      case 'follow':
        return `${notification?.nickName}님이 회원님을 팔로우했습니다.`;
      case 'curation':
        return `큐레이션 레터가 도착했습니다.`;
      case 'community':
        return `회원님이 참여한 북톡이 사라질 예정입니다.`;
      default:
        return `알 수 없는 알림입니다.`;
    }
  };

  return (
    <li className='px-4 py-6 flex items-center'>
      <div className='bg-yellow-400 rounded-full p-2 mr-4'>
        <FaRegBell className='text-white' />
      </div>
      <div className='flex-1'>
        <p className='text-sm text-gray-600'>{renderNotification()}</p>
        <p className='text-xs text-gray-400 mt-1'></p>
      </div>
    </li>
  );
};

export default NotificationItem;
