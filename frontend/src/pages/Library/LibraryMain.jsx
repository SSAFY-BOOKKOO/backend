import React, { useState, useEffect } from 'react';
import { DndProvider } from 'react-dnd';
import { MultiBackend, TouchTransition } from 'react-dnd-multi-backend';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { TouchBackend } from 'react-dnd-touch-backend';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAtom } from 'jotai';
import MemberProfile from '@components/Library/Main/MemberProfile';
import LibraryOptions from '@components/Library/Main/LibraryOptions';
import BookShelf from '@components/Library/Main/BookShelf';
import { books as initialBooks } from '@mocks/BookData';
import profileImgSample from '@assets/images/profile_img_sample.png';
import Alert from '@components/@common/Alert';
import { alertAtom } from '@atoms/alertAtom';
import { authAxiosInstance } from '@services/axiosInstance';

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

const LibraryMain = () => {
  const [showMenu, setShowMenu] = useState(false);
  const [newLibraryName, setNewLibraryName] = useState('');
  const [createLibraryName, setCreateLibraryName] = useState('');
  const [activeLibrary, setActiveLibrary] = useState(0);
  const navigate = useNavigate();
  const location = useLocation();
  const [, setAlert] = useAtom(alertAtom);
  const [member, setMember] = useState(null);
  const [testLibrary, setTestLibrary] = useState(null);

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const response = await authAxiosInstance.get('/members/info');
        setMember(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchMemberInfo();
  }, []);

  useEffect(() => {
    const fetchLibraries = async () => {
      try {
        const response = await authAxiosInstance.get('/libraries/me/');
        setTestLibrary(response.data);
        console.log(response.data); // Fetch한 데이터를 콘솔에 출력
      } catch (error) {
        console.error(error);
      }
    };

    fetchLibraries();
  }, []);

  const [libraries, setLibraries] = useState([
    {
      name: '서재 1',
      id: 1,
      books: initialBooks.filter(book => book.libraryId === 1),
    },
    {
      name: '서재 2',
      id: 2,
      books: initialBooks.filter(book => book.libraryId === 2),
    },
  ]);

  useEffect(() => {
    if (location.state && location.state.deleteBookId) {
      const deleteBookId = location.state.deleteBookId;
      setLibraries(prevLibraries => {
        return prevLibraries.map(library => ({
          ...library,
          books: library.books.filter(book => book.bookId !== deleteBookId),
        }));
      });
    }
  }, [location.state]);

  const moveBook = (fromIndex, toIndex) => {
    setLibraries(prevLibraries => {
      const newLibraries = prevLibraries.map(library => {
        if (library.id === libraries[activeLibrary].id) {
          const newBooks = [...library.books];
          const movedBook = newBooks.find(book => book.slotId === fromIndex);

          if (movedBook) {
            movedBook.slotId = toIndex;
          }

          newBooks.forEach(book => {
            if (book.slotId === toIndex && book.bookId !== movedBook.bookId) {
              book.slotId = fromIndex;
            }
          });

          return {
            ...library,
            books: newBooks,
          };
        }
        return library;
      });
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
    } else {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재 이름은 1자이상 10자 이하로 설정해야 합니다.',
      });
    }
  };

  const deleteLibrary = () => {
    setLibraries(prev => {
      const newLibraries = prev.filter((_, index) => index !== activeLibrary);
      return newLibraries;
    });
    setActiveLibrary(0);
    setShowMenu(false);
  };

  const createLibrary = () => {
    if (createLibraryName.trim()) {
      if (createLibraryName.length > 10) {
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '서재 이름은 10자 이내로 설정해야 합니다.',
        });
      } else {
        setLibraries([...libraries, { name: createLibraryName, books: [] }]);
        setActiveLibrary(libraries.length);
        setCreateLibraryName('');
      }
    }
  };

  const handleBookClick = book => {
    navigate(`/library/detail/${book.bookId}`, { state: { book } });
  };

  return (
    <DndProvider backend={MultiBackend} options={HTML5toTouch}>
      <div className='bg-white min-h-screen'>
        <MemberProfile member={member} />

        <LibraryOptions
          activeLibrary={activeLibrary}
          setActiveLibrary={setActiveLibrary}
          libraries={libraries}
          setShowMenu={setShowMenu}
          deleteLibrary={deleteLibrary}
          createLibraryName={createLibraryName}
          setCreateLibraryName={setCreateLibraryName}
          createLibrary={createLibrary}
          newLibraryName={newLibraryName}
          setNewLibraryName={setNewLibraryName}
          changeLibraryName={changeLibraryName}
        />

        {libraries.length > 0 && (
          <BookShelf
            books={libraries[activeLibrary]?.books || []}
            moveBook={moveBook}
            onBookClick={handleBookClick}
          />
        )}
      </div>
      <Alert />
    </DndProvider>
  );
};

export default LibraryMain;
