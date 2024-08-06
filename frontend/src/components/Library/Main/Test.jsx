import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authAxiosInstance } from '@services/axiosInstance';

const TestPage = () => {
  const [profileImgUrl, setProfileImgUrl] = useState('');
  const [nickname, setNickname] = useState('');
  const memberId = '5c0bf9bf-f323-495b-9866-1684e2293e77';
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const response = await authAxiosInstance.get(`/members/info`, {
          params: { memberId },
        });
        const memberData = response.data;
        setProfileImgUrl(memberData.profileImgUrl);
        setNickname(memberData.nickName);
      } catch (error) {
        console.error('멤버 정보 가져오기 실패:', error);
      }
    };

    fetchMemberInfo();
  }, [memberId]);

  const handleProfileClick = () => {
    console.log('Navigating with nickname:', nickname);
    navigate('/', { state: { nickname } });
  };

  return (
    <div className='flex justify-center items-center min-h-screen bg-gray-100'>
      <p>{profileImgUrl}</p>;
      {profileImgUrl ? (
        <img
          src={profileImgUrl}
          alt='Profile'
          className='w-32 h-32 rounded-full cursor-pointer'
          onClick={handleProfileClick}
        />
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default TestPage;
