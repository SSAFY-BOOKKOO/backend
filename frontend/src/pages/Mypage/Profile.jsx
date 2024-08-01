import React, { useState, useEffect } from 'react';
import ProfileUpdate from '@components/MyPage/Profile/ProfileUpdate.jsx';
import ProfileView from '@components/MyPage/Profile/ProfileView.jsx';
import PasswordUpdate from '@components/MyPage/Profile/PasswordUpdate.jsx';
import AdditionalSetting from '@components/MyPage/Profile/AdditionalSetting.jsx';
import { authAxiosInstance } from '@services/axiosInstance';

const ProfilePage = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);
  const [activeTab, setActiveTab] = useState('profile');
  const [userInfo, setUserInfo] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchMemberInfo = async () => {
    try {
      const response = await authAxiosInstance.get(
        '/members/info?memberId=312c2435-d0b5-4607-808d-fc0e9c51b58d'
      );
      setUserInfo(response.data);
      setLoading(false);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMemberInfo();
  }, []);

  const handleEdit = () => setIsEditing(true);
  const handleCancelEdit = () => setIsEditing(false);

  const handleSave = updatedInfo => {
    setUserInfo(updatedInfo);
    setIsEditing(false);
  };

  const handleChangePassword = () => setIsChangingPassword(true);
  const handleCancelChangePassword = () => setIsChangingPassword(false);

  const handleTabClick = tab => {
    setActiveTab(tab);
    setIsEditing(false);
    setIsChangingPassword(false);
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className='max-w-md mx-auto mt-10'>
      <div className='flex border-b mb-6'>
        <button
          className={`flex-1 py-2 text-center ${
            activeTab === 'profile'
              ? 'border-b-2 border-black text-black'
              : 'text-gray-500'
          }`}
          onClick={() => handleTabClick('profile')}
        >
          프로필
        </button>
        <button
          className={`flex-1 py-2 text-center ${
            activeTab === 'settings'
              ? 'border-b-2 border-black text-black'
              : 'text-gray-500'
          }`}
          onClick={() => handleTabClick('settings')}
        >
          공개
        </button>
      </div>
      {activeTab === 'profile' ? (
        isEditing ? (
          <ProfileUpdate
            userInfo={userInfo}
            onSave={handleSave}
            onCancel={handleCancelEdit}
          />
        ) : isChangingPassword ? (
          <PasswordUpdate onCancel={handleCancelChangePassword} />
        ) : (
          <ProfileView
            userInfo={userInfo}
            onEdit={handleEdit}
            onChangePassword={handleChangePassword}
          />
        )
      ) : (
        <AdditionalSetting userInfo={userInfo} onSave={handleSave} />
      )}
    </div>
  );
};

export default ProfilePage;
