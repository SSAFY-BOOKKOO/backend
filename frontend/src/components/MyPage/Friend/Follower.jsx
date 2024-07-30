import React from 'react';

const Follower = ({ followers, handleRemoveFollower }) => (
  <div>
    <ul className='space-y-4'>
      {followers.map(user => (
        <li
          key={user}
          className='flex items-center justify-between pb-3 px-3 mb-2 border-b'
        >
          <div className='flex items-center space-x-4'>
            <img
              src='https://via.placeholder.com/50'
              alt='user'
              className='w-12 h-12 rounded-full'
            />
            <span className='text-lg'>{user}</span>
          </div>
          <button
            onClick={() => handleRemoveFollower(user)}
            className='bg-pink-400 hover:bg-pink-400 text-gray-700 py-1 px-3 rounded'
          >
            팔로워 삭제
          </button>
        </li>
      ))}
    </ul>
  </div>
);

export default Follower;
