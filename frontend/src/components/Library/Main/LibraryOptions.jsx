import React, { useState } from 'react';
import { useSetAtom } from 'jotai';
import Button from '../../@common/Button';
import DeleteLibraryModal from './DeleteLibraryModal';
import SettingsModal from '../../@common/SettingsModal';
import Alert from '../../@common/Alert';
import { alertAtom } from '@atoms/alertAtom';

const LibraryOptions = ({
  activeLibrary,
  setActiveLibrary,
  libraries,
  showMenu,
  setShowMenu,
  setShowModal,
  setShowCreateModal,
  deleteLibrary,
}) => {
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);
  const setAlert = useSetAtom(alertAtom);

  const handleDeleteLibrary = () => {
    if (libraries.length <= 1) {
      setAlert({
        isOpen: true,
        message: '서재가 하나만 남아 있어 삭제할 수 없습니다.',
      });
    } else {
      setShowDeleteModal(true);
    }
  };

  const actions = [
    {
      label: '서재명 변경',
      onClick: () => {
        setShowMenu(false);
        setShowModal(true);
      },
    },
    {
      label: '서재 삭제',
      onClick: handleDeleteLibrary,
    },
    {
      label: '서재 생성',
      onClick: () => {
        setShowMenu(false);
        setShowCreateModal(true);
      },
    },
  ];

  return (
    <div className='flex items-center justify-between w-full px-4 relative'>
      <div className='relative w-64'>
        <select
          className='block w-full p-2 border rounded-lg bg-white text-black text-center appearance-none'
          style={{ textAlignLast: 'center' }}
          value={activeLibrary}
          onChange={e => setActiveLibrary(Number(e.target.value))}
        >
          {libraries.map((library, index) => (
            <option key={index} value={index} className='text-center'>
              {library.name}
            </option>
          ))}
        </select>
      </div>
      <Button
        text='+'
        color='text-white bg-green-400 active:bg-pink-400'
        size='medium'
        onClick={() => setShowMenu(true)}
      />
      <SettingsModal
        isOpen={isSettingsOpen}
        onClose={() => setIsSettingsOpen(false)}
        onToggle={() => setIsSettingsOpen(!isSettingsOpen)}
        actions={actions}
      />
      <DeleteLibraryModal
        showDeleteModal={showDeleteModal}
        deleteLibrary={deleteLibrary}
        setShowDeleteModal={setShowDeleteModal}
      />
      <Alert />
    </div>
  );
};

export default LibraryOptions;
