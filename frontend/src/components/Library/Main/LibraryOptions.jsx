import React, { useState } from 'react';
import { useSetAtom } from 'jotai';
import DeleteLibraryModal from './DeleteLibraryModal';
import SettingsModal from '../../@common/SettingsModal';
import Alert from '../../@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import CreateLibraryModal from './CreateLibraryModal';
import ChangeLibraryNameModal from './ChangeLibraryNameModal';
import { authAxiosInstance } from '@services/axiosInstance';

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

  const changeLibraryName = async (libraryId, newName) => {
    try {
      // 기존 서재 데이터를 먼저 가져옵니다.
      const existingLibraryResponse = await authAxiosInstance.get(
        `/libraries/${libraryId}`
      );
      const existingLibrary = existingLibraryResponse.data;

      // 새로운 이름으로 서재를 업데이트 합니다.
      await authAxiosInstance.put(`/libraries/${libraryId}`, {
        name: newName,
        libraryOrder: existingLibrary.libraryOrder,
        libraryStyleDto: {
          libraryColor: existingLibrary.libraryStyleDto.libraryColor,
        },
      });

      // 로컬 상태를 업데이트 합니다.
      setLibraries(prev => {
        const newLibraries = [...prev];
        const libraryIndex = newLibraries.findIndex(
          lib => lib.id === libraryId
        );
        if (libraryIndex !== -1) {
          newLibraries[libraryIndex].name = newName;
        }
        return newLibraries;
      });

      // 성공 알림을 표시합니다.
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재명이 성공적으로 변경되었습니다.',
      });
    } catch (error) {
      // 오류 알림을 표시합니다.
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재명 변경에 실패했습니다. 다시 시도해 주세요.',
      });
      console.error(error);
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
