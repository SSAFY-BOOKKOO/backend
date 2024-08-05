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
  const [libraries, setLibraries] = useState([]);

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
        const response = await authAxiosInstance.get('/libraries/me');
        const libraries = response.data;
        const libraryDetailsPromises = libraries.map(library =>
          authAxiosInstance.get(`/libraries/${library.id}`)
        );
        const librariesDetails = await Promise.all(libraryDetailsPromises);
        setLibraries(librariesDetails.map(response => response.data));
      } catch (error) {
        console.error(error);
      }
    };

    fetchLibraries();
  }, []);

  useEffect(() => {
    if (location.state && location.state.deleteBookId) {
      const deleteBookId = location.state.deleteBookId;
      setLibraries(prevLibraries => {
        return prevLibraries.map(library => ({
          ...library,
          books: library.books.filter(book => book.book.id !== deleteBookId),
        }));
      });
    }
  }, [location.state]);

  const moveBook = (fromIndex, toIndex) => {
    setLibraries(prevLibraries => {
      const newLibraries = prevLibraries.map(library => {
        if (library.id === libraries[activeLibrary].id) {
          const newBooks = [...library.books];
          const movedBook = newBooks.find(
            book => book.bookOrder === fromIndex + 1
          );

          if (movedBook) {
            movedBook.bookOrder = toIndex + 1;
          }

          newBooks.forEach(book => {
            if (
              book.bookOrder === toIndex + 1 &&
              book.book.id !== movedBook.book.id
            ) {
              book.bookOrder = fromIndex + 1;
            }
          });

          return {
            ...library,
            books: newBooks.sort((a, b) => a.bookOrder - b.bookOrder),
          };
        }
        return library;
      });
      return newLibraries;
    });
  };

  const changeLibraryName = async (libraryId, newName) => {
    try {
      const existingLibraryResponse = await authAxiosInstance.get(
        `/libraries/${libraryId}`
      );
      const existingLibrary = existingLibraryResponse.data;

      await authAxiosInstance.patch(`/libraries/${libraryId}`, {
        name: newName,
        libraryOrder: existingLibrary.libraryOrder,
        libraryStyleDto: {
          libraryColor: existingLibrary.libraryStyleDto.libraryColor,
        },
      });

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

      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재명이 성공적으로 변경되었습니다.',
      });
    } catch (error) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재명 변경에 실패했습니다. 다시 시도해 주세요.',
      });
      console.error(error);
    }
  };

  const deleteLibrary = async () => {
    try {
      const libraryId = libraries[activeLibrary].id;
      await authAxiosInstance.delete(`/libraries/${libraryId}`);
      setLibraries(prev => {
        const newLibraries = prev.filter((_, index) => index !== activeLibrary);
        return newLibraries;
      });
      setActiveLibrary(0);
      setShowMenu(false);
    } catch (error) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재 삭제에 실패했습니다. 다시 시도해 주세요.',
      });
      console.error(error);
    }
  };

  const createLibrary = async () => {
    if (createLibraryName.trim()) {
      if (createLibraryName.length > 10) {
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '서재 이름은 10자 이내로 설정해야 합니다.',
        });
      } else {
        try {
          const response = await authAxiosInstance.post('/libraries', {
            name: createLibraryName,
            libraryOrder: libraries.length + 1,
            libraryStyleDto: {
              libraryColor: '#FFFFFF',
            },
          });

          setLibraries([...libraries, response.data]);

          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '서재가 성공적으로 생성되었습니다.',
          });
          setCreateLibraryName('');
          setActiveLibrary(libraries.length);
        } catch (error) {
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '서재 생성에 실패했습니다. 다시 시도해 주세요.',
          });
          console.error(error);
        }
      }
    }
  };

  const handleBookClick = item => {
    navigate(`/library/${libraries[activeLibrary].id}/detail/${item.book.id}`, {
      state: { book: item.book, libraryId: libraries[activeLibrary].id },
    });
  };

  return (
    <DndProvider backend={MultiBackend} options={HTML5toTouch}>
      <div className='bg-white min-h-screen'>
        {member && <MemberProfile member={member} />}

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
