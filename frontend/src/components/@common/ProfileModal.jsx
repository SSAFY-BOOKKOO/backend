import React, { useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useNavigate } from 'react-router-dom';
import { authAxiosInstance } from '@services/axiosInstance';

Modal.setAppElement('#root');

const ProfileModal = ({
  isOpen,
  onRequestClose,
  profileImgUrl,
  nickname,
  introduction,
}) => {
  const navigate = useNavigate();
  const [isOwnProfile, setIsOwnProfile] = useState(false);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await authAxiosInstance.get('/members/info');
        if (response.data.nickName === nickname) {
          setIsOwnProfile(true);
        }
      } catch (error) {
        console.error('Failed to fetch user info:', error);
      }
    };

    fetchUserInfo();
  }, [nickname]);

  const handleLibraryNavigation = () => {
    if (isOwnProfile) {
      navigate('/');
    } else {
      navigate('/library', { state: { nickname } });
    }
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onRequestClose}
      shouldCloseOnOverlayClick={true}
      contentLabel='Profile Modal'
      className='flex items-center justify-center fixed inset-0 z-50'
      overlayClassName='fixed inset-0 bg-black bg-opacity-50 z-50'
    >
      <div className='bg-white rounded-lg p-6 shadow-lg w-72 relative flex flex-col items-center'>
        <img
          src={profileImgUrl}
          alt='Profile'
          className='w-24 h-24 rounded-full mb-4'
        />
        <p className='text-lg font-semibold'>{nickname}</p>
        <p className='text-sm text-gray-600 mb-4 text-center'>{introduction}</p>
        <button
          onClick={handleLibraryNavigation}
          className='mt-2 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600'
        >
          {isOwnProfile ? '내 서재로 이동' : '서재 구경하러가기'}
        </button>
        <button
          onClick={onRequestClose}
          className='absolute top-2 right-2 text-gray-400 hover:text-gray-600 text-2xl'
        >
          &times;
        </button>
      </div>
    </Modal>
  );
};

export default ProfileModal;
