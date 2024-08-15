import React from 'react';
import ProfileItem from './ProfileItem';

const Follower = ({ followers, handleRemoveFollower }) => (
  <div>
    <ul className='space-y-4'>
      {followers.map(user => (
        <ProfileItem
          key={user?.memberId}
          user={user}
          actionText='팔로워 삭제'
          onActionClick={handleRemoveFollower}
          buttonClassName='bg-pink-500 hover:bg-pink-500 active:bg-pink-400'
        />
      ))}
    </ul>
  </div>
);

export default Follower;
