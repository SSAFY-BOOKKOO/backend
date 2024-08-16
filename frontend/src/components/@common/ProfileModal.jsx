import React, { useEffect, useState } from 'react';
import Modal from 'react-modal';
import { useNavigate } from 'react-router-dom';
import IconButton from '@components/@common/IconButton';
import Button from '@components/@common/Button';
import { IoCloseSharp } from 'react-icons/io5';

Modal.setAppElement('#root');

const ProfileModal = ({
  isOpen,
  onRequestClose,
  profileImgUrl,
  user,
  introduction,
}) => {
  const navigate = useNavigate();
  const memberId = localStorage.getItem('MEMBER_ID');
  const [isOwnProfile, setIsOwnProfile] = useState(false);

  useEffect(() => {
    if (memberId === user?.memberId) {
      setIsOwnProfile(true);
    }
  }, [user]);

  const handleLibraryNavigation = () => {
    if (isOwnProfile) {
      navigate('/');
    } else {
      navigate('/library', { state: { nickname: user?.nickName } });
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
        <p className='text-lg font-semibold'>{user?.nickName}</p>
        <p className='text-sm text-gray-600 mb-4 text-center'>{introduction}</p>
        <Button onClick={handleLibraryNavigation}>
          {isOwnProfile ? '내 서재로 이동' : '서재 구경하러가기'}
        </Button>
        <IconButton
          onClick={onRequestClose}
          icon={IoCloseSharp}
          className='absolute top-2 right-2 text-gray-400 hover:text-gray-600 text-2xl'
        />
      </div>
    </Modal>
  );
};

export default ProfileModal;
