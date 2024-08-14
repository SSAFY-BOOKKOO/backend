import React, { useState, useEffect } from 'react';
import ProfileUpdate from '@components/MyPage/Profile/ProfileUpdate.jsx';
import ProfileView from '@components/MyPage/Profile/ProfileView.jsx';
import PasswordUpdate from '@components/MyPage/Profile/PasswordUpdate.jsx';
import AdditionalSetting from '@components/MyPage/Profile/AdditionalSetting.jsx';
import { authAxiosInstance } from '@services/axiosInstance';
import { useAtom } from 'jotai';
import { alertAtom } from '@atoms/alertAtom';
import Alert from '@components/@common/Alert';
import Spinner from '@components/@common/Spinner'; // 스피너 컴포넌트 임포트
import { postCategories } from '@services/Book';

const ProfilePage = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [isChangingPassword, setIsChangingPassword] = useState(false);
  const [activeTab, setActiveTab] = useState('profile');
  const [member, setMember] = useState(null);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [, setAlert] = useAtom(alertAtom);

  const fetchMemberInfo = async () => {
    try {
      const memberResponse = await authAxiosInstance.get('/members/info');
      setMember(memberResponse.data);
      const categoriesResponse = await postCategories();
      setCategories(categoriesResponse);
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
    setMember(prevMember => ({
      ...prevMember,
      ...updatedInfo,
      categories: updatedInfo.categories ?? prevMember.categories,
    }));
    setIsEditing(false);
  };

  const handleChangePassword = () => setIsChangingPassword(true);
  const handleCancelChangePassword = () => setIsChangingPassword(false);

  const handleTabClick = tab => {
    setActiveTab(tab);
    setIsEditing(false);
    setIsChangingPassword(false);
  };

  const handleAdditionalSettingSave = updatedInfo => {
    setMember(prevMember => ({
      ...prevMember,
      ...updatedInfo,
    }));
    setAlert({
      isOpen: true,
      confirmOnly: true,
      message: '설정이 성공적으로 저장되었습니다.',
    });
  };

  if (loading) {
    return (
      <div className='flex justify-center items-center min-h-screen'>
        <Spinner /> {/* 로딩 중에 스피너를 표시 */}
      </div>
    );
  }

  return (
    <div className='max-w-md mx-auto'>
      <div className='flex border-b'>
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
            member={member}
            categories={categories}
            onSave={handleSave}
            onCancel={handleCancelEdit}
          />
        ) : isChangingPassword ? (
          <PasswordUpdate onCancel={handleCancelChangePassword} />
        ) : (
          <ProfileView
            member={member}
            categories={categories}
            onEdit={handleEdit}
            onChangePassword={handleChangePassword}
          />
        )
      ) : (
        <AdditionalSetting
          userInfo={member}
          onSave={handleAdditionalSettingSave}
        />
      )}
      <Alert />
    </div>
  );
};

export default ProfilePage;
