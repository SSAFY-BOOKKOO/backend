import React, { useState } from 'react';
import { useSetAtom } from 'jotai';
import DeleteLibraryModal from './DeleteLibraryModal';
import SettingsModal from '../../@common/SettingsModal';
import Alert from '../../@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import CreateLibraryModal from './CreateLibraryModal';
import ChangeLibraryNameModal from './ChangeLibraryNameModal';
import CaptureButton from './CaptureButton';

const LibraryOptions = ({
  activeLibrary,
  setActiveLibrary,
  libraries,
  setShowMenu,
  deleteLibrary,
  createLibraryName,
  setCreateLibraryName,
  createLibrary,
  newLibraryName,
  setNewLibraryName,
  changeLibraryName,
  viewOnly = false,
  libraryRef,
}) => {
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showChangeLibraryNameModal, setShowChangeLibraryNameModal] =
    useState(false);
  const setAlert = useSetAtom(alertAtom);

  const handleDeleteLibrary = () => {
    if (libraries.length <= 1) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재가 하나만 남아 있어 삭제할 수 없습니다.',
      });
    } else {
      setShowDeleteModal(true);
    }
  };

  const handleCreateLibraryOption = () => {
    if (libraries.length >= 3) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재는 최대 3개까지만 생성할 수 있습니다.',
      });
    } else {
      setShowMenu(false);
      setShowCreateModal(true);
    }
  };

  const actions = [
    {
      label: '서재명 변경',
      onClick: () => {
        setShowMenu(false);
        setShowChangeLibraryNameModal(true);
      },
    },
    {
      label: '서재 삭제',
      onClick: handleDeleteLibrary,
    },
    {
      label: '서재 생성',
      onClick: handleCreateLibraryOption,
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
      <div>
        <div className='p-1 mr-3 mt-1'>
          <CaptureButton targetRef={libraryRef} />
        </div>
        {!viewOnly && (
          <SettingsModal
            isOpen={isSettingsOpen}
            onClose={() => setIsSettingsOpen(false)}
            onToggle={() => setIsSettingsOpen(!isSettingsOpen)}
            actions={actions}
          />
        )}
      </div>
      <DeleteLibraryModal
        showDeleteModal={showDeleteModal}
        deleteLibrary={deleteLibrary}
        setShowDeleteModal={setShowDeleteModal}
      />
      <CreateLibraryModal
        showCreateModal={showCreateModal}
        createLibraryName={createLibraryName}
        setCreateLibraryName={setCreateLibraryName}
        createLibrary={createLibrary}
        setShowCreateModal={setShowCreateModal}
      />
      <ChangeLibraryNameModal
        showModal={showChangeLibraryNameModal}
        newLibraryName={newLibraryName}
        setNewLibraryName={setNewLibraryName}
        changeLibraryName={() =>
          changeLibraryName(libraries[activeLibrary].id, newLibraryName)
        }
        setShowModal={setShowChangeLibraryNameModal}
      />
      <Alert />
    </div>
  );
};

export default LibraryOptions;
