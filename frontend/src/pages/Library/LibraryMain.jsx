import React, { useState, useEffect, useRef } from 'react';
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
import Spinner from '@components/@common/Spinner';
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
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
  const [, setAlert] = useAtom(alertAtom);
  const [member, setMember] = useState(null);
  const [libraries, setLibraries] = useState([]);
  const [bookChanges, setBookChanges] = useState([]);
  const libraryRef = useRef(null);

  const fetchLibraries = async () => {
    try {
      const response = await authAxiosInstance.get(`/libraries/me`);
      const libraries = response.data;
      const libraryDetailsPromises = libraries.map(library =>
        authAxiosInstance.get(`/libraries/${library.id}`)
      );
      const librariesDetails = await Promise.all(libraryDetailsPromises);
      setLibraries(librariesDetails.map(response => response.data));
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchMemberInfo = async () => {
    try {
      const response = await authAxiosInstance.get('/members/info');
      setMember(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('ACCESS_TOKEN');
    if (token) {
      fetchLibraries();
      fetchMemberInfo();
    }
  }, []);

  useEffect(() => {
    if (location.state && location.state.deleteBookId) {
      const deleteBookId = location.state.deleteBookId;
      setLibraries(prevLibraries =>
        prevLibraries.map(library => ({
          ...library,
          books: library.books.filter(book => book.book.id !== deleteBookId),
        }))
      );
    }
  }, [location.state]);

  const moveBook = (fromIndex, toIndex) => {
    const libraryId = libraries[activeLibrary].id;
    setLibraries(prevLibraries => {
      const newLibraries = prevLibraries.map(library => {
        if (library.id === libraryId) {
          const newBooks = [...library.books];
          const movedBookIndex = newBooks.findIndex(
            book => book.bookOrder === fromIndex + 1
          );
          newBooks[movedBookIndex].bookOrder = toIndex + 1;

          newBooks.forEach(book => {
            if (
              book.bookOrder === toIndex + 1 &&
              book.book.id !== newBooks[movedBookIndex].book.id
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

      setBookChanges(prevChanges => [
        ...prevChanges,
        { fromIndex, toIndex, libraryId },
      ]);

      return newLibraries;
    });
  };

  useEffect(() => {
    const handleSaveChanges = async () => {
      if (bookChanges.length > 0) {
        try {
          const libraryId = libraries[activeLibrary].id;
          const changesToApply = libraries[activeLibrary].books.map(book => ({
            bookOrder: book.bookOrder,
            bookColor: book.bookColor,
            startAt: book.startAt,
            endAt: book.endAt,
            status: book.status,
            rating: book.rating,
            bookId: book.book.id,
          }));

          await authAxiosInstance.put(
            `/libraries/${libraryId}/books`,
            changesToApply
          );
        } catch (error) {
          console.error('책 순서 변경에 실패했습니다:', error);
          setAlert({
            isOpen: true,
            confirmOnly: true,
            message: '책 순서 변경에 실패했습니다. 다시 시도해 주세요.',
          });
        }
      }
    };

    return () => {
      handleSaveChanges();
    };
  }, [bookChanges, libraries, activeLibrary, setAlert]);

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
          fontName: existingLibrary.libraryStyleDto.fontName,
          fontSize: existingLibrary.libraryStyleDto.fontSize,
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

  const changeFontStyle = async (libraryId, fontName, fontSize) => {
    try {
      const existingLibraryResponse = await authAxiosInstance.get(
        `/libraries/${libraryId}`
      );
      const existingLibrary = existingLibraryResponse.data;

      await authAxiosInstance.patch(`/libraries/${libraryId}`, {
        libraryStyleDto: {
          libraryColor: existingLibrary.libraryStyleDto.libraryColor,
          fontName: fontName || existingLibrary.libraryStyleDto.fontName,
          fontSize: fontSize || existingLibrary.libraryStyleDto.fontSize,
        },
      });

      setLibraries(prev => {
        const newLibraries = [...prev];
        const libraryIndex = newLibraries.findIndex(
          lib => lib.id === libraryId
        );
        if (libraryIndex !== -1) {
          newLibraries[libraryIndex].libraryStyleDto.fontName = fontName;
          newLibraries[libraryIndex].libraryStyleDto.fontSize = fontSize;
        }
        return newLibraries;
      });

      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '폰트 스타일이 성공적으로 변경되었습니다.',
      });
    } catch (error) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '폰트 스타일 변경에 실패했습니다. 다시 시도해 주세요.',
      });
      console.error(error);
    }
  };

  const deleteLibrary = async () => {
    try {
      const libraryId = libraries[activeLibrary].id;
      await authAxiosInstance.delete(`/libraries/${libraryId}`);
      setLibraries(prev => prev.filter((_, index) => index !== activeLibrary));
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
      } else if (libraries.length >= 3) {
        setAlert({
          isOpen: true,
          confirmOnly: true,
          message: '서재는 최대 3개까지만 생성할 수 있습니다.',
        });
      } else {
        try {
          const response = await authAxiosInstance.post('/libraries', {
            name: createLibraryName,
            libraryOrder: libraries.length + 1,
            libraryStyleDto: {
              libraryColor: '#FFFFFF',
              fontName: 'default',
              fontSize: '0',
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
      state: { nickname: member.nickName },
    });
  };

  if (isLoading) {
    return (
      <div className='flex items-center justify-center min-h-screen'>
        <Spinner />
      </div>
    );
  }

  const currentLibraryStyleDto = libraries[activeLibrary]?.libraryStyleDto;

  const changeLibraryColor = async (libraryId, colorClassName) => {
    try {
      const existingLibraryResponse = await authAxiosInstance.get(
        `/libraries/${libraryId}`
      );
      const existingLibrary = existingLibraryResponse.data;

      await authAxiosInstance.patch(`/libraries/${libraryId}`, {
        libraryStyleDto: {
          ...existingLibrary.libraryStyleDto,
          libraryColor: colorClassName,
        },
      });

      setLibraries(prev => {
        const newLibraries = [...prev];
        const libraryIndex = newLibraries.findIndex(
          lib => lib.id === libraryId
        );
        if (libraryIndex !== -1) {
          newLibraries[libraryIndex].libraryStyleDto.libraryColor =
            colorClassName;
        }
        return newLibraries;
      });

      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재 색이 성공적으로 변경되었습니다.',
      });
    } catch (error) {
      setAlert({
        isOpen: true,
        confirmOnly: true,
        message: '서재 색 변경에 실패했습니다. 다시 시도해 주세요.',
      });
      console.error(error);
    }
  };

  return (
    <DndProvider backend={MultiBackend} options={HTML5toTouch}>
      <div className='bg-white'>
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
          libraryRef={libraryRef}
          changeFontStyle={changeFontStyle}
          changeLibraryColor={changeLibraryColor}
        />
        <div ref={libraryRef}>
          {libraries.length > 0 && (
            <BookShelf
              books={libraries[activeLibrary]?.books || []}
              moveBook={moveBook}
              onBookClick={handleBookClick}
              viewOnly={false}
              libraryStyleDto={libraries[activeLibrary]?.libraryStyleDto}
            />
          )}
        </div>
      </div>
      <Alert />
    </DndProvider>
  );
};

export default LibraryMain;
