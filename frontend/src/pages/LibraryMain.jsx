import React, { useState, useRef } from 'react';
import MemberProfile from '../components/Library/Main/MemberProfile';
import LibraryModal from '../components/Library/Main/LibraryModal';
import CreateLibraryModal from '../components/Library/Main/CreateLibraryModal';
import LibraryOptions from '../components/Library/Main/LibraryOptions';
import BookShelf from '../components/Library/Main/BookShelf';

const member = {
  nickname: 'user1',
  followers: 120,
  following: 150,
  profilePicture: 'https://via.placeholder.com/100',
};

const LibraryMain = () => {
  const dragItem = useRef();
  const dragOverItem = useRef();

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
        '미움받을 용기',
        '하루의 취향',
        '죽고 싶지만 떡볶이는 먹고 싶어',
        '그릿',
        '나미야 잡화점의 기적',
        '아몬드',
        '종의 기원',
        '멋진 신세계',
        '불편한 편의점',
        '여행의 이유',
        '지적 대화를 위한 넓고 얕은 지식',
        '마법천자문',
        '달러구트 꿈 백화점',
        '봉제인형 살인사건',
        '무례한 사람에게 웃으며 대처하는 법',
        '더 해빙',
      ],
    },
    {
      name: '서재 2',
      books: ['책 1', '책 2', '책 3', '책 4', '책 5', '책 6', '책 7'],
    },
  ]);

  const dragStart = (e, position) => {
    dragItem.current = position;
  };

  const dragEnter = (e, position) => {
    dragOverItem.current = position;
  };

  const drop = e => {
    const newList = [...libraries[activeLibrary].books];
    const dragItemValue = newList[dragItem.current];
    newList.splice(dragItem.current, 1);
    newList.splice(dragOverItem.current, 0, dragItemValue);
    dragItem.current = null;
    dragOverItem.current = null;
    setLibraries(prev => {
      const newLibraries = [...prev];
      newLibraries[activeLibrary].books = newList;
      return newLibraries;
    });
  };

  const changeLibraryName = () => {
    if (newLibraryName.trim()) {
      setLibraries(prev => {
        const newLibraries = [...prev];
        newLibraries[activeLibrary].name = newLibraryName;
        return newLibraries;
      });
      setNewLibraryName('');
      setShowModal(false);
    }
  };

  const clearLibrary = () => {
    setLibraries(prev => {
      const newLibraries = [...prev];
      newLibraries[activeLibrary].books = [];
      return newLibraries;
    });
    setShowMenu(false);
  };

  const createLibrary = () => {
    if (createLibraryName.trim()) {
      setLibraries([...libraries, { name: createLibraryName, books: [] }]);
      setActiveLibrary(libraries.length);
      setCreateLibraryName('');
      setShowCreateModal(false);
    }
  };

  return (
    <>
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
          {libraries[activeLibrary].name}
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
        clearLibrary={clearLibrary}
      />

      <BookShelf
        books={libraries[activeLibrary].books}
        dragStart={dragStart}
        dragEnter={dragEnter}
        drop={drop}
      />
    </>
  );
};

export default LibraryMain;
