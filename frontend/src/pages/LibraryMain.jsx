import React, { useState } from 'react';
import { DndProvider } from 'react-dnd';
import { MultiBackend, TouchTransition } from 'react-dnd-multi-backend';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { TouchBackend } from 'react-dnd-touch-backend';
import MemberProfile from '../components/Library/Main/MemberProfile';
import LibraryModal from '../components/Library/Main/LibraryModal';
import CreateLibraryModal from '../components/Library/Main/CreateLibraryModal';
import LibraryOptions from '../components/Library/Main/LibraryOptions';
import BookShelf from '../components/Library/Main/BookShelf';

const HTML5toTouch = {
  backends: [
    {
      id: 'html5',
      backend: HTML5Backend,
    },
    {
      id: 'touch',
      backend: TouchBackend,
      options: { enableMouseEvents: true },
      preview: true,
      transition: TouchTransition,
    },
  ],
};

const member = {
  nickname: 'user1',
  followers: ['user2', 'user3'],
  following: ['user4', 'user5', 'user6'],
  profilePicture: 'https://via.placeholder.com/100',
};

const LibraryMain = () => {
  const [showMenu, setShowMenu] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newLibraryName, setNewLibraryName] = useState('');
  const [createLibraryName, setCreateLibraryName] = useState('');
  const [activeLibrary, setActiveLibrary] = useState(0);
  const [libraries, setLibraries] = useState([
    {
      name: '서재 1',
      books: [
        { id: 1, title: '미움받을 용기' },
        { id: 2, title: '하루의 취향' },
        { id: 3, title: '죽고 싶지만 떡볶이는 먹고 싶어' },
        { id: 4, title: '그릿' },
        { id: 5, title: '나미야 잡화점의 기적' },
        { id: 6, title: '아몬드' },
        { id: 7, title: '종의 기원' },
        { id: 8, title: '멋진 신세계' },
        { id: 9, title: '불편한 편의점' },
        { id: 10, title: '여행의 이유' },
        { id: 11, title: '지적 대화를 위한 넓고 얕은 지식' },
        { id: 12, title: '마법천자문' },
        { id: 13, title: '달러구트 꿈 백화점' },
        { id: 14, title: '봉제인형 살인사건' },
        { id: 15, title: '무례한 사람에게 웃으며 대처하는 법' },
        { id: 16, title: '더 해빙' },
      ],
    },
    {
      name: '서재 2',
      books: [
        { id: 17, title: '책 1' },
        { id: 18, title: '책 2' },
        { id: 19, title: '책 3' },
        { id: 20, title: '책 4' },
        { id: 21, title: '책 5' },
        { id: 22, title: '책 6' },
        { id: 23, title: '책 7' },
      ],
    },
  ]);

  const moveBook = (fromIndex, toIndex) => {
    const newList = [...libraries[activeLibrary].books];
    const [movedBook] = newList.splice(fromIndex, 1);
    newList.splice(toIndex, 0, movedBook);
    setLibraries(prev => {
      const newLibraries = [...prev];
      newLibraries[activeLibrary].books = newList;
      return newLibraries;
    });
  };

  const changeLibraryName = () => {
    if (newLibraryName.trim() && newLibraryName.length <= 10) {
      setLibraries(prev => {
        const newLibraries = [...prev];
        newLibraries[activeLibrary].name = newLibraryName;
        return newLibraries;
      });
      setNewLibraryName('');
      setShowModal(false);
    } else {
      alert('서재 이름은 10자 이내로 설정해야 합니다.');
    }
  };

  const deleteLibrary = () => {
    setLibraries(prev => {
      const newLibraries = prev.filter((_, index) => index !== activeLibrary);
      return newLibraries;
    });
    setActiveLibrary(0); // 첫 번째 서재로 이동
    setShowMenu(false);
  };

  const createLibrary = () => {
    if (createLibraryName.trim()) {
      if (createLibraryName.length > 10) {
        alert('서재 이름은 10자 이내로 설정해야 합니다.');
      } else {
        setLibraries([...libraries, { name: createLibraryName, books: [] }]);
        setActiveLibrary(libraries.length);
        setCreateLibraryName('');
        setShowCreateModal(false);
      }
    }
  };

  return (
    <DndProvider backend={MultiBackend} options={HTML5toTouch}>
      <div className='bg-white min-h-screen'>
        <MemberProfile member={member} />

        <LibraryModal
          showModal={showModal}
          newLibraryName={newLibraryName}
          setNewLibraryName={setNewLibraryName}
          changeLibraryName={changeLibraryName}
          setShowModal={setShowModal}
        />

        <CreateLibraryModal
          showCreateModal={showCreateModal}
          createLibraryName={createLibraryName}
          setCreateLibraryName={setCreateLibraryName}
          createLibrary={createLibrary}
          setShowCreateModal={setShowCreateModal}
        />

        <div className='text-center p-4'>
          <h2 className='text-xl sm:text-2xl font-bold text-gray-700'>
            {libraries[activeLibrary]?.name || '서재가 없습니다'}
          </h2>
        </div>

        <LibraryOptions
          activeLibrary={activeLibrary}
          setActiveLibrary={setActiveLibrary}
          libraries={libraries}
          showMenu={showMenu}
          setShowMenu={setShowMenu}
          setShowModal={setShowModal}
          setShowCreateModal={setShowCreateModal}
          deleteLibrary={deleteLibrary}
        />

        {libraries.length > 0 && (
          <BookShelf
            books={libraries[activeLibrary]?.books || []}
            moveBook={moveBook}
          />
        )}
      </div>
    </DndProvider>
  );
};

export default LibraryMain;
