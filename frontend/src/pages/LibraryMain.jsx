import React, { useState, useEffect } from 'react';
import { DndProvider } from 'react-dnd';
import { MultiBackend, TouchTransition } from 'react-dnd-multi-backend';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { TouchBackend } from 'react-dnd-touch-backend';
import { useNavigate, useLocation } from 'react-router-dom';
import MemberProfile from '../components/Library/Main/MemberProfile';
import LibraryModal from '../components/Library/Main/LibraryModal';
import CreateLibraryModal from '../components/Library/Main/CreateLibraryModal';
import LibraryOptions from '../components/Library/Main/LibraryOptions';
import BookShelf from '../components/Library/Main/BookShelf';
import { books as initialBooks } from '../mocks/BookData'; // 데이터를 가져옵니다

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
  const navigate = useNavigate();
  const location = useLocation();

  const [libraries, setLibraries] = useState([
    {
      name: '서재 1',
      books: initialBooks.slice(0, 5), // 첫 5권을 서재 1에 배치합니다
    },
    {
      name: '서재 2',
      books: initialBooks.slice(5, 10), // 나머지 5권을 서재 2에 배치합니다
    },
  ]);

  useEffect(() => {
    if (location.state && location.state.deleteBookId) {
      const deleteBookId = location.state.deleteBookId;
      setLibraries(prevLibraries => {
        return prevLibraries.map(library => ({
          ...library,
          books: library.books.filter(book => book.book_id !== deleteBookId),
        }));
      });
    }
  }, [location.state]);

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

  const handleBookClick = book => {
    navigate(`/library/detail/${book.book_id}`, { state: { book } });
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
            onBookClick={handleBookClick}
          />
        )}
      </div>
    </DndProvider>
  );
};

export default LibraryMain;
