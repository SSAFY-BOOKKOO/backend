import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Following from '@components/MyPage/Friend/Following.jsx';
import Follower from '@components/MyPage/Friend/Follower.jsx';
import { getFollowers, getFollowings, postUnfollow } from '@services/Member';
import WrapContainer from '@components/Layout/WrapContainer';
import Button from '@components/@common/Button';
import { deleteFollower } from '@services/Member';

const Friend = () => {
  const [view, setView] = useState('following');
  const [followings, setFollowings] = useState([]);
  const [followers, setFollowers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    handleGetFollowers();
    handleGetFollowings();
  }, []);

  const handleGetFollowers = async () => {
    try {
      const data = await getFollowers();
      setFollowers(data);
    } catch (error) {}
  };

  const handleGetFollowings = async () => {
    try {
      const data = await getFollowings();
      setFollowings(data);
    } catch (error) {}
  };

  const handleUnfollow = async memberId => {
    try {
      await postUnfollow(memberId);
      handleGetFollowings(); // 새로고침
    } catch (error) {
      console.error('unfollow', error);
    }
  };

  const handleRemoveFollower = async memberId => {
    try {
      await deleteFollower(memberId);
      handleGetFollowers(); //새로고침
    } catch (error) {
      console.error('remove follower', error);
    }
  };

  return (
    <WrapContainer className='mt-4'>
      <div className='flex space-x-4 mb-2 justify-between items-center'>
        <div>
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

        <Button
          onClick={() => navigate('/mypage/friend/search')}
          size='small'
          className=' mb-2'
        >
          친구 찾기
        </Button>
      </div>
      {view === 'following' ? (
        <Following following={followings} handleUnfollow={handleUnfollow} />
      ) : (
        <Follower
          followers={followers}
          handleRemoveFollower={handleRemoveFollower}
        />
      )}
    </WrapContainer>
  );
};

export default Friend;
