import React, { useState, useEffect } from 'react';
import { getInfoByNickName, postFollow, postUnfollow } from '@services/Member';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchForm from '@components/Library/Search/SearchForm';
import Button from '@components/@common/Button';

const FriendSearch = () => {
  const [nickname, setNickname] = useState('');
  const [user, setUser] = useState(null);
  const [error, setError] = useState('');
  const [isFollowing, setIsFollowing] = useState(false);
  const memberId = localStorage.getItem('MEMBER_ID');

  const handleSearch = async e => {
    e.preventDefault();
    setError('');
    setUser(null);
    setIsFollowing(false);
    try {
      const data = await getInfoByNickName(nickname);
      setUser(data);
      setIsFollowing(data.isFollowing);
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setError('찾는 유저가 없습니다.');
      } else {
        console.error('User search failed:', error);
        setError('유저 검색에 실패했습니다.');
      }
    }
  };

  const handleFollow = async memberId => {
    try {
      await postFollow(memberId);
      setIsFollowing(true);
    } catch (error) {
      console.error('Follow failed:', error);
    }
  };

  const handleUnfollow = async memberId => {
    try {
      await postUnfollow(memberId);
      setIsFollowing(false);
    } catch (error) {
      console.error('Unfollow failed:', error);
    }
  };

  return (
    <WrapContainer className='mt-4'>
      <SearchForm
        placeholder='유저를 검색하세요'
        searchText={nickname}
        setSearchText={setNickname}
        onSubmit={handleSearch}
      />
      {error && (
        <div className='flex flex-col items-center text-center bg-gray-100 space-y-4 p-4 m-4 rounded'>
          <p>{error}</p>
        </div>
      )}
      {user && (
        <div className='mt-4 flex items-center justify-between pb-3 px-3 border-b'>
          <div className='flex items-center space-x-4'>
            <img
              src={user?.profileImgUrl}
              alt='user'
              className='w-12 h-12 rounded-full'
            />
            <span className='text-lg'>{user?.nickName}</span>
          </div>
          {user.memberId !== memberId &&
            (isFollowing ? (
              <Button
                onClick={() => handleUnfollow(user.memberId)}
                className='bg-gray-500 hover:bg-gray-500 active:bg-gray-400'
                size='small'
              >
                팔로잉 취소
              </Button>
            ) : (
              <Button
                onClick={() => handleFollow(user.memberId)}
                className='bg-pink-500 hover:bg-pink-500 active:bg-pink-400'
                size='small'
              >
                팔로우
              </Button>
            ))}
        </div>
      )}
    </WrapContainer>
  );
};

export default FriendSearch;
