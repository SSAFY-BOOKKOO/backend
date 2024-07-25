import React from 'react';

const MemberProfile = ({ member }) => (
  <div className='text-center p-4 relative'>
    <div className='flex items-center justify-center flex-row space-x-8'>
      <img
        src={member.profilePicture}
        alt={member.nickname}
        className='rounded-full w-24 h-24 sm:w-32 sm:h-32'
      />
      <div className='text-left'>
        <h1 className='text-2xl sm:text-3xl font-bold text-gray-700'>
          {member.nickname}
        </h1>
        <div className='flex justify-start space-x-4 mt-2'>
          <div>
            <span className='font-bold'>{member.followers.length}</span> 팔로워
          </div>
          <div>
            <span className='font-bold'>{member.following.length}</span> 팔로잉
          </div>
        </div>
      </div>
    </div>
  </div>
);

export default MemberProfile;
