import React, { useState } from 'react';
import ProfileUpdate from '@components/MyPage/Profile/ProfileUpdate.jsx';
import ProfileView from '@components/MyPage/Profile/ProfileView.jsx';
import PasswordUpdate from '@components/MyPage/Profile/PasswordUpdate.jsx';
import AdditionalSetting from '@components/MyPage/Profile/AdditionalSetting.jsx';
import profileImgSample from '@assets/images/profile_img_sample.png';

const ProfilePage = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);
  const [activeTab, setActiveTab] = useState('profile');
  const [userInfo, setUserInfo] = useState({
    nickname: '유제3',
    profile_img_url: profileImgSample,
    email: 'user@example.com',
    receiveLetters: true,
    introduction: '안녕하세요! 반갑습니다.',
    categories: ['추리/스릴러', '로맨스'],
    one_line_review_privacy: 'public_reveal',
  });

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
