import React, { useState } from 'react';
import {
  getInfoByNickNameLike,
  postFollow,
  postUnfollow,
} from '@services/Member';
import WrapContainer from '@components/Layout/WrapContainer';
import SearchForm from '@components/Library/Search/SearchForm';
import ProfileItem from '@components/MyPage/Friend/ProfileItem';

const FriendSearch = () => {
  const [nickname, setNickname] = useState('');
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');
  const memberId = localStorage.getItem('MEMBER_ID');

  const handleSearch = async e => {
    e.preventDefault();
    setError('');
    setUsers([]);
    try {
      const data = await getInfoByNickNameLike(nickname);
      setUsers(data);

      if (data.length === 0) {
        setError('찾는 유저가 없습니다.');
      }
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setError('찾는 유저가 없습니다.');
      } else {
        setError('유저 검색에 실패했습니다.');
      }
    }
  };

  const handleFollowAction = async (userId, isFollow) => {
    try {
      if (isFollow) {
        await postUnfollow(userId);
      } else {
        await postFollow(userId);
      }

      setUsers(prevUsers =>
        prevUsers.map(user =>
          user.memberId === userId ? { ...user, isFollow: !isFollow } : user
        )
      );
    } catch (error) {
      console.error(error);
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
      {users.length > 0 && (
        <ul className='mt-8 space-y-4'>
          {users.map(user => (
            <ProfileItem
              key={user.memberId}
              user={user}
              actionText={user.isFollow ? '팔로잉 취소' : '팔로우'}
              onActionClick={() =>
                handleFollowAction(user.memberId, user.isFollow)
              }
              buttonClassName={
                user.isFollow
                  ? ''
                  : 'bg-pink-500 hover:bg-pink-500 active:bg-pink-400'
              }
              showButton={user.memberId !== memberId}
            />
          ))}
        </ul>
      )}
    </WrapContainer>
  );
};

export default FriendSearch;
