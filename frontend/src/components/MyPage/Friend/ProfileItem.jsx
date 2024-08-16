import React from 'react';
import Button from '@components/@common/Button';
import useModal from '@hooks/useModal';
import ProfileModal from '@components/@common/ProfileModal';

const ProfileItem = ({
  user,
  actionText,
  onActionClick,
  buttonClassName,
  showButton = true,
  onProfileClick,
}) => {
  const { isOpen, toggleModal, closeModal } = useModal();

  const handleProfileClick = () => {
    if (onProfileClick) {
      onProfileClick(user);
    } else {
      toggleModal();
    }
  };

  return (
    <>
      <li className='flex items-center justify-between pb-3 px-3 border-b'>
        <div
          className='flex items-center space-x-4 cursor-pointer'
          onClick={handleProfileClick}
        >
          <img
            src={user?.profileImgUrl}
            alt='user'
            className='w-12 h-12 rounded-full'
          />
          <span className='text-lg'>{user?.nickName}</span>
        </div>
        {showButton && (
          <Button
            onClick={() => onActionClick(user?.memberId)}
            className={buttonClassName}
            size='small'
          >
            {actionText}
          </Button>
        )}
      </li>
      {!onProfileClick && (
        <ProfileModal
          isOpen={isOpen}
          onRequestClose={closeModal}
          user={user}
          profileImgUrl={user?.profileImgUrl}
        />
      )}
    </>
  );
};

export default ProfileItem;
