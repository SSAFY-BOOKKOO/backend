import React, { useEffect, useState } from 'react';
import { authAxiosInstance } from '@services/axiosInstance';

const MemberProfile = ({ member }) => {
  const [followersCount, setFollowersCount] = useState(0);
  const [followingCount, setFollowingCount] = useState(0);

  useEffect(() => {
    const fetchFollowerData = async () => {
      try {
        const followersResponse = await authAxiosInstance.get(
          `/members/follow/followers?memberId=${member.memberId}`
        );
        setFollowersCount(followersResponse.data.length);
      } catch (error) {
        console.error('Error fetching follower data:', error);
      }
    };

    const fetchFollowingData = async () => {
      try {
        const followingResponse = await authAxiosInstance.get(
          `/members/follow/followees?memberId=${member.memberId}`
        );
        setFollowingCount(followingResponse.data.length);
      } catch (error) {
        console.error('Error fetching following data:', error);
      }
    };

    fetchFollowerData();
    fetchFollowingData();
  }, [member.memberId]);

  return (
    <div className='text-center pb-4 relative'>
      <div className='flex items-center justify-center flex-row space-x-12'>
        <img
          src={member.profileImgUrl}
          alt={member.nickName}
          className='rounded-full w-24 h-24 sm:w-32 sm:h-32'
        />
        <div className='text-left ml-4'>
          <h1 className='text-2xl sm:text-3xl font-bold text-gray-700'>
            {member.nickName}
          </h1>
          <div className='flex justify-start space-x-8 mt-2'>
            <div>
              팔로워 <span className='font-bold'>{followersCount}</span>
            </div>
            <div>
              팔로잉 <span className='font-bold'>{followingCount}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MemberProfile;
