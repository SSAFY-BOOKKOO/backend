import React, { useState } from 'react';
import Following from '@components/MyPage/Friend/Following.jsx';
import Follower from '@components/MyPage/Friend/Follower.jsx';
import detailedUserData from '@mocks/FollowUserData';

const UserPage = () => {
  const [userData, setUserData] = useState(detailedUserData);
  const currentUser = userData.find(user => user.username === 'user1');
  const [view, setView] = useState('following');
  const handleUnfollow = username => {
    setUserData(prevData => {
      const newData = prevData.map(user => {
        if (user.username === 'user1') {
          return {
            ...user,
            following: user.following.filter(follow => follow !== username),
          };
        }
        if (user.username === username) {
          return {
            ...user,
            followers: user.followers.filter(follower => follower !== 'user1'),
          };
        }
        return user;
      });
      return newData;
    });
  };

  const handleRemoveFollower = username => {
    setUserData(prevData => {
      const newData = prevData.map(user => {
        if (user.username === 'user1') {
          return {
            ...user,
            followers: user.followers.filter(follower => follower !== username),
          };
        }
        if (user.username === username) {
          return {
            ...user,
            following: user.following.filter(follow => follow !== 'user1'),
          };
        }
        return user;
      });
      return newData;
    });
  };

  return (
    <div className='px-8'>
      <div className='flex space-x-4 mb-2'>
        <button
          onClick={() => setView('following')}
          className={`py-2 px-4 mb-2 ${view === 'following' ? 'border-b-2 border-black' : ''}`}
        >
          팔로잉
        </button>
        <button
          onClick={() => setView('followers')}
          className={`py-2 px-4 mb-2 ${view === 'followers' ? 'border-b-2 border-black' : ''}`}
        >
          팔로워
        </button>
      </div>
      {view === 'following' ? (
        <Following
          following={currentUser.following}
          handleUnfollow={handleUnfollow}
        />
      ) : (
        <Follower
          followers={currentUser.followers}
          handleRemoveFollower={handleRemoveFollower}
        />
      )}
    </div>
  );
};

export default UserPage;
