import React, { useState } from 'react';
import { useSetAtom } from 'jotai';
import DeleteLibraryModal from './DeleteLibraryModal';
import SettingsModal from '../../@common/SettingsModal';
import Alert from '../../@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import CreateLibraryModal from './CreateLibraryModal';
import ChangeLibraryNameModal from './ChangeLibraryNameModal';
import ChangeFontStyleModal from './ChangeFontStyleModal'; // 추가된 모달

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
  changeFontStyle, // 폰트 스타일 변경 함수 추가
  viewOnly = false,
}) => {
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showChangeLibraryNameModal, setShowChangeLibraryNameModal] =
    useState(false);
  const [showChangeFontStyleModal, setShowChangeFontStyleModal] =
    useState(false); // 폰트 변경 모달 상태 추가
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
    {
      label: '폰트 변경',
      onClick: () => {
        setShowMenu(false);
        setShowChangeFontStyleModal(true);
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
      {!viewOnly && (
        <SettingsModal
          isOpen={isSettingsOpen}
          onClose={() => setIsSettingsOpen(false)}
          onToggle={() => setIsSettingsOpen(!isSettingsOpen)}
          actions={actions}
        />
      )}
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
      <ChangeFontStyleModal
        showModal={showChangeFontStyleModal}
        fontName={libraries[activeLibrary]?.libraryStyleDto?.fontName}
        setFontName={name => setFontName(libraries[activeLibrary].id, name)}
        fontSize={libraries[activeLibrary]?.libraryStyleDto?.fontSize}
        setFontSize={size => setFontSize(libraries[activeLibrary].id, size)}
        changeFontStyle={(name, size) =>
          changeFontStyle(libraries[activeLibrary].id, name, size)
        }
        setShowModal={setShowChangeFontStyleModal}
      />
      <Alert />
    </div>
  );
};

export default LibraryOptions;
