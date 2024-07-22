import React, { useState } from 'react';
import ProfileUpdate from '@components/MyPage/Profile/ProfileUpdate.jsx';
import ProfileView from '@components/MyPage/Profile/ProfileVIew';

const ProfilePage = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [userInfo, setUserInfo] = useState({
    nickname: '유제3',
    profileImage: 'https://via.placeholder.com/150', // Placeholder image URL
    email: 'user@example.com',
    password: 'password123',
    receiveLetters: true,
    introduction: '안녕하세요! 반갑습니다.',
    favoriteCategory: '카테고리1',
  });

  const handleEdit = () => setIsEditing(true);

  const handleCancel = () => setIsEditing(false);

  const handleSave = updatedInfo => {
    setUserInfo(updatedInfo);
    setIsEditing(false);
  };

  return (
    <div>
      {isEditing ? (
        <ProfileUpdate
          userInfo={userInfo}
          onSave={handleSave}
          onCancel={handleCancel}
        />
      ) : (
        <ProfileView userInfo={userInfo} onEdit={handleEdit} />
      )}
    </div>
  );
};

export default ProfilePage;
