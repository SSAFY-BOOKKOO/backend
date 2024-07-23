import React from 'react';

const notifications = [
  '유저3 님이 댓글을 작성했습니다.',
  '댓글을 작성한 유저1님이 한줄평을 남기셨습니다.',
];

const Notification = () => {
  return (
    <div className='max-w-md mx-auto mt-10'>
      <div className='flex items-center justify-between mb-4'>
        <h1 className='text-2xl font-bold'>알림 목록</h1>
        <div className='relative'>
          <button className='relative w-8 h-8 bg-gray-200 rounded-full'>
            <img src='@assets\icons\notification.png' alt='notification' />
          </button>
          <span className='absolute top-0 right-0 inline-block w-5 h-5 bg-red-600 text-white text-xs font-medium text-center rounded-full'>
            {notifications.length}
          </span>
        </div>
      </div>
      <ul className='bg-white shadow rounded-lg divide-y divide-gray-200'>
        {notifications.map((notification, index) => (
          <li key={index} className='p-4'>
            {notification}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Notification;
