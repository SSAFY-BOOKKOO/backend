import React from 'react';
import ProfileItem from './ProfileItem';

const Following = ({ following, handleUnfollow }) => (
  <div>
    <ul className='space-y-4'>
      {following.map(user => (
        <ProfileItem
          key={user?.memberId}
          user={user}
          actionText='팔로잉 취소'
          onActionClick={handleUnfollow}
          buttonClassName='bg-pink-500 hover:bg-pink-500 active:bg-pink-400'
        />
      ))}
    </ul>
  </div>
);

export default Following;
