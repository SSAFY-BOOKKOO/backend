import React, { useState } from 'react';
import Following from '../components/MyPage/Friend/Following.jsx';
import Follower from '../components/MyPage/Friend/Follower.jsx';

// dummy data: 사용자 정보
const detailedUserData = [
  {
    username: 'userY',
    followers: ['userX', 'userC'],
    following: ['userD', 'userX'],
  },
  {
    username: 'userD',
    followers: ['userY', 'userZ', 'userA', 'userX', 'userB', 'userC'],
    following: ['userB', 'userY', 'userZ', 'userA'],
  },
  {
    username: 'userZ',
    followers: ['userD', 'userY', 'userX', 'userB'],
    following: ['userC', 'userX', 'userD', 'userB'],
  },
  {
    username: 'userA',
    followers: ['userB', 'userD'],
    following: ['userC', 'userX', 'userB'],
  },
  {
    username: 'userX',
    followers: ['userZ', 'userD', 'userY', 'userC', 'user1'],
    following: ['userZ', 'userA', 'userD', 'userY', 'userB', 'user1'],
  },
  {
    username: 'userB',
    followers: ['userC', 'userA', 'userD', 'userX', 'userZ'],
    following: ['userD', 'userX', 'userZ', 'userY'],
  },
  {
    username: 'userC',
    followers: ['userY', 'userB', 'userD', 'userX', 'userZ', 'userA', 'user1'],
    following: ['userA', 'userB', 'userD', 'user1'],
  },
  {
    username: 'user1',
    followers: ['userB', 'userX', 'userD'],
    following: ['userX', 'userC'],
  },
];

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
    <div className='p-8'>
      <h1 className='text-2xl font-bold mb-6'>{currentUser.username}'s Page</h1>
      <div className='flex space-x-4 mb-6'>
        <button
          onClick={() => setView('following')}
          className={`py-2 px-4 rounded ${view === 'following' ? 'border-b-2 border-black' : ''}`}
        >
          팔로잉
        </button>
        <button
          onClick={() => setView('followers')}
          className={`py-2 px-4 rounded ${view === 'followers' ? 'border-b-2 border-black' : ''}`}
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
