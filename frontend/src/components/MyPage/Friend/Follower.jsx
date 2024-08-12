import React from 'react';
import Button from '@components/@common/Button';

const Follower = ({ followers, handleRemoveFollower }) => {
  return (
    <div>
      <ul className='space-y-4'>
        {followers.map(user => (
          <li
            key={user?.memberId}
            className='flex items-center justify-between pb-3 px-3 mb-2 border-b'
          >
            <div className='flex items-center space-x-4'>
              <img
                src={user?.profileImgUrl}
                alt='user'
                className='w-12 h-12 rounded-full'
              />
              <span className='text-lg'>{user?.nickName}</span>
            </div>
            <Button
              onClick={() => handleRemoveFollower(user?.memberId)}
              className='bg-pink-500 hover:bg-pink-500 active:bg-pink-400'
              size='small'
            >
              삭제
            </Button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Follower;
